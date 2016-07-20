package org.uag.netsim.core.layer.APSME;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpResponse;
import org.uag.netsim.core.layer.app.AppLayerNode;
import org.uag.netsim.core.layer.app.APSME.APSMEConfirm;
import org.uag.netsim.core.layer.app.APSME.APSMERequest;

public class APSMERequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<APSMERequest,APSMEConfirm>{

	public APSMERequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected APSMEConfirm resolveRequest(APSMERequest request) {
		APSMEConfirm confirm = new APSMEConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		AppLayerNode node =  (AppLayerNode)super.node;
		
		switch(request.getPrimitive()){
		case  ADD_NEIGHBOR:
			try {
				if(node.addNeigbor(request.getBeacons().get(0),request.getBeacons().get(1))){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		
		return confirm;
	}
}
