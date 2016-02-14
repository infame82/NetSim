package org.uag.netsim.core.layer.app;

import java.io.IOException;

import org.uag.netsim.core.layer.AbstractLayerTcpConnection;

public class AppLayerTcpConnection extends AbstractLayerTcpConnection{

	public AppLayerTcpConnection(int port) throws IOException {
		super(port);
	}

}
