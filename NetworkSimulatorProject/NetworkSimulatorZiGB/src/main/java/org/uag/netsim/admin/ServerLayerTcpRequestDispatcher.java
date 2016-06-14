package org.uag.netsim.admin;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class ServerLayerTcpRequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<ServerLayerTcpRequest,ServerLayerTcpResponse>{

	public ServerLayerTcpRequestDispatcher(LayerNode node,Socket socket) throws IOException {
        super(node,socket);
    }

    @Override
    protected ServerLayerTcpResponse resolveRequest(ServerLayerTcpRequest request){
    	ServerLayerTcpResponse response = new ServerLayerTcpResponse();
        response.setStatus(ServerLayerTcpResponse.STATUS.INVALID_REQUEST);
        return response;
    }
}
