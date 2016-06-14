package org.uag.netsim.core.layer.phy;

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
import org.uag.netsim.core.layer.AbstractLayerNode;
import org.uag.netsim.core.layer.DefaultLayerTcpConnection;
import org.uag.netsim.core.layer.network.NLDE.NLDERequestDispatcher;
import org.uag.netsim.core.layer.network.NLME.NLMERequestDispatcher;
import org.uag.netsim.core.layer.phy.PD.PDRequestDispatcher;
import org.uag.netsim.core.layer.phy.PLME.PLMERequestDispatcher;

@SuppressWarnings("rawtypes")
@Component("phyLayerNode")
@Scope("prototype")
public class PhyLayerNode extends AbstractLayerNode<PhyLayerRequestDispatcher
,PhyLayerTcpRequestDispatcher
,DefaultLayerTcpConnection
,PhyLayerClient> {
	
	private ThreadPoolExecutor pdRequestExecutor;
	private ThreadPoolExecutor plmeRequestExecutor;
	public static int MIN_PORT_RANGE = 9500;
	public static int MAX_PORT_RANGE = 9509;
	public static int MAX_THREADS = 10;
	
	final static Logger logger = Logger.getLogger(PhyLayerNode.class);
	public final Map<Class<PDRequestDispatcher>,List<DefaultLayerTcpConnection>> PD_CONN_MAP = 
			new HashMap<Class<PDRequestDispatcher>,List<DefaultLayerTcpConnection>>();
	public final Map<Class<PLMERequestDispatcher>,List<DefaultLayerTcpConnection>> PLME_CONN_MAP = 
			new HashMap<Class<PLMERequestDispatcher>,List<DefaultLayerTcpConnection>>();

	public PhyLayerNode() {
		super(PhyLayerRequestDispatcher.class,PhyLayerTcpRequestDispatcher.class,DefaultLayerTcpConnection.class,PhyLayerClient.class);
		PD_CONN_MAP.put(PDRequestDispatcher.class,new ArrayList<DefaultLayerTcpConnection>());
		PLME_CONN_MAP.put(PLMERequestDispatcher.class,new ArrayList<DefaultLayerTcpConnection>());
	}

	

	
	@PostConstruct
	public void construct() throws Exception{
		pdRequestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		plmeRequestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		minPortRange = MIN_PORT_RANGE;
		maxPortRange = MAX_PORT_RANGE;
		super.init();
		
	}
	
	public void release() throws Exception{		
		List<DefaultLayerTcpConnection> nldeConnections = PD_CONN_MAP.get(NLDERequestDispatcher.class);
		if(nldeConnections!=null){
			for(DefaultLayerTcpConnection conn:nldeConnections){
				conn.release();
			}
		}
		pdRequestExecutor.shutdown();
		List<DefaultLayerTcpConnection> nlmeConnections = PLME_CONN_MAP.get(NLMERequestDispatcher.class);
		if(nlmeConnections!=null){
			for(DefaultLayerTcpConnection conn:nlmeConnections){
				conn.release();
			}
		}
		plmeRequestExecutor.shutdown();
		super.release();
	}
	
	public synchronized DefaultLayerTcpConnection openPDSAP() throws Exception {
		DefaultLayerTcpConnection<PDRequestDispatcher> conn = 
				new DefaultLayerTcpConnection<PDRequestDispatcher>(this,PDRequestDispatcher.class);
		logger.info("Opened PD SAP "+conn.getClass()+" on "+conn.getPort());
		pdRequestExecutor.execute(conn);
		PD_CONN_MAP.get(PDRequestDispatcher.class).add(conn);
		return conn;
	}
	
	public synchronized DefaultLayerTcpConnection openPLMESAP() throws Exception {
		DefaultLayerTcpConnection<PLMERequestDispatcher> conn = 
				new DefaultLayerTcpConnection<PLMERequestDispatcher>(this,PLMERequestDispatcher.class);
		logger.info("Opened PLME SAP "+conn.getClass()+" on "+conn.getPort());
		plmeRequestExecutor.execute(conn);
		PLME_CONN_MAP.get(PLMERequestDispatcher.class).add(conn);
		return conn;
	}
	
	public synchronized boolean closePDSAP(int tcpPort) throws Exception{
		boolean result = false;
		List<DefaultLayerTcpConnection> connections = PD_CONN_MAP.get(PDRequestDispatcher.class);
		for(DefaultLayerTcpConnection conn:connections){
			if(conn.getPort() == tcpPort){
				conn.release();
				result = true;
			}
		}
		return result;
	}
	
	public synchronized boolean closePLMESAP(int tcpPort) throws Exception{
		boolean result = false;
		List<DefaultLayerTcpConnection> connections = PLME_CONN_MAP.get(PLMERequestDispatcher.class);
		for(DefaultLayerTcpConnection conn:connections){
			if(conn.getPort() == tcpPort){
				conn.release();
				result = true;
			}
		}
		return result;
	}
}
