package org.uag.netsim.core.layer.network;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;
import org.uag.netsim.core.layer.AbstractLayerRequestDispatcher;
import org.uag.netsim.core.layer.DefaultLayerTcpConnectionHandler;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpConnection;

public class NetworkLayerRequestDispatcher extends AbstractLayerRequestDispatcher<NetworkLayerRequest>{
	
	final static Logger logger = Logger.getLogger(NetworkLayerRequestDispatcher.class);

	public NetworkLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		super(node,packet);
	}

	protected Object resolveLayerOperation(NetworkLayerRequest request) throws Exception{
		NetworkLayerNode node = (NetworkLayerNode)super.node;
		NetworkLayerResponse response = new NetworkLayerResponse();
		response.setStatus(NetworkLayerResponse.STATUS.INVALID_REQUEST);
		if(request.getLayerPrimitive() == NetworkLayerRequest.LAYER_PRIMITIVE.NLME_SAP_OPEN){
			logger.debug("Requesting NLME_SAP "+node.getClass()+" on "+node.getPort());
			LayerTcpConnection tcpConnection = node.openNLMESAP();
			return new DefaultLayerTcpConnectionHandler(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.getActiveCount());
		}else if(request.getLayerPrimitive() == NetworkLayerRequest.LAYER_PRIMITIVE.NLME_SAP_CLOSE){			
			int port = Integer.parseInt(request.getFrame());
			if(node.closeNLMESAP(port)){
				response.setStatus(NetworkLayerResponse.STATUS.SUCCESS);
			}
		}else if(request.getLayerPrimitive() == NetworkLayerRequest.LAYER_PRIMITIVE.NLDE_SAP_OPEN){
			logger.debug("Requesting NLDE_SAP "+node.getClass()+" on "+node.getPort());
			LayerTcpConnection tcpConnection = node.openNLDESAP();
			return new DefaultLayerTcpConnectionHandler(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.getActiveCount());
		}else if(request.getLayerPrimitive() == NetworkLayerRequest.LAYER_PRIMITIVE.NLDE_SAP_CLOSE){
			int port = Integer.getInteger(request.getFrame());
			if(node.closeNLDESAP(port)){
				response.setStatus(NetworkLayerResponse.STATUS.SUCCESS);
			}
			
		}	
		return response;
	}
}
