package org.uag.netsim.core.layer.mac;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class MacLayerTcpRequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<MacLayerTcpRequest,MacLayerTcpResponse>{


    public MacLayerTcpRequestDispatcher(LayerNode node,Socket socket) throws IOException {
        super(node,socket);
    }

    @Override
    protected MacLayerTcpResponse resolveRequest(MacLayerTcpRequest request){
        MacLayerTcpResponse response = new MacLayerTcpResponse();
        response.setStatus(MacLayerTcpResponse.STATUS.INVALID_REQUEST);
        return response;
    }

}
