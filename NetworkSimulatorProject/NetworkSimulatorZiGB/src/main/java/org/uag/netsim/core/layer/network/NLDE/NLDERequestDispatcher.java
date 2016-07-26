package org.uag.netsim.core.layer.network.NLDE;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.device.DataPackage;
import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpResponse;
import org.uag.netsim.core.layer.network.NetworkLayerNode;

public class NLDERequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<NLDERequest,NLDEConfirm>{

	public NLDERequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected NLDEConfirm resolveRequest(NLDERequest request) {
		NLDEConfirm confirm = new NLDEConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		NetworkLayerNode node =  (NetworkLayerNode)super.node;
		switch(request.getPrimitive()){
		case RETRANSMISSION:
			try {
				DataPackage data = node.retransmitData(request.getBeacons(), request.getPack());
				if(data!=null){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
					confirm.setData(data);
				}else{
					confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
				}
			} catch (Exception e) {
				confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
				e.printStackTrace();
			}
			break;
		case TRANSMISSION:
			try {
				DataPackage data = node.transmitData(request.getBeacons(), request.getData());
				if(data!=null){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
					confirm.setData(data);
				}else{
					confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
				}
			} catch (Exception e) {
				confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
				e.printStackTrace();
			}
			break;
			default:
				break;
		}
		
		return confirm;
	}

}
