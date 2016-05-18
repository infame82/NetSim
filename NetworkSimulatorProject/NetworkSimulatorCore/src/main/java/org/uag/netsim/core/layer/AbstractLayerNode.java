package org.uag.netsim.core.layer;

import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.uag.netsim.core.DefaultCoreLog;
import org.uag.netsim.core.ICoreLog;


public abstract class AbstractLayerNode
<RD extends LayerRequestDispatcher
,TD extends LayerTcpRequestDispatcher
,C extends LayerTcpConnection
,L extends LayerClient> 
implements LayerNode {
	
	private final Class<RD> dispatcherClass;
	private final Class<TD> tcpDispatcherClass;
	private final Class<C> tcpConnectionClass;
	private final Class<L> selfLayerClientClass;
	
	
	final static Logger logger = Logger.getLogger(AbstractLayerNode.class);
	
	public final Map<Class<RD>,Set<Integer>> OPENED_PORTS = 
			new HashMap<Class<RD>,Set<Integer>>();
	
	public final Map<Class<TD>,List<C>> TCP_CONN_MAP = 
			new HashMap<Class<TD>,List<C>>();
	
	class LayerTcpComparator implements Comparator<C>{

		public int compare(C o1, C o2) {
			if(o1.getActiveCount()<o2.getActiveCount()){
				return -1;
			}
			if(o1.getActiveCount()>o2.getActiveCount()){
				return 1;
			}
			return 0;
		}
		
	}

	protected LayerTcpComparator layerTcpComparator = new LayerTcpComparator();
	protected boolean ready;
	private DatagramSocket socket;
	private ThreadPoolExecutor requestExecutor;

	protected int minPortRange;
	protected int maxPortRange;
	protected L _selfLayerClient;
	
	public int getMaxPort(){
		return maxPortRange;
	}
	
	public AbstractLayerNode(Class<RD> dispatcherClass,Class<TD> tcpDispatcherClass,Class<C> tcpConnectionClass,Class<L> selfLayerClientClass){
		this.dispatcherClass = dispatcherClass;
		this.tcpConnectionClass = tcpConnectionClass;
		this.selfLayerClientClass = selfLayerClientClass;
		this.tcpDispatcherClass = tcpDispatcherClass;
	}
	
	
	
	public static int ASYNC_REQUEST = 1;

	public void init() throws Exception{		
		logger.info("Initializing "+super.getClass()+"...");
		ready = false;
		requestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(ASYNC_REQUEST);
	}
	
	public void release(){
		logger.info("Realising "+super.getClass()+" on "+socket.getLocalPort());
		ready = false;
		if(socket!=null){
			releasePort(socket.getLocalPort());
			if(socket.isConnected()){
				socket.close();
			}
			
		}
		requestExecutor.shutdownNow();
		logger.info("Released "+super.getClass());
		
	}
	public synchronized boolean isReady() {
		return ready;
	}

	public synchronized boolean isBusy() {
		return requestExecutor.getActiveCount()>=ASYNC_REQUEST;
	}
	
	public synchronized int getActiveCount(){
		return requestExecutor.getActiveCount();
	}
	
	public void stop(){
		ready = false;
		socket.close();
	}
	
	private void refreshOpenPorts() throws Exception{
		_selfLayerClient.discoverNodes();
		for(LayerNodeHandler handler : _selfLayerClient.getNodeHandlers()){
			if(handler.getHost().equals(InetAddress.getLocalHost())){
				getOpenedPorts().add(handler.getPort());
			}
		}
	}
	
	public void run() {		
		byte[] buffer = new byte[MSG_LENGHT];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		try{
			_selfLayerClient = selfLayerClientClass.getConstructor(ICoreLog.class).newInstance(new DefaultCoreLog());
			for(LayerNodeHandler handler : _selfLayerClient.getNodeHandlers()){
				if(handler.getHost().equals(InetAddress.getLocalHost())){
					getOpenedPorts().add(handler.getPort());
				}
			}
			int availablePort;
			do{
				availablePort = getAvailablePort();
				if(availablePort!=-1){
					try{
						socket = new DatagramSocket(availablePort);
						socket.setBroadcast(true);
						ready = true;
						System.out.println("Listening on "+availablePort);;
					}catch(BindException be){
						refreshOpenPorts();
					}
				}else{
					throw new Exception("Not available Port");
				}				
			}while(!ready);
			logger.info("Listening "+super.getClass()+" on "+availablePort);
			while(ready){
				socket.receive(packet);
				requestExecutor.execute(dispatcherClass.getConstructor(LayerNode.class,DatagramPacket.class).newInstance(this,packet));
				buffer = new byte[MSG_LENGHT];
				packet = new DatagramPacket(buffer, buffer.length);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public synchronized int getAvailablePort(){
		int port = minPortRange;
		Set<Integer> openedPorts = getOpenedPorts();
		while( port<= maxPortRange){
			if(!openedPorts.contains(port)){
				openedPorts.add(port);
				return port;
			}
			port++;
		}
		
		return -1;
	}
	
	public synchronized void releasePort(int port){
		getOpenedPorts().remove((Integer)port);
		
	}
	
	public Set<Integer> getOpenedPorts(){
		
		Set<Integer> openedPorts = OPENED_PORTS.get(dispatcherClass);
		if(openedPorts==null){
			openedPorts = new LinkedHashSet<Integer>();
			OPENED_PORTS.put(dispatcherClass, openedPorts);
		}
		return openedPorts;
	}
	public synchronized C getAvailableTcpConnection() {
		List<C> connections = TCP_CONN_MAP.get(tcpDispatcherClass);
		if(connections==null){
			connections = new ArrayList<C>();			
			TCP_CONN_MAP.put(tcpDispatcherClass, connections);
			return null;
		}
		if(connections.isEmpty()){
			return null;
		}	
		Collections.sort(connections, layerTcpComparator);
		return connections.get(0);
	}
	
	public synchronized C openTcpConnection() throws Exception {
		C conn = getAvailableTcpConnection();		
		if(conn == null || conn.isBusy()){
			conn = tcpConnectionClass.getConstructor(Class.class).newInstance(tcpDispatcherClass);
			logger.info("Opened TCP node "+conn.getClass()+" on "+conn.getPort());
			requestExecutor.execute(conn);
			TCP_CONN_MAP.get(tcpDispatcherClass).add(conn);
		}
		return conn;
	}

	public synchronized int getPort(){
		return socket.getLocalPort();
	}
	
	public synchronized InetAddress getHost(){
		try {
			return InetAddress.getLocalHost();
		}catch(Exception e){
			return socket.getInetAddress();
		}
	}
	
}
