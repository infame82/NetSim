package org.uag.netsim.core.layer.mac;

import java.net.DatagramPacket;

import org.uag.netsim.core.layer.AbstractLayerRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.app.AppLayerResponse;

public class MacLayerRequestDispatcher extends AbstractLayerRequestDispatcher<MacLayerRequest>{

	public MacLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		super(node,packet);
	}

	protected Object resolveLayerOperation(MacLayerRequest request) throws Exception{

		MacLayerResponse response = new MacLayerResponse();
		response.setStatus(AppLayerResponse.STATUS.INVALID_REQUEST);
		return response;
	}
}
