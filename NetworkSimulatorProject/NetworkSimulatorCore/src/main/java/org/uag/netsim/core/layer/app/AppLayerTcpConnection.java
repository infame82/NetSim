package org.uag.netsim.core.layer.app;

import java.io.IOException;

import org.uag.netsim.core.layer.DefaultLayerTcpConnection;

public class AppLayerTcpConnection extends DefaultLayerTcpConnection<AppLayerTcpRequestDispatcher>{

	public AppLayerTcpConnection() throws IOException {
		super(AppLayerTcpRequestDispatcher.class);
	}

}
