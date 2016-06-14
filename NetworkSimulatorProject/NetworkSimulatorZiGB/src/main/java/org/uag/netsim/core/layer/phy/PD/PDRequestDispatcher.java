package org.uag.netsim.core.layer.phy.PD;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class PDRequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<PDRequest,PDConfirm>{

	public PDRequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected PDConfirm resolveRequest(PDRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
