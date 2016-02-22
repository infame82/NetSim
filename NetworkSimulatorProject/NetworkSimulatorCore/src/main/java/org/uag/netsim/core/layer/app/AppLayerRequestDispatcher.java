package org.uag.netsim.core.layer.app;

import java.net.DatagramPacket;

import org.uag.netsim.core.layer.*;

public class AppLayerRequestDispatcher extends AbstractLayerRequestDispatcher<AppLayerRequest>{

	
	public AppLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		super(node,packet);
	}

	protected Object resolveRequest(AppLayerRequest request) throws Exception{
		byte[] responseContent = null;
		AppLayerResponse response = new AppLayerResponse();
		response.setStatus(AppLayerResponse.STATUS.INVALID_REQUEST);
		if(request.getPrimitive() == AppLayerRequest.PRIMITIVE.REQUEST_NODE){
			LayerTcpConnection tcpConnection = node.openTcpConnection();
			return new DefaultLayerTcpConnectionHandler(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.getActiveCount());
		}else if(request.getPrimitive() == AppLayerRequest.PRIMITIVE.DISCOVER){
			return new DefaultLayerNodeHandler(node.getHost(),node.getPort(),node.getActiveCount());
		}
		return response;
	}

}
