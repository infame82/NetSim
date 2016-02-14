package org.uag.netsim.core.layer.app;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.layer.AbstractLayerRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpConnection;

public class AppLayerRequestDispatcher extends AbstractLayerRequestDispatcher{

	
	public AppLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		super(node,packet);
	}
	
	@Override
	public void resolveRequest(Object obj) throws Exception {
		if(obj instanceof AppLayerRequest){
			resolveRequest((AppLayerRequest)obj);
		}		
	}

	private void resolveRequest(AppLayerRequest request) throws Exception{
		byte[] responseContent = null;
		AppLayerResponse response = new AppLayerResponse();
		response.setStatus(AppLayerResponse.STATUS.INVALID_REQUEST);
		if(request.getPrimitive() == AppLayerRequest.PRIMITIVE.REQUEST_APP_NODE){
			response = requestAppNode();
			responseContent = ObjectSerializer.serialize(response);
		}else if(request.getPrimitive() == AppLayerRequest.PRIMITIVE.DISCOVER){
			AppLayerNodeHandler handler = new AppLayerNodeHandler(node.getHost(),node.getPort(),node.getActiveCount());
			responseContent = ObjectSerializer.serialize(handler);
		}

		int port = node.getAvailablePort();
		DatagramSocket socket = new DatagramSocket(port);
		DatagramPacket packet = new DatagramPacket(responseContent,
				responseContent.length, this.packet.getAddress(), this.packet.getPort());
		socket.send(packet);
		socket.close();
		node.releasePort(port);
	}
	
	private AppLayerResponse requestAppNode() throws Exception{
		AppLayerResponse response = new AppLayerResponse();
		response.setStatus(AppLayerResponse.STATUS.SUCCESS);
		LayerTcpConnection conn = node.openTcpConnection();
		return response;
	}

	



}
