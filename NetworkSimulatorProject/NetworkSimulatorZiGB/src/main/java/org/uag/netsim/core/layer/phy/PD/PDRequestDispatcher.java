package org.uag.netsim.core.layer.phy.PD;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpResponse;
import org.uag.netsim.core.layer.phy.PhyLayerNode;

public class PDRequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<PDRequest,PDConfirm>{

	public PDRequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected PDConfirm resolveRequest(PDRequest request) {
		PDConfirm confirm = new PDConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		PhyLayerNode node = (PhyLayerNode)super.node;
		if( request.getPrimitive() == PDRequest.PRIMITIVE.TRANSMIT ){
			try {
				if(node.transmit(request.getChannel(), request.getBeacons(), request.getData())){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
				}
			} catch (Exception e) {
				confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
				e.printStackTrace();
			}
		}
		return confirm;
	}
}
