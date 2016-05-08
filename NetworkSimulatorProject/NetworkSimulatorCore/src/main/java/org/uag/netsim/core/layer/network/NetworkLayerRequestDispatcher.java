package org.uag.netsim.core.layer.network;

import java.net.DatagramPacket;

import org.uag.netsim.core.layer.AbstractLayerRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class NetworkLayerRequestDispatcher extends AbstractLayerRequestDispatcher<NetworkLayerRequest>{

	public NetworkLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		super(node,packet);
	}

	protected Object resolveLayerOperation(NetworkLayerRequest request) throws Exception{
		NetworkLayerResponse response = new NetworkLayerResponse();
		response.setStatus(NetworkLayerResponse.STATUS.INVALID_REQUEST);
		return response;
	}
}
