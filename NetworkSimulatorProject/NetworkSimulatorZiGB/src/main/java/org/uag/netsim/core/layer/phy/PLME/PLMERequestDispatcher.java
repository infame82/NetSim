package org.uag.netsim.core.layer.phy.PLME;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpResponse;
import org.uag.netsim.core.layer.phy.PhyLayerNode;

public class PLMERequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<PLMERequest,PLMEConfirm>{

	public PLMERequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected PLMEConfirm resolveRequest(PLMERequest request) {
		PLMEConfirm confirm = new PLMEConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		PhyLayerNode node = (PhyLayerNode)super.node;
		if( request.getPrimitive() == PLMERequest.PRIMITIVE.GET_CHANNELS ){
			confirm.setChannels(node.getChannels());
		}else if( request.getPrimitive() == PLMERequest.PRIMITIVE.INCREASE_ENERGY ){
			if(node.increaseEnergyLevel(request.getChannel())){
				confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
			}
		}else if( request.getPrimitive() == PLMERequest.PRIMITIVE.TRANSMIT ){
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
