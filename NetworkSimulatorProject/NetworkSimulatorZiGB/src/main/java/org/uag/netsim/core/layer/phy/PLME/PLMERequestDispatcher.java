package org.uag.netsim.core.layer.phy.PLME;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class PLMERequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<PLMERequest,PLMEConfirm>{

	public PLMERequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected PLMEConfirm resolveRequest(PLMERequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
