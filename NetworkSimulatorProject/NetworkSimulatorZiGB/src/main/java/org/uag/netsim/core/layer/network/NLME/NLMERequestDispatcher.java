package org.uag.netsim.core.layer.network.NLME;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class NLMERequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<NLMERequest,NLMEConfirm>{

	public NLMERequestDispatcher(LayerNode node,Socket socket) throws IOException {
		super(node,socket);
	}

	@Override
	protected NLMEConfirm resolveRequest(NLMERequest request) {
		
		return null;
	}

}
