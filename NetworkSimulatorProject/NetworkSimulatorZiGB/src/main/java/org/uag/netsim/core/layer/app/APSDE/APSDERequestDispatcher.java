package org.uag.netsim.core.layer.app.APSDE;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpResponse;
import org.uag.netsim.core.layer.app.AppLayerNode;

public class APSDERequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<APSDERequest,APSDEConfirm>{
	
	public APSDERequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected APSDEConfirm resolveRequest(APSDERequest request) {
		APSDEConfirm confirm = new APSDEConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		AppLayerNode node =  (AppLayerNode)super.node;
		
		switch(request.getPrimitive()){
		case  TRANSMIT:
			try {
				confirm.setDataPack(node.transmitData(request.getBeacon(), request.getData()));
				confirm.setStatus(confirm.getDataPack()!=null?LayerTcpResponse.STATUS.SUCCESS:LayerTcpResponse.STATUS.ERROR);
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
