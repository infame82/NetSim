package org.uag.netsim.core.layer.app;

import java.net.DatagramPacket;

import org.uag.netsim.core.layer.AbstractLayerRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.app.client.AppLayerRequest;
import org.uag.netsim.core.layer.app.client.AppLayerResponse;

public class AppLayerRequestDispatcher extends AbstractLayerRequestDispatcher<AppLayerRequest>{

	
	public AppLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		super(node,packet);
	}

	protected Object resolveLayerOperation(AppLayerRequest request) throws Exception{
		AppLayerResponse response = new AppLayerResponse();
		response.setStatus(AppLayerResponse.STATUS.INVALID_REQUEST);
		return response;
	}

}