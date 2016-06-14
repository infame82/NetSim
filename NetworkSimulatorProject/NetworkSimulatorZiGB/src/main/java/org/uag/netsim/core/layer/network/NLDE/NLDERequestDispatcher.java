package org.uag.netsim.core.layer.network.NLDE;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class NLDERequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<NLDERequest,NLDEConfirm>{

	public NLDERequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected NLDEConfirm resolveRequest(NLDERequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
