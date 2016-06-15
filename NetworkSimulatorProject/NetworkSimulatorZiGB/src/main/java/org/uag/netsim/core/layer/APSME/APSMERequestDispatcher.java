package org.uag.netsim.core.layer.APSME;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class APSMERequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<APSMERequest,APSMEConfirm>{

	public APSMERequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected APSMEConfirm resolveRequest(APSMERequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
