package org.uag.netsim.core.layer.mac;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;
import org.uag.netsim.core.layer.AbstractLayerRequestDispatcher;
import org.uag.netsim.core.layer.DefaultLayerTcpConnectionHandler;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpConnection;
import org.uag.netsim.core.layer.network.NetworkLayerResponse;

public class MacLayerRequestDispatcher extends AbstractLayerRequestDispatcher<MacLayerRequest>{

	final static Logger logger = Logger.getLogger(MacLayerRequestDispatcher.class);
	public MacLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		super(node,packet);
	}

	protected Object resolveLayerOperation(MacLayerRequest request) throws Exception{
		MacLayerNode node = (MacLayerNode)super.node;
		MacLayerResponse response = new MacLayerResponse();
		response.setStatus(MacLayerResponse.STATUS.INVALID_REQUEST);
		if(request.getLayerPrimitive() == MacLayerRequest.LAYER_PRIMITIVE.MCPS_SAP_OPEN){
			logger.debug("Requesting MCPS_SAP "+node.getClass()+" on "+node.getPort());
			LayerTcpConnection tcpConnection = node.openMCPSSAP();
			return new DefaultLayerTcpConnectionHandler(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.getActiveCount());
		}else if(request.getLayerPrimitive() == MacLayerRequest.LAYER_PRIMITIVE.MCPS_SAP_CLOSE){			
			int port = Integer.parseInt(request.getFrame());
			if(node.closeMCPSSAP(port)){
				response.setStatus(NetworkLayerResponse.STATUS.SUCCESS);
			}
		}else if(request.getLayerPrimitive() == MacLayerRequest.LAYER_PRIMITIVE.MLME_SAP_OPEN){
			logger.debug("Requesting MLME_SAP "+node.getClass()+" on "+node.getPort());
			LayerTcpConnection tcpConnection = node.openMLMESAP();
			return new DefaultLayerTcpConnectionHandler(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.getActiveCount());
		}else if(request.getLayerPrimitive() == MacLayerRequest.LAYER_PRIMITIVE.MLME_SAP_CLOSE){
			int port = Integer.getInteger(request.getFrame());
			if(node.closeMLMESAP(port)){
				response.setStatus(NetworkLayerResponse.STATUS.SUCCESS);
			}			
		}	
		return response;
	}
}
