package org.uag.netsim.core.layer.mac;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;

public class MacLayerTcpRequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<MacLayerTcpRequest,MacLayerTcpResponse>{


    public MacLayerTcpRequestDispatcher(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    protected MacLayerTcpResponse resolveRequest(MacLayerTcpRequest request){
        MacLayerTcpResponse response = new MacLayerTcpResponse();
        response.setStatus(MacLayerTcpResponse.STATUS.INVALID_REQUEST);
        return response;
    }

}
