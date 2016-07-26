package org.uag.netsim.core.layer.mac.MCPS;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.device.DataPackage;
import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpResponse;
import org.uag.netsim.core.layer.mac.MacLayerNode;

public class MCPSRequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<MCPSRequest,MCPSConfirm>{

	public MCPSRequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected MCPSConfirm resolveRequest(MCPSRequest request) {
		MCPSConfirm confirm = new MCPSConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		MacLayerNode node = (MacLayerNode)super.node;
		switch(request.getPrimitive()){
		case TRANSMISSION:
			try {
				DataPackage data = request.getData();
				data = node.transmission(request.getBeacons(),data);
				if(data!=null){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
					confirm.setData(data);
				}else{
					confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
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
