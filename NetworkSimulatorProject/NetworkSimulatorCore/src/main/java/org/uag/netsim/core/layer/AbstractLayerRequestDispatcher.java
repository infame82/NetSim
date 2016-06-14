package org.uag.netsim.core.layer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.apache.log4j.Logger;
import org.uag.netsim.core.ObjectSerializer;

public abstract class AbstractLayerRequestDispatcher<R extends LayerRequest<? extends Enum<?>>> implements LayerRequestDispatcher{
	protected DatagramPacket packet;
	protected LayerNode node;
	final static Logger logger = Logger.getLogger(AbstractLayerRequestDispatcher.class);
	
	public AbstractLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		this.packet = packet;
		this.node = node;
	}
	public void run() {
		try {
			Object obj = ObjectSerializer.unserialize(packet.getData());
			resolveRequest(obj);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@SuppressWarnings("unchecked")
	public void resolveRequest(Object obj) throws Exception {
		if(obj instanceof LayerRequest){
			sendMsg(ObjectSerializer.serialize(resolveRequest((R)obj)));
		}
	}



	private void sendMsg(byte[] responseContent) throws IOException {
		DatagramSocket socket  = new DatagramSocket();
		DatagramPacket packet = new DatagramPacket(responseContent,
				responseContent.length, this.packet.getAddress(), this.packet.getPort());
		socket.send(packet);
		socket.close();
	}
	
	protected Object resolveRequest(R request) throws Exception{
		logger.debug("Resolving request "+node.getClass()+" on "+node.getPort());
		if(request.getPrimitive() == LayerRequest.PRIMITIVE.REQUEST_NODE){
			logger.debug("Requesting TCP node "+node.getClass()+" on "+node.getPort());
			LayerTcpConnection tcpConnection = node.openTcpConnection();
			return new DefaultLayerTcpConnectionHandler(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.getActiveCount());
		}else if(request.getPrimitive() == LayerRequest.PRIMITIVE.DISCOVER){
			logger.debug("Discover request "+node.getClass()+" on "+node.getPort());
			return new DefaultLayerNodeHandler(node.getHost(),node.getPort(),node.getActiveCount());
		}else if(request.getPrimitive() == LayerRequest.PRIMITIVE.CLOSE_NODE){
			
		}
		return resolveLayerOperation(request);
	}

	protected abstract Object resolveLayerOperation(R request) throws Exception;
}
