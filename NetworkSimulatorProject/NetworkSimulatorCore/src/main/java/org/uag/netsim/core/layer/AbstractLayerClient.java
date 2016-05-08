package org.uag.netsim.core.layer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
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

public abstract class AbstractLayerClient<LR extends LayerRequest<? extends Enum<?>>> implements LayerClient{
	
	public ICoreLog log;
	protected final List<LayerNodeHandler> HANDLERS = new ArrayList<LayerNodeHandler>();
	private Class<LR> layerRequestClass;
	
	class DiscoverNode implements Runnable{

		private int port;
		public DiscoverNode(int port){
			this.port = port;
		}

		public void run() {
			DatagramSocket socket = null;
			try {
				byte[] buffer = new byte[512];
				DatagramPacket rpacket = new DatagramPacket(buffer, buffer.length);
				
				socket = new DatagramSocket();
				//socket.setBroadcast(true);
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
			}catch(SocketTimeoutException ste){
				//ste.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(socket!=null){
					socket.close();
					socket.disconnect();
				}
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
				.newFixedThreadPool(100);
		for(int i=getMinPort();i<=getMaxPort();i++){
			requestExecutor.execute(new DiscoverNode(i));
		}
		//while(!(requestExecutor.getActiveCount()==0));
		requestExecutor.shutdown();
		while (!requestExecutor.awaitTermination(10, TimeUnit.SECONDS));
		log.sendInfo("Discovered "+getNodeHandlers().size()+" nodes ["+super.getClass()+"]");
		
		
	}
	
	public abstract int getMinPort();
	public abstract int getMaxPort();
	
	public LayerTcpConnectionHandler requestTcpNode() throws Exception{
		if(getNodeHandlers().isEmpty()){
			discoverNodes();
		}
		if(getNodeHandlers().isEmpty()){
			throw new Exception("Not available Nodes");
		}
		LayerTcpConnectionHandler tcpHandler = null;
		Collections.sort(getNodeHandlers(), layerHandlerComparator);
		LayerNodeHandler handler = getNodeHandlers().get(0);
		DatagramSocket socket = null;
		try {
			byte[] buffer = new byte[512];
			DatagramPacket rpacket = new DatagramPacket(buffer, buffer.length);
			socket = new DatagramSocket();//handler.getPort(),handler.getHost());
			byte[] data = getTcpNodeRequest();
			DatagramPacket packet = new DatagramPacket(data, data.length, handler.getHost(),handler.getPort());
			socket.send(packet);
			socket.receive(rpacket);
			tcpHandler = (LayerTcpConnectionHandler)ObjectSerializer.unserialize(rpacket.getData());
		}catch(SocketTimeoutException sto){
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(socket!=null){
				socket.close();
				socket.disconnect();
			}
		}
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


	private byte[] getTcpNodeRequest() throws Exception {
		LR request = layerRequestClass.getConstructor().newInstance();
		request.setPrimitive(LR.PRIMITIVE.REQUEST_NODE);
		byte[] data = ObjectSerializer.serialize(request);
		return data;
	}

}
