package org.uag.netsim.core.layer.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.layer.AbstractLayerNode;
import org.uag.netsim.core.layer.DefaultLayerTcpConnection;
import org.uag.netsim.core.layer.APSME.APSMERequestDispatcher;
import org.uag.netsim.core.layer.app.APSDE.APSDERequestDispatcher;
import org.uag.netsim.core.layer.app.client.AppLayerAPSMEOperations;
import org.uag.netsim.core.layer.app.client.AppLayerClient;

@SuppressWarnings("rawtypes")
@Component("appLayerNode")
@Scope("prototype")
public class AppLayerNode extends AbstractLayerNode<AppLayerRequestDispatcher
,AppLayerTcpRequestDispatcher
,DefaultLayerTcpConnection
,AppLayerClient> implements AppLayerAPSMEOperations{

	/*@Autowired
	@Qualifier("networkLayerClient")
	private NetworkLayerClient networkClient;*/
	
	public static int MAX_THREADS = 10;
	private ThreadPoolExecutor apsdeRequestExecutor;
	private ThreadPoolExecutor apsmeRequestExecutor;
	
	 protected Map<Beacon,List<Beacon>> neighbors;
	
	final static Logger logger = Logger.getLogger(AppLayerNode.class);
	public final Map<Class<APSDERequestDispatcher>,List<DefaultLayerTcpConnection>> APSDE_CONN_MAP = 
			new HashMap<Class<APSDERequestDispatcher>,List<DefaultLayerTcpConnection>>();
	public final Map<Class<APSMERequestDispatcher>,List<DefaultLayerTcpConnection>> APSME_CONN_MAP = 
			new HashMap<Class<APSMERequestDispatcher>,List<DefaultLayerTcpConnection>>();
	
	public AppLayerNode() {
		super(AppLayerRequestDispatcher.class,AppLayerTcpRequestDispatcher.class,DefaultLayerTcpConnection.class,AppLayerClient.class);
		APSDE_CONN_MAP.put(APSDERequestDispatcher.class,new ArrayList<DefaultLayerTcpConnection>());
		APSME_CONN_MAP.put(APSMERequestDispatcher.class,new ArrayList<DefaultLayerTcpConnection>());
		
		neighbors = new HashMap<Beacon, List<Beacon>>();
	}

	public static int MIN_PORT_RANGE = 9300;
	public static int MAX_PORT_RANGE = 9309;
	
	@PostConstruct
	public void construct() throws Exception{
		apsdeRequestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		apsmeRequestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		minPortRange = MIN_PORT_RANGE;
		maxPortRange = MAX_PORT_RANGE;
		super.init();
	}
	
	public void release() throws Exception{		
		List<DefaultLayerTcpConnection> apsdeConnections = APSDE_CONN_MAP.get(APSDERequestDispatcher.class);
		if(apsdeConnections!=null){
			for(DefaultLayerTcpConnection conn:apsdeConnections){
				conn.release();
			}
		}
		apsdeRequestExecutor.shutdown();
		List<DefaultLayerTcpConnection> apsmeConnections = APSME_CONN_MAP.get(APSMERequestDispatcher.class);
		if(apsmeConnections!=null){
			for(DefaultLayerTcpConnection conn:apsmeConnections){
				conn.release();
			}
		}
		apsmeRequestExecutor.shutdown();
		super.release();
	}
	
	public synchronized DefaultLayerTcpConnection openAPSDESAP() throws Exception {
		DefaultLayerTcpConnection<APSDERequestDispatcher> conn = 
				new DefaultLayerTcpConnection<APSDERequestDispatcher>(this,APSDERequestDispatcher.class);
		logger.info("Opened APSDE SAP "+conn.getClass()+" on "+conn.getPort());
		apsdeRequestExecutor.execute(conn);
		APSDE_CONN_MAP.get(APSDERequestDispatcher.class).add(conn);
		return conn;
	}
	
	public synchronized DefaultLayerTcpConnection openAPSMESAP() throws Exception {
		DefaultLayerTcpConnection<APSMERequestDispatcher> conn = 
				new DefaultLayerTcpConnection<APSMERequestDispatcher>(this,APSMERequestDispatcher.class);
		logger.info("Opened APSME SAP "+conn.getClass()+" on "+conn.getPort());
		apsmeRequestExecutor.execute(conn);
		APSME_CONN_MAP.get(APSMERequestDispatcher.class).add(conn);
		return conn;
	}
	
	public synchronized boolean closeAPSDESAP(int tcpPort) throws Exception{
		boolean result = false;
		List<DefaultLayerTcpConnection> connections = APSDE_CONN_MAP.get(APSDERequestDispatcher.class);
		for(DefaultLayerTcpConnection conn:connections){
			if(conn.getPort() == tcpPort){
				conn.release();
				result = true;
			}
		}
		return result;
	}
	
	public synchronized boolean closeAPSMESAP(int tcpPort) throws Exception{
		boolean result = false;
		List<DefaultLayerTcpConnection> connections = APSME_CONN_MAP.get(APSMERequestDispatcher.class);
		for(DefaultLayerTcpConnection conn:connections){
			if(conn.getPort() == tcpPort){
				conn.release();
				result = true;
			}
		}
		return result;
	}

	@Override
	public boolean addNeigbor(Beacon beacon,Beacon neighbor) throws Exception{
		if(!alreadyRegistered(neighbor, neighbors.get(beacon))){
			neighbors.get(beacon.getType()).add(beacon);
			return true;
		}
		return false;
	}
	private boolean alreadyRegistered(Beacon beacon,List<Beacon> beacons) {
		for(Beacon registeredNeighbord:beacons) {
			if(registeredNeighbord.getPanId() == beacon.getPanId() 
					&& registeredNeighbord.getExtendedPanId() == beacon.getExtendedPanId()) {
				return true;
			}
		}
		return false;
	}
	
}