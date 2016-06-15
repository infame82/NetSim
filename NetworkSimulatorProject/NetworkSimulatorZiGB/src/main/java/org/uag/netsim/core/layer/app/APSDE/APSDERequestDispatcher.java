package org.uag.netsim.core.layer.app.APSDE;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class APSDERequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<APSDERequest,APSDEConfirm>{
	
	public APSDERequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected APSDEConfirm resolveRequest(APSDERequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
