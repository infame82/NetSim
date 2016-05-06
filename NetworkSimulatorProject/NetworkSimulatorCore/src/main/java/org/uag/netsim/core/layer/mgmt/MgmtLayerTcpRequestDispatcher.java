package org.uag.netsim.core.layer.mgmt;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;

/**
 * Created by david on 21/02/16.
 */
public class MgmtLayerTcpRequestDispatcher
        extends AbstractLayerTcpRequestDispatcher<MgmtLayerTcpRequest,MgmtLayerTcpResponse> {

    public MgmtLayerTcpRequestDispatcher(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    protected MgmtLayerTcpResponse resolveRequest(MgmtLayerTcpRequest request) {
        MgmtLayerTcpResponse response = new MgmtLayerTcpResponse();
        response.setStatus(MgmtLayerTcpResponse.STATUS.INVALID_REQUEST);
        return response;
    }
}
