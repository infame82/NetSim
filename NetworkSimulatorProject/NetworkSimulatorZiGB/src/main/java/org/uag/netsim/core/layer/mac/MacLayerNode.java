package org.uag.netsim.core.layer.mac;

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
import org.uag.netsim.core.layer.mac.MCPS.MCPSRequestDispatcher;
import org.uag.netsim.core.layer.mac.MLME.MLMERequestDispatcher;
import org.uag.netsim.core.layer.network.NLDE.NLDERequestDispatcher;
import org.uag.netsim.core.layer.network.NLME.NLMERequestDispatcher;
import org.uag.netsim.core.layer.phy.PhyLayerClient;

@SuppressWarnings("rawtypes")
@Component("macLayerNode")
@Scope("prototype")
public class MacLayerNode extends AbstractLayerNode<MacLayerRequestDispatcher,MacLayerTcpRequestDispatcher
,DefaultLayerTcpConnection
,MacLayerClient> {

	@Autowired
	@Qualifier("phyLayerClient")
	private PhyLayerClient phyClient;
	
	public static int MAX_THREADS = 10;
	private ThreadPoolExecutor mcpsRequestExecutor;
	private ThreadPoolExecutor mlmeRequestExecutor;
	
	final static Logger logger = Logger.getLogger(MacLayerNode.class);
	public final Map<Class<MCPSRequestDispatcher>,List<DefaultLayerTcpConnection>> MCPS_CONN_MAP = 
			new HashMap<Class<MCPSRequestDispatcher>,List<DefaultLayerTcpConnection>>();
	public final Map<Class<MLMERequestDispatcher>,List<DefaultLayerTcpConnection>> MLME_CONN_MAP = 
			new HashMap<Class<MLMERequestDispatcher>,List<DefaultLayerTcpConnection>>();
	
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
		super.init();
	}
	
	public void release() throws Exception{		
		List<DefaultLayerTcpConnection> mcpsConnections = MCPS_CONN_MAP.get(NLDERequestDispatcher.class);
		if(mcpsConnections!=null){
			for(DefaultLayerTcpConnection conn:mcpsConnections){
				conn.release();
			}
		}
		mcpsRequestExecutor.shutdown();
		List<DefaultLayerTcpConnection> mlmeConnections = MLME_CONN_MAP.get(NLMERequestDispatcher.class);
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

}
