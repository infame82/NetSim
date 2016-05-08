package org.uag.netsim.core.layer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.uag.netsim.core.ObjectSerializer;

public abstract class AbstractLayerRequestDispatcher<R extends LayerRequest<? extends Enum<?>>> implements LayerRequestDispatcher{
	protected DatagramPacket packet;
	protected LayerNode node;
	
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
		if(request.getPrimitive() == LayerRequest.PRIMITIVE.REQUEST_NODE){
			LayerTcpConnection tcpConnection = node.openTcpConnection();
			return new DefaultLayerTcpConnectionHandler(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.getActiveCount());
		}else if(request.getPrimitive() == LayerRequest.PRIMITIVE.DISCOVER){
			return new DefaultLayerNodeHandler(node.getHost(),node.getPort(),node.getActiveCount());
		}
		return resolveLayerOperation(request);
	}

	protected abstract Object resolveLayerOperation(R request) throws Exception;
}
