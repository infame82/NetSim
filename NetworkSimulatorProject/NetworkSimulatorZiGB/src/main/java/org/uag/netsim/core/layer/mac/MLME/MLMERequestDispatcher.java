package org.uag.netsim.core.layer.mac.MLME;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class MLMERequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<MLMERequest,MLMEConfirm>{

	public MLMERequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected MLMEConfirm resolveRequest(MLMERequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
