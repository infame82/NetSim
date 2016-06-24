package org.uag.netsim.core.layer.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.layer.AbstractLayerNode;
import org.uag.netsim.core.layer.DefaultLayerTcpConnection;
import org.uag.netsim.core.layer.mac.MacLayerClient;
import org.uag.netsim.core.layer.network.NLDE.NLDERequestDispatcher;
import org.uag.netsim.core.layer.network.NLME.NLMERequestDispatcher;

@SuppressWarnings("rawtypes")
@Component("networkLayerNode")
@Scope("prototype")
public class NetworkLayerNode extends AbstractLayerNode<NetworkLayerRequestDispatcher
,NetworkLayerTcpRequestDispatcher
,DefaultLayerTcpConnection
,NetworkLayerClient> {
	
	public static int MAX_THREADS = 10;
	
	/*@Autowired
	@Qualifier("macLayerClient")
	private MacLayerClient macClient;*/
	
	private ThreadPoolExecutor nlmeRequestExecutor;
	private ThreadPoolExecutor nldeRequestExecutor;
	public static int MIN_PORT_RANGE = 9200;
	public static int MAX_PORT_RANGE = 9209;
	
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
	
}
