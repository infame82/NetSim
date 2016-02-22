package org.uag.netsim.core.layer.mgmt;

import org.uag.netsim.core.layer.AbstractLayerTcpConnection;

import java.io.IOException;

/**
 * Created by david on 21/02/16.
 */
public class MgmtLayerTcpConnection extends AbstractLayerTcpConnection<MgmtLayerTcpRequestDispatcher> {
    public MgmtLayerTcpConnection(int port) throws IOException {
        super(MgmtLayerTcpRequestDispatcher.class,port);
    }

}
