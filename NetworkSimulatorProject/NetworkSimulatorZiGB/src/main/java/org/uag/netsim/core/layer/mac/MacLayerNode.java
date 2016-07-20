package org.uag.netsim.core.layer.mac;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.uag.netsim.core.layer.mac.MCPS.MCPSRequestDispatcher;
import org.uag.netsim.core.layer.mac.MLME.MLMERequestDispatcher;
import org.uag.netsim.core.layer.phy.PhyLayerClient;
import org.uag.netsim.core.layer.phy.RFChannel;

@SuppressWarnings("rawtypes")
@Component("macLayerNode")
@Scope("prototype")
public class MacLayerNode extends AbstractLayerNode<MacLayerRequestDispatcher,MacLayerTcpRequestDispatcher
,DefaultLayerTcpConnection
,MacLayerClient> implements MacLayerMLMEOperations{

	/*@Autowired
	@Qualifier("phyLayerClient")
	private PhyLayerClient phyClient;*/
	
	public static int MAX_THREADS = 10;
	private ThreadPoolExecutor mcpsRequestExecutor;
	private ThreadPoolExecutor mlmeRequestExecutor;
	
	final static Logger logger = Logger.getLogger(MacLayerNode.class);
	public final Map<Class<MCPSRequestDispatcher>,List<DefaultLayerTcpConnection>> MCPS_CONN_MAP = 
			new HashMap<Class<MCPSRequestDispatcher>,List<DefaultLayerTcpConnection>>();
	public final Map<Class<MLMERequestDispatcher>,List<DefaultLayerTcpConnection>> MLME_CONN_MAP = 
			new HashMap<Class<MLMERequestDispatcher>,List<DefaultLayerTcpConnection>>();
	
	//SYNCH STATE
	private Map<RFChannel,List<Beacon>> registeredNetworks;
	private Map<String,List<Beacon>> registeredDevices;
	private long extendedAddress;
	
	public MacLayerNode() {
		super(MacLayerRequestDispatcher.class,MacLayerTcpRequestDispatcher.class ,DefaultLayerTcpConnection.class,MacLayerClient.class);
		MCPS_CONN_MAP.put(MCPSRequestDispatcher.class,new ArrayList<DefaultLayerTcpConnection>());
		MLME_CONN_MAP.put(MLMERequestDispatcher.class,new ArrayList<DefaultLayerTcpConnection>());
	}

	public static int MIN_PORT_RANGE = 9100;
	public static int MAX_PORT_RANGE = 9109;
	
	@PostConstruct
	public void construct() throws Exception{
		mcpsRequestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		mlmeRequestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		minPortRange = MIN_PORT_RANGE;
		maxPortRange = MAX_PORT_RANGE;
		registeredNetworks = new HashMap<RFChannel, List<Beacon>>();
		registeredDevices = new HashMap<String, List<Beacon>>();
		extendedAddress = new Random().nextLong();
		super.init();
	}
	
	public void release() throws Exception{		
		List<DefaultLayerTcpConnection> mcpsConnections = MCPS_CONN_MAP.get(MCPSRequestDispatcher.class);
		if(mcpsConnections!=null){
			for(DefaultLayerTcpConnection conn:mcpsConnections){
				conn.release();
			}
		}
		mcpsRequestExecutor.shutdown();
		List<DefaultLayerTcpConnection> mlmeConnections = MLME_CONN_MAP.get(MLMERequestDispatcher.class);
		if(mlmeConnections!=null){
			for(DefaultLayerTcpConnection conn:mlmeConnections){
				conn.release();
			}
		}
		mlmeRequestExecutor.shutdown();
		super.release();
	}
	
	
	public synchronized DefaultLayerTcpConnection openMCPSSAP() throws Exception {
		DefaultLayerTcpConnection<MCPSRequestDispatcher> conn = 
				new DefaultLayerTcpConnection<MCPSRequestDispatcher>(this,MCPSRequestDispatcher.class);
		logger.info("Opened MCPS SAP "+conn.getClass()+" on "+conn.getPort());
		mcpsRequestExecutor.execute(conn);
		MCPS_CONN_MAP.get(MCPSRequestDispatcher.class).add(conn);
		return conn;
	}
	
	public synchronized DefaultLayerTcpConnection openMLMESAP() throws Exception {
		DefaultLayerTcpConnection<MLMERequestDispatcher> conn = 
				new DefaultLayerTcpConnection<MLMERequestDispatcher>(this,MLMERequestDispatcher.class);
		logger.info("Opened MLME SAP "+conn.getClass()+" on "+conn.getPort());
		mlmeRequestExecutor.execute(conn);
		MLME_CONN_MAP.get(MLMERequestDispatcher.class).add(conn);
		return conn;
	}
	
	public synchronized boolean closeMCPSSAP(int tcpPort) throws Exception{
		boolean result = false;
		List<DefaultLayerTcpConnection> connections = MCPS_CONN_MAP.get(MCPSRequestDispatcher.class);
		for(DefaultLayerTcpConnection conn:connections){
			if(conn.getPort() == tcpPort){
				conn.release();
				result = true;
			}
		}
		return result;
	}
	
	public synchronized boolean closeMLMESAP(int tcpPort) throws Exception{
		boolean result = false;
		List<DefaultLayerTcpConnection> connections = MLME_CONN_MAP.get(MLMERequestDispatcher.class);
		for(DefaultLayerTcpConnection conn:connections){
			if(conn.getPort() == tcpPort){
				conn.release();
				result = true;
			}
		}
		return result;
	}

	@Override
	public List<RFChannel> energyDetectionScan() {
		List<RFChannel> acceptableChannels = new ArrayList<RFChannel>();
		try {
			PhyLayerClient client = new PhyLayerClient();
			List<RFChannel> channels = client.getChannels();
			if(channels!=null){
				for(RFChannel channel:channels) {
					if(channel.getEnergy()<=ACCEPTABLE_ENERGY_LEVEL) {
						acceptableChannels.add(channel);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acceptableChannels;
	}

	@Override
	public Map<RFChannel,List<Beacon>> activeScan(List<RFChannel> channels,Beacon beacon) {
		Map<RFChannel,List<Beacon>> detectedNetworks = new HashMap<RFChannel, List<Beacon>>();
		List<Beacon> registeredBeacons = null;
		for(RFChannel channel:channels) {
			List<Beacon> detectedBeacons = new ArrayList<Beacon>();
			registeredBeacons = this.registeredNetworks.get(channel);
			if(registeredBeacons!=null && !registeredBeacons.isEmpty()) {
				for(Beacon registeredBeacon:registeredBeacons) {
					if(isBeaconInRange(beacon, registeredBeacon)) {
						detectedBeacons.add(registeredBeacon);
					}
				}
			}
			detectedNetworks.put(channel,detectedBeacons);
		}

		return detectedNetworks;
	}
	
	public static boolean isBeaconInRange(Beacon a,Beacon b) {
		Point p1 = a.getLocation();
		Point p2 = b.getLocation();
		boolean rangeX = false;
		boolean rangeY = false;
		if(p1.getX() == p2.getX()) {
			rangeX = true;
		}else if(p1.getX()<p2.getX() && ( (p1.getX()+a.getPotency()) > (p2.getX()-b.getPotency()) )) {
			rangeX = true;
		}else if(p1.getX()>p2.getX() && ( (p1.getX()-a.getPotency()) < (p2.getX()+b.getPotency()) )) {
			rangeX = true;
		}
		
		if(p1.getY() == p2.getY()) {
			rangeY = true;
		}else if(p1.getY()<p2.getY() && ( (p1.getY()+a.getPotency()) > (p2.getY()-b.getPotency()) )) {
			rangeY = true;
		}else if(p1.getY()>p2.getY() && ( (p1.getY()-a.getPotency()) < (p2.getY()+b.getPotency()) )) {
			rangeY = true;
		}
		
		return rangeX && rangeY;
	}

	@Override
	public boolean setPANId(RFChannel channel,Beacon beacon) {
		List<Beacon> beacons = registeredNetworks.get(channel);
		if(beacons == null) {
			beacons = new ArrayList<Beacon>();
		}
		beacons.add(beacon);
		registeredNetworks.put(channel, beacons);
		return true;
	}

	@Override
	public List<Beacon> association(Beacon beacon,Beacon joinBeacon) {
		// TODO Auto-generated method stub
		String beaconId = joinBeacon.getPanId()+":"+joinBeacon.getExtendedPanId();
		List<Beacon> neighbords = new ArrayList<Beacon>();
		for(Beacon parent:registeredDevices.get(beaconId)) {
			if(parent.isAllowJoin() && isBeaconInRange(beacon, parent)) {
				neighbords.add(parent);
			}
		}
		return null;
	}

	@Override
	public boolean transmission(Beacon beacon) throws Exception {
		List<Beacon> registeredBeacons = null;
		RFChannel transmissionChannel = null;
		for(RFChannel channel:registeredNetworks.keySet()) {
			registeredBeacons = registeredNetworks.get(channel);
			if(registeredBeacons!=null && !registeredBeacons.isEmpty()) {
				for(Beacon registeredBeacon:registeredBeacons) {
					if(registeredBeacon.getPanId() == beacon.getPanId() && 
							registeredBeacon.getExtendedPanId() == beacon.getExtendedPanId()) {
						transmissionChannel = channel;
						break;
					}
				}
			}
		}
		
		if(transmissionChannel==null) {
			return false;
		}
		PhyLayerClient phyClient = new PhyLayerClient();
		phyClient.increaseEnergyLevel(transmissionChannel.getChannel());
		return true;
	}

	@Override
	public boolean start(RFChannel channel,Beacon beacon) {
		boolean response = false;
		List<Beacon> networks = registeredNetworks.get(channel);
		if(networks == null) {
			networks = new ArrayList<Beacon>();
		}
		networks.add(beacon);
		String deviceNwId = beacon.getPanId()+":"+beacon.getExtendedPanId();
		List<Beacon> networkDevices = registeredDevices.get(deviceNwId);
		if(networkDevices == null) {
			networkDevices = new ArrayList<Beacon>();
		}
		response = registerDevice(beacon);
		if(response){
			registeredNetworks.put(channel, networks);
		}
		return response;
	}

	@Override
	public Map<RFChannel,List<Beacon>> getRegisteredNetworks() {
		return this.registeredNetworks;
	}

	@Override
	public Map<String,List<Beacon>> getRegisteredDevices() {
		return this.registeredDevices;
	}

	@Override
	public boolean registerDevice(Beacon beacon) {
		boolean response = false;
		String deviceNwId = beacon.getPanId()+":"+beacon.getExtendedPanId();
		List<Beacon> networkDevices = registeredDevices.get(deviceNwId);
		if(networkDevices == null) {
			networkDevices = new ArrayList<Beacon>();
		}
		boolean add = true;
		for(Beacon registered:networkDevices) {
			if(registered.getId().equals(beacon.getId())) {
				add=false;
			}
		}
		if(add) {
			networkDevices.add(beacon);
			response = true;
		}
		registeredDevices.put(deviceNwId, networkDevices);
		return response;
	}

	@Override
	public long getExtendedAddress() {
		return extendedAddress;
	}

}
