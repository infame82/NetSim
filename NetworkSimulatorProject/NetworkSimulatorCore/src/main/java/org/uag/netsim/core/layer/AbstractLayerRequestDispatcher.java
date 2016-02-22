package org.uag.netsim.core.layer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.layer.app.AppLayerRequest;
import org.uag.netsim.core.layer.app.AppLayerResponse;

public abstract class AbstractLayerRequestDispatcher<R extends LayerRequest> implements LayerRequestDispatcher{
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

	public void resolveRequest(Object obj) throws Exception {
		if(obj instanceof LayerRequest){
			sendMsg(ObjectSerializer.serialize(resolveRequest((R)obj)));
		}
	}



	private void sendMsg(byte[] responseContent) throws IOException {
		int port = node.getAvailablePort();
		DatagramSocket socket = new DatagramSocket(port);
		DatagramPacket packet = new DatagramPacket(responseContent,
				responseContent.length, this.packet.getAddress(), this.packet.getPort());
		socket.send(packet);
		socket.close();
		node.releasePort(port);
	}

	protected abstract Object resolveRequest(R request) throws Exception;
}
