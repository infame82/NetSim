package org.uag.netsim.core.layer.app;

import java.io.IOException;

import org.uag.netsim.core.layer.AbstractLayerTcpConnection;
import org.uag.netsim.core.layer.LayerTcpRequestDispatcher;

public class AppLayerTcpConnection extends AbstractLayerTcpConnection<AppLayerTcpRequestDispatcher>{

	public AppLayerTcpConnection(int port) throws IOException {
		super(AppLayerTcpRequestDispatcher.class,port);
	}

}
