package org.uag.netsim.core.layer;

import java.io.IOException;
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

import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.layer.app.AppLayerNode;

public abstract class AbstractLayerClient implements LayerClient{
	
	public static final List<LayerNodeHandler> HANDLERS = new ArrayList<LayerNodeHandler>();
	
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
				socket.setSoTimeout(1000);
				InetSocketAddress broadcastAddress = new InetSocketAddress( "255.255.255.255", port );
				byte[] data = getDiscoverRequest();
				DatagramPacket packet = new DatagramPacket(data, data.length, broadcastAddress);
				socket.send(packet);
				socket.receive(rpacket);
				LayerNodeHandler handler = (LayerNodeHandler)ObjectSerializer.unserialize(rpacket.getData());
				synchronized(HANDLERS){
					HANDLERS.add(handler);
				}
			}catch(SocketTimeoutException ste){
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
	
	public AbstractLayerClient() throws Exception{
		discoverNodes();
		layerHandlerComparator = new LayerNodeHandlerComparator();
	}
	
	public void discoverNodes() throws Exception {
		HANDLERS.clear();
		ThreadPoolExecutor requestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(100);
		for(int i=AppLayerNode.MIN_PORT_RANGE;i<=AppLayerNode.MAX_PORT_RANGE;i++){
			requestExecutor.execute(new DiscoverNode(i));
		}
		while(!(requestExecutor.getActiveCount()==0));
		requestExecutor.shutdown();
		
	}
	
	public LayerTcpNodeHandler requestTcpNode() throws Exception{
		if(HANDLERS.isEmpty()){
			discoverNodes();
		}
		if(HANDLERS.isEmpty()){
			throw new Exception("Not available Nodes");
		}
		LayerTcpNodeHandler tcpHandler = null;
		Collections.sort(HANDLERS, layerHandlerComparator);
		LayerNodeHandler handler = HANDLERS.get(0);
		DatagramSocket socket = null;
		try {
			byte[] buffer = new byte[512];
			DatagramPacket rpacket = new DatagramPacket(buffer, buffer.length);
			socket = new DatagramSocket(handler.getPort(),handler.getHost());
			byte[] data = getTcpNodeRequest();
			DatagramPacket packet = new DatagramPacket(data, data.length, handler.getHost(),handler.getPort());
			socket.send(packet);
			socket.receive(rpacket);
			tcpHandler = (LayerTcpNodeHandler)ObjectSerializer.unserialize(rpacket.getData());
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

	public abstract byte[] getDiscoverRequest() throws IOException;
	public abstract byte[] getTcpNodeRequest() throws IOException;

}
