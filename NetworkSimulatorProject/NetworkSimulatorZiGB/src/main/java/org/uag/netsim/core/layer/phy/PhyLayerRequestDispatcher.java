package org.uag.netsim.core.layer.phy;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;
import org.uag.netsim.core.layer.AbstractLayerRequestDispatcher;
import org.uag.netsim.core.layer.DefaultLayerTcpConnectionHandler;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpConnection;

public class PhyLayerRequestDispatcher extends AbstractLayerRequestDispatcher<PhyLayerRequest>{
	
	final static Logger logger = Logger.getLogger(PhyLayerRequestDispatcher.class);

	public PhyLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		super(node,packet);
	}

	protected Object resolveLayerOperation(PhyLayerRequest request) throws Exception{
		PhyLayerNode node = (PhyLayerNode)super.node;
		PhyLayerResponse response = new PhyLayerResponse();
		response.setStatus(PhyLayerResponse.STATUS.INVALID_REQUEST);
		if(request.getLayerPrimitive() == PhyLayerRequest.LAYER_PRIMITIVE.PD_SAP_OPEN){
			logger.debug("Requesting PD_SAP "+node.getClass()+" on "+node.getPort());
			LayerTcpConnection tcpConnection = node.openPDSAP();
			return new DefaultLayerTcpConnectionHandler(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.getActiveCount());
		}else if(request.getLayerPrimitive() == PhyLayerRequest.LAYER_PRIMITIVE.PD_SAP_CLOSE){			
			int port = Integer.parseInt(request.getFrame());
			if(node.closePDSAP(port)){
				response.setStatus(PhyLayerResponse.STATUS.SUCCESS);
			}
		}else if(request.getLayerPrimitive() == PhyLayerRequest.LAYER_PRIMITIVE.PLME_SAP_OPEN){
			logger.debug("Requesting NLDE_SAP "+node.getClass()+" on "+node.getPort());
			LayerTcpConnection tcpConnection = node.openPLMESAP();
			return new DefaultLayerTcpConnectionHandler(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.getActiveCount());
		}else if(request.getLayerPrimitive() == PhyLayerRequest.LAYER_PRIMITIVE.PLME_SAP_CLOSE){
			int port = Integer.parseInt(request.getFrame());
			if(node.closePLMESAP(port)){
				response.setStatus(PhyLayerResponse.STATUS.SUCCESS);
			}
			
		}
		return response;
	}
}
