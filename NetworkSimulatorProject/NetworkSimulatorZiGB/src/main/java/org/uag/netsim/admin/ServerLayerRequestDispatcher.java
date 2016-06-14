package org.uag.netsim.admin;

import java.net.DatagramPacket;

import org.uag.netsim.core.layer.AbstractLayerRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;


public class ServerLayerRequestDispatcher extends AbstractLayerRequestDispatcher<ServerLayerRequest>
implements ServerLayerCommands{
	

	public ServerLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		super(node,packet);
	}

	protected Object resolveLayerOperation(ServerLayerRequest request) throws Exception{
		ServerLayerResponse response = new ServerLayerResponse();
		response.setStatus(ServerLayerResponse.STATUS.INVALID_REQUEST);
		if(request.getLayerPrimitive() == ServerLayerRequest.LAYER_PRIMITIVE.START_NODES){
			if(startNodes()){
				response.setStatus(ServerLayerResponse.STATUS.SUCCESS);
			}else{
				response.setStatus(ServerLayerResponse.STATUS.ERROR);
			}
			
		}
		if(request.getLayerPrimitive() == ServerLayerRequest.LAYER_PRIMITIVE.STOP_NODES){
			if(stopNodes()){
				response.setStatus(ServerLayerResponse.STATUS.SUCCESS);
			}else{
				response.setStatus(ServerLayerResponse.STATUS.ERROR);
			}
			
		}
			
		return response;
	}

	
	public boolean startNodes()  throws Exception{
		ServerLayerNode serverNode = (ServerLayerNode)super.node;
		return serverNode.startNodes();
	}

	@Override
	public boolean stopNodes() throws Exception {
		ServerLayerNode serverNode = (ServerLayerNode)super.node;
		return serverNode.stopNodes();
	}
}
