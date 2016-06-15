package org.uag.netsim.core.layer.app;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;
import org.uag.netsim.core.layer.AbstractLayerRequestDispatcher;
import org.uag.netsim.core.layer.DefaultLayerTcpConnectionHandler;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpConnection;
import org.uag.netsim.core.layer.app.client.AppLayerRequest;
import org.uag.netsim.core.layer.app.client.AppLayerResponse;
import org.uag.netsim.core.layer.network.NetworkLayerResponse;

public class AppLayerRequestDispatcher extends AbstractLayerRequestDispatcher<AppLayerRequest>{

	final static Logger logger = Logger.getLogger(AppLayerRequestDispatcher.class);
	public AppLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		super(node,packet);
	}

	protected Object resolveLayerOperation(AppLayerRequest request) throws Exception{
		AppLayerNode node = (AppLayerNode)super.node;
		AppLayerResponse response = new AppLayerResponse();
		response.setStatus(AppLayerResponse.STATUS.INVALID_REQUEST);
		if(request.getLayerPrimitive() == AppLayerRequest.LAYER_PRIMITIVE.APSDE_SAP_OPEN){
			logger.debug("Requesting APSDE_SAP "+node.getClass()+" on "+node.getPort());
			LayerTcpConnection tcpConnection = node.openAPSDESAP();
			return new DefaultLayerTcpConnectionHandler(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.getActiveCount());
		}else if(request.getLayerPrimitive() == AppLayerRequest.LAYER_PRIMITIVE.APSDE_SAP_CLOSE){			
			int port = Integer.parseInt(request.getFrame());
			if(node.closeAPSDESAP(port)){
				response.setStatus(NetworkLayerResponse.STATUS.SUCCESS);
			}
		}else if(request.getLayerPrimitive() == AppLayerRequest.LAYER_PRIMITIVE.APSME_SAP_OPEN){
			logger.debug("Requesting APSME_SAP "+node.getClass()+" on "+node.getPort());
			LayerTcpConnection tcpConnection = node.openAPSMESAP();
			return new DefaultLayerTcpConnectionHandler(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.getActiveCount());
		}else if(request.getLayerPrimitive() == AppLayerRequest.LAYER_PRIMITIVE.APSME_SAP_CLOSE){
			int port = Integer.getInteger(request.getFrame());
			if(node.closeAPSMESAP(port)){
				response.setStatus(NetworkLayerResponse.STATUS.SUCCESS);
			}			
		}	
		return response;
	}

}
