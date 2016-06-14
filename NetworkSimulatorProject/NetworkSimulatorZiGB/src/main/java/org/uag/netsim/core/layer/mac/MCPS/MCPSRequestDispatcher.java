package org.uag.netsim.core.layer.mac.MCPS;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class MCPSRequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<MCPSRequest,MCPSConfirm>{

	public MCPSRequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}

	@Override
	protected MCPSConfirm resolveRequest(MCPSRequest request) {
		return null;
	}
}
