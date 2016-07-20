package org.uag.netsim.core.layer.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.layer.AbstractLayerNode;
import org.uag.netsim.core.layer.DefaultLayerTcpConnection;
import org.uag.netsim.core.layer.app.client.AppLayerClient;
import org.uag.netsim.core.layer.mac.MacLayerClient;
import org.uag.netsim.core.layer.network.NLDE.NLDERequestDispatcher;
import org.uag.netsim.core.layer.network.NLME.NLMERequestDispatcher;
import org.uag.netsim.core.layer.phy.RFChannel;
import org.uag.netsim.core.layer.phy.RFChannel.RF_CHANNEL;

@SuppressWarnings("rawtypes")
@Component("networkLayerNode")
@Scope("prototype")
public class NetworkLayerNode extends AbstractLayerNode<NetworkLayerRequestDispatcher
,NetworkLayerTcpRequestDispatcher
,DefaultLayerTcpConnection
,NetworkLayerClient> implements NetworkLayerNLMEOperations{
	
	public static int MAX_THREADS = 10;
	
	/*@Autowired
	@Qualifier("macLayerClient")
	private MacLayerClient macClient;*/
	
	private ThreadPoolExecutor nlmeRequestExecutor;
	private ThreadPoolExecutor nldeRequestExecutor;
	private Random random;
	public static int MIN_PORT_RANGE = 9200;
	public static int MAX_PORT_RANGE = 9209;
	
	private static final List<RFChannel> channels = new ArrayList<RFChannel>();

	static {
		channels.add(new RFChannel(RF_CHANNEL.CH_11));
		channels.add(new RFChannel(RF_CHANNEL.CH_12));
		channels.add(new RFChannel(RF_CHANNEL.CH_13));
		channels.add(new RFChannel(RF_CHANNEL.CH_14));
		channels.add(new RFChannel(RF_CHANNEL.CH_15));
		channels.add(new RFChannel(RF_CHANNEL.CH_16));
		channels.add(new RFChannel(RF_CHANNEL.CH_17));
		channels.add(new RFChannel(RF_CHANNEL.CH_18));
		channels.add(new RFChannel(RF_CHANNEL.CH_19));
		channels.add(new RFChannel(RF_CHANNEL.CH_20));
		channels.add(new RFChannel(RF_CHANNEL.CH_21));
		channels.add(new RFChannel(RF_CHANNEL.CH_22));
		channels.add(new RFChannel(RF_CHANNEL.CH_23));
		channels.add(new RFChannel(RF_CHANNEL.CH_24));
	}
	
	final static Logger logger = Logger.getLogger(NetworkLayerNode.class);
	public final Map<Class<NLMERequestDispatcher>,List<DefaultLayerTcpConnection>> NLME_CONN_MAP = 
			new HashMap<Class<NLMERequestDispatcher>,List<DefaultLayerTcpConnection>>();
	public final Map<Class<NLDERequestDispatcher>,List<DefaultLayerTcpConnection>> NLDE_CONN_MAP = 
			new HashMap<Class<NLDERequestDispatcher>,List<DefaultLayerTcpConnection>>();

	public NetworkLayerNode() {
		super(NetworkLayerRequestDispatcher.class,NetworkLayerTcpRequestDispatcher.class,DefaultLayerTcpConnection.class,NetworkLayerClient.class);
		NLME_CONN_MAP.put(NLMERequestDispatcher.class,new ArrayList<DefaultLayerTcpConnection>());
		NLDE_CONN_MAP.put(NLDERequestDispatcher.class,new ArrayList<DefaultLayerTcpConnection>());
	}

	
	
	@PostConstruct
	public void construct() throws Exception{
		nlmeRequestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		nldeRequestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		minPortRange = MIN_PORT_RANGE;
		maxPortRange = MAX_PORT_RANGE;
		random = new Random();
		super.init();
		
	}
	
	public void release() throws Exception{		
		List<DefaultLayerTcpConnection> nldeConnections = NLDE_CONN_MAP.get(NLDERequestDispatcher.class);
		if(nldeConnections!=null){
			for(DefaultLayerTcpConnection conn:nldeConnections){
				conn.release();
			}
		}
		nldeRequestExecutor.shutdown();
		List<DefaultLayerTcpConnection> nlmeConnections = NLME_CONN_MAP.get(NLMERequestDispatcher.class);
		if(nlmeConnections!=null){
			for(DefaultLayerTcpConnection conn:nlmeConnections){
				conn.release();
			}
		}
		nlmeRequestExecutor.shutdown();
		super.release();
	}
	
	public synchronized DefaultLayerTcpConnection openNLMESAP() throws Exception {
		DefaultLayerTcpConnection<NLMERequestDispatcher> conn = 
				new DefaultLayerTcpConnection<NLMERequestDispatcher>(this,NLMERequestDispatcher.class);
		logger.info("Opened NLME SAP "+conn.getClass()+" on "+conn.getPort());
		nlmeRequestExecutor.execute(conn);
		NLME_CONN_MAP.get(NLMERequestDispatcher.class).add(conn);
		return conn;
	}
	
	public synchronized DefaultLayerTcpConnection openNLDESAP() throws Exception {
		DefaultLayerTcpConnection<NLDERequestDispatcher> conn = 
				new DefaultLayerTcpConnection<NLDERequestDispatcher>(this,NLDERequestDispatcher.class);
		logger.info("Opened NLDE SAP "+conn.getClass()+" on "+conn.getPort());
		nldeRequestExecutor.execute(conn);
		NLDE_CONN_MAP.get(NLDERequestDispatcher.class).add(conn);
		return conn;
	}
	
	public synchronized boolean closeNLDESAP(int tcpPort) throws Exception{
		boolean result = false;
		List<DefaultLayerTcpConnection> connections = NLDE_CONN_MAP.get(NLDERequestDispatcher.class);
		for(DefaultLayerTcpConnection conn:connections){
			if(conn.getPort() == tcpPort){
				conn.release();
				result = true;
			}
		}
		return result;
	}
	
	public synchronized boolean closeNLMESAP(int tcpPort) throws Exception{
		boolean result = false;
		List<DefaultLayerTcpConnection> connections = NLME_CONN_MAP.get(NLMERequestDispatcher.class);
		for(DefaultLayerTcpConnection conn:connections){
			if(conn.getPort() == tcpPort){
				conn.release();
				result = true;
			}
		}
		return result;
	}



	@Override
	public Beacon networkFormation(Beacon beacon) throws Exception {
		if(!beacon.isCoordinator()){
			throw new Exception("NLME001:Is not a coordinator");
		}
		MacLayerClient macClient = new MacLayerClient();
		List<RFChannel> acceptableChannels = macClient.energyDetectionScan();
		Map<RFChannel,List<Beacon>> registeredDevices = macClient.activeScan(acceptableChannels, beacon);
		RFChannel selectedChannel = getMinDevicesChannel(registeredDevices);
		if(selectedChannel==null){
			throw new Exception("NLME002:Not available channel");
		}
		int panId = createPanID(registeredDevices);
		int extendedPANId = createExtendedPanID(registeredDevices);
		beacon.setExtendedPanId(extendedPANId);
		beacon.setPanId(panId);
		if(!macClient.start(selectedChannel, beacon)){
			throw new Exception("NLME002: Unable to start a network");
		}
		return beacon;
	}
	
	private RFChannel getMinDevicesChannel(
			Map<RFChannel, List<Beacon>> registeredDevices) {
		RFChannel selectedChannel = null;
		int minDevices = 0;
		for (RFChannel channel : registeredDevices.keySet()) {
			if (selectedChannel == null) {
				selectedChannel = channel;
			} else {
				List<Beacon> devices = registeredDevices.get(channel);
				if ((devices == null && minDevices > 0)
						|| (devices != null && (devices.size() < minDevices))) {
					selectedChannel = channel;
				}
			}
		}
		return selectedChannel;
	}
	
	private int createPanID(Map<RFChannel, List<Beacon>>  registeredDevices) {
		boolean validPanID = true;
		int panID = -1;
		do {
			panID = random.nextInt(65535);
			for(List<Beacon> devices:registeredDevices.values()) {
				if (devices != null && !devices.isEmpty()) {
					for (Beacon device : devices) {
						if (device.getPanId() == panID) {
							validPanID = false;
							break;
						}
					}
				}
			}

		} while (!validPanID);
		return panID;
	}
	
	private int createExtendedPanID(Map<RFChannel, List<Beacon>>  registeredDevices) {
		boolean validExtendedPanID = true;
		int extendedPanID = -1;
		do {
			extendedPanID = random.nextInt(262140);
			for(List<Beacon> devices:registeredDevices.values()) {
				if (devices != null && !devices.isEmpty()) {
					for (Beacon device : devices) {
						if (device.getExtendedPanId()== extendedPanID) {
							validExtendedPanID = false;
							break;
						}
					}
				}
			}

		} while (!validExtendedPanID);
		return extendedPanID;
	}

	@Override
	public Map<RFChannel, List<Beacon>> discovery(Beacon beacon) throws Exception{
		MacLayerClient macClient = new MacLayerClient();
		Map<RFChannel, List<Beacon>> detectedNetworks = macClient.activeScan(channels, beacon);
		Map<RFChannel, List<Beacon>> availableNetworks = new LinkedHashMap<RFChannel, List<Beacon>>();
		for (RFChannel channel : detectedNetworks.keySet()) {
			List<Beacon> registered = detectedNetworks.get(channel);
			if (registered != null && !registered.isEmpty()) {
				availableNetworks.put(channel, registered);
			}
		}
		return availableNetworks;
	}

	@Override
	public List<Beacon> join(RFChannel channel,Beacon beacon,Beacon joinBeacon) throws Exception {
		MacLayerClient macClient = new MacLayerClient();
		List<Beacon> beacons = macClient.association(beacon, joinBeacon);
		if(beacons!=null && !beacons.isEmpty() && beacon.isRouter()){
			macClient.start(channel, beacons.get(0));
		}
		return beacons;
	}

	@Override
	public boolean associate(Beacon beacon,List<Beacon> neighbors) throws Exception{
		AppLayerClient appClient = new AppLayerClient();
		for(Beacon neighbor:neighbors) {
			appClient.addNeigbor(beacon, neighbor);
		}
		return true;
	}


	@Override
	public void transmitData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void retransmitData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestExtenedPanId() {
		// TODO Auto-generated method stub
		
	}
	
}
