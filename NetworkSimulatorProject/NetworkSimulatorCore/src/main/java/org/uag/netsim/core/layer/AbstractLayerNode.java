package org.uag.netsim.core.layer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public abstract class AbstractLayerNode<D extends LayerRequestDispatcher,C extends LayerTcpConnection> implements LayerNode {
	
	private final Class<D> dispatcherClass;
	private final Class<C> tcpConnectionClass;
	
	
	public static final Map<Class,List<Integer>> OPENED_PORTS = 
			new HashMap<Class,List<Integer>>();
	
	public static final Map<Class,List<LayerTcpConnection>> TCP_CONN_MAP = 
			new HashMap<Class,List<LayerTcpConnection>>();
	
	class LayerTcpComparator implements Comparator<LayerTcpConnection>{

		public int compare(LayerTcpConnection o1, LayerTcpConnection o2) {
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
	
	public AbstractLayerNode(Class<D> dispatcherClass,Class<C> tcpConnectionClass){
		this.dispatcherClass = dispatcherClass;
		this.tcpConnectionClass = tcpConnectionClass;
	}
	
	
	public void init() throws Exception{
		ready = false;
		int availablePort = getAvailablePort();
		if(availablePort==-1){
			throw new Exception("Not available Port");
		}
		socket = new DatagramSocket(availablePort);
		requestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(10);
	}
	
	public void release(){
		ready = false;
		if(socket!=null && socket.isConnected()){
			socket.close();
		}
		requestExecutor.shutdownNow();
		releasePort(socket.getLocalPort());
	}
	public synchronized boolean isReady() {
		return ready;
	}

	public synchronized boolean isBusy() {
		return requestExecutor.getActiveCount()>=10;
	}
	
	public synchronized int getActiveCount(){
		return requestExecutor.getActiveCount();
	}
	
	public void stop(){
		ready = false;
		socket.close();
	}
	
	public void run() {
		DatagramPacket packet = null;
		byte[] buffer = null;
		ready = true;
		try{
			while(ready){
				buffer = new byte[MSG_LENGHT];
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				LayerRequestDispatcher dispatcher = dispatcherClass.getConstructor(LayerNode.class,DatagramPacket.class).newInstance(this,packet);
				requestExecutor.execute(dispatcher);
			}
		}catch(Exception e){
		}
	}
	
	public synchronized int getAvailablePort(){
		int port = minPortRange;
		List<Integer> openedPorts = getOpenedPorts();
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
	
	private List<Integer> getOpenedPorts(){
		List<Integer> openedPorts = OPENED_PORTS.get(getClass());
		if(openedPorts==null){
			openedPorts = new ArrayList<Integer>();
			OPENED_PORTS.put(getClass(), openedPorts);
		}
		return openedPorts;
	}
	public synchronized LayerTcpConnection getAvailableTcpConnection() {
		List<LayerTcpConnection> connections = TCP_CONN_MAP.get(tcpConnectionClass);
		if(connections==null){
			connections = new ArrayList<LayerTcpConnection>();			
			TCP_CONN_MAP.put(tcpConnectionClass, connections);
			return null;
		}
		if(connections.isEmpty()){
			return null;
		}	
		Collections.sort(connections, layerTcpComparator);
		return connections.get(0);
	}
	
	public synchronized LayerTcpConnection openTcpConnection() throws Exception {
		LayerTcpConnection conn = getAvailableTcpConnection();
		
		if(conn == null){
			int port = getAvailablePort();
			if(port == -1){
				throw new Exception("Not available port");
			}
			conn = tcpConnectionClass.getConstructor(int.class).newInstance(port);
			requestExecutor.execute(conn);
			TCP_CONN_MAP.get(tcpConnectionClass).add(conn);
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
