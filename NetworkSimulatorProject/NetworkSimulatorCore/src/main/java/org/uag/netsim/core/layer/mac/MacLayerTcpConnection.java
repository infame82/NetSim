package org.uag.netsim.core.layer.mac;

import java.io.IOException;

import org.uag.netsim.core.layer.DefaultLayerTcpConnection;

public class MacLayerTcpConnection extends DefaultLayerTcpConnection<MacLayerTcpRequestDispatcher>{

	public MacLayerTcpConnection() throws IOException {
		super(MacLayerTcpRequestDispatcher.class);
	}

}
