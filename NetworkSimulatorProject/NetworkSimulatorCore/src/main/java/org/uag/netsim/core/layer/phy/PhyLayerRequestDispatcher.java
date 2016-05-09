package org.uag.netsim.core.layer.phy;

import java.net.DatagramPacket;

import org.uag.netsim.core.layer.AbstractLayerRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class PhyLayerRequestDispatcher extends AbstractLayerRequestDispatcher<PhyLayerRequest>{

	public PhyLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		super(node,packet);
	}

	protected Object resolveLayerOperation(PhyLayerRequest request) throws Exception{
		PhyLayerResponse response = new PhyLayerResponse();
		response.setStatus(PhyLayerResponse.STATUS.INVALID_REQUEST);
		return response;
	}
}
