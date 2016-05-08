package org.uag.netsim.core.layer.mgmt;

import org.uag.netsim.core.layer.DefaultLayerTcpConnection;

import java.io.IOException;

/**
 * Created by david on 21/02/16.
 */
public class MgmtLayerTcpConnection extends DefaultLayerTcpConnection<MgmtLayerTcpRequestDispatcher> {
    public MgmtLayerTcpConnection() throws IOException {
        super(MgmtLayerTcpRequestDispatcher.class);
    }

}
