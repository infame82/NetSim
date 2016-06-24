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
import org.uag.netsim.core.layer.phy.RFChannel.RF_CHANNEL;
import org.uag.netsim.core.layer.phy.PD.PDRequestDispatcher;
import org.uag.netsim.core.layer.phy.PLME.PLMERequestDispatcher;

@SuppressWarnings("rawtypes")
@Component("phyLayerNode")
@Scope("prototype")
public class PhyLayerNode extends AbstractLayerNode<PhyLayerRequestDispatcher
,PhyLayerTcpRequestDispatcher
,DefaultLayerTcpConnection
,PhyLayerClient> implements PhyLayerOperations{
	
	private List<RFChannel> channels;
	
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
		channels = new ArrayList<RFChannel>();
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
		
		pdRequestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		plmeRequestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		minPortRange = MIN_PORT_RANGE;
		maxPortRange = MAX_PORT_RANGE;
		super.init();		
	}
	
	private RFChannel getChannel(RF_CHANNEL channelNumber) {
		for(RFChannel channel:channels) {
			if(channel.getChannel()== channelNumber) {
				return channel;
			}
		}
		return null;
	}
	
	public synchronized List<RFChannel> getChannels() {
		return channels;
	}
	
	public synchronized boolean increaseEnergyLevel(RF_CHANNEL channel) {
		RFChannel ch = getChannel(channel);
		ch.setEnergy(ch.getEnergy()+1);
		return true;
	}
	
	public void release() throws Exception{		
		List<DefaultLayerTcpConnection> pdConnections = PD_CONN_MAP.get(PDRequestDispatcher.class);
		if(pdConnections!=null){
			for(DefaultLayerTcpConnection conn:pdConnections){
				conn.release();
			}
		}
		pdRequestExecutor.shutdown();
		List<DefaultLayerTcpConnection> plmeConnections = PLME_CONN_MAP.get(PLMERequestDispatcher.class);
		if(plmeConnections!=null){
			for(DefaultLayerTcpConnection conn:plmeConnections){
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
