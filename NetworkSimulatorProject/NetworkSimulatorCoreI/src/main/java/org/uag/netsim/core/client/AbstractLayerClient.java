package org.uag.netsim.core.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.uag.netsim.core.DefaultCoreLog;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.layer.LayerClient;
import org.uag.netsim.core.layer.LayerNodeHandler;
import org.uag.netsim.core.layer.LayerRequest;
import org.uag.netsim.core.layer.LayerTcpConnectionHandler;

public abstract class AbstractLayerClient<LR extends LayerRequest<? extends Enum<?>>> implements LayerClient{
	
	public ICoreLog log;
	protected final List<LayerNodeHandler> HANDLERS = new ArrayList<LayerNodeHandler>();
	private Class<LR> layerRequestClass;
	
	class DiscoverNode implements Runnable{

		private DatagramSocket socket;
		private int port;
		public DiscoverNode(int port){
			this.port = port;
		}

		public void run() {
			try {
				byte[] buffer = new byte[512];
				DatagramPacket rpacket = new DatagramPacket(buffer, buffer.length);
				
				socket = new DatagramSocket();
				socket.setSoTimeout(1000);
				InetSocketAddress broadcastAddress = new InetSocketAddress( "255.255.255.255", port );
				byte[] data = getDiscoverRequest();
				DatagramPacket packet = new DatagramPacket(data, data.length, broadcastAddress);
				socket.send(packet);
				socket.receive(rpacket);
				LayerNodeHandler handler = (LayerNodeHandler)ObjectSerializer.unserialize(rpacket.getData());
				synchronized(getNodeHandlers()){
					getNodeHandlers().add(handler);
				}
			}catch (Exception e) {
			}finally{
				release();
			}
			
		}
		
		public void release(){
			if(socket!=null){
				socket.close();
				socket.disconnect();
			}
		}
	}
	
	class LayerNodeHandlerComparator implements Comparator<LayerNodeHandler>{

		public int compare(LayerNodeHandler o1, LayerNodeHandler o2) {
			if(o1.getNoise()<o2.getNoise()){
				return -1;
			}
			if(o1.getNoise()>o2.getNoise()){
				return 1;
			}
			return 0;
		}
		
	}
	
	private LayerNodeHandlerComparator layerHandlerComparator;
	
	public AbstractLayerClient(Class<LR> layerRequestClass) throws Exception{
		this(layerRequestClass,new DefaultCoreLog());
	}
	
	public AbstractLayerClient(Class<LR> layerRequestClass,ICoreLog log) throws Exception{
		this.log = log;
		this.layerRequestClass = layerRequestClass;
		if(log==null){
			this.log = new DefaultCoreLog();
		}
		enableLog(true);
		discoverNodes();
		layerHandlerComparator = new LayerNodeHandlerComparator();
	}
	
	public void discoverNodes() throws Exception {
		getNodeHandlers().clear();
		log.sendDebug("Discovering Nodes ["+this.getClass()+"]");
		ThreadPoolExecutor requestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(10);
		List<DiscoverNode> dns = new ArrayList<DiscoverNode>();
		for(int i=getMinPort();i<=getMaxPort();i++){
			DiscoverNode dn = new DiscoverNode(i);
			dns.add(dn);
			requestExecutor.execute(dn);
		}
		requestExecutor.shutdown();
		while (!requestExecutor.awaitTermination(2, TimeUnit.SECONDS));
		for(DiscoverNode dn:dns){
			dn.release();
		}
		log.sendInfo("Discovered "+getNodeHandlers().size()+" nodes ["+super.getClass()+"]");
		
		
	}
	
	public abstract int getMinPort();
	public abstract int getMaxPort();
	
	public LayerTcpConnectionHandler requestTcpNode() throws Exception{
		LR request = layerRequestClass.getConstructor().newInstance();
		request.setPrimitive(LR.PRIMITIVE.REQUEST_NODE);
		DatagramPacket response = sendRequest(request);
		LayerTcpConnectionHandler tcpHandler = (LayerTcpConnectionHandler)ObjectSerializer.unserialize(response.getData());
		return tcpHandler;
	}

	public void enableLog(boolean enable){
		log.setEnableLog(enable);
	}
	
	public List<LayerNodeHandler> getNodeHandlers() {
		return HANDLERS;
	}
	
	private byte[] getDiscoverRequest() throws Exception{
		LR request = layerRequestClass.getConstructor().newInstance();
		request.setPrimitive(LR.PRIMITIVE.DISCOVER);
		byte[] data = ObjectSerializer.serialize(request);
		return data;
	}
	
	public DatagramPacket sendRequest(LR request) throws Exception{
		discoverNodes();
		if(HANDLERS.size()==0){
			throw new Exception("No server node found");
		}		
		Collections.sort(getNodeHandlers(), layerHandlerComparator);
		LayerNodeHandler handler = getNodeHandlers().get(0);
		DatagramSocket socket = null;
		byte[] buffer = new byte[512];
		DatagramPacket rpacket = new DatagramPacket(buffer, buffer.length);
		try {
			
			socket = new DatagramSocket();//handler.getPort(),handler.getHost());
			byte[] data = ObjectSerializer.serialize(request);
			DatagramPacket packet = new DatagramPacket(data, data.length, handler.getHost(),handler.getPort());
			socket.send(packet);
			socket.receive(rpacket);
		}finally{
			if(socket!=null){
				socket.close();
				socket.disconnect();
			}
		}
		return rpacket;
	}

}
