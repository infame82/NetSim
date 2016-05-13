package org.uag.netsim.core.layer.network;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;

public class NetworkLayerTcpRequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<NetworkLayerTcpRequest,NetworkLayerTcpResponse>{

	public NetworkLayerTcpRequestDispatcher(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    protected NetworkLayerTcpResponse resolveRequest(NetworkLayerTcpRequest request){
    	NetworkLayerTcpResponse response = new NetworkLayerTcpResponse();
        response.setStatus(NetworkLayerTcpResponse.STATUS.INVALID_REQUEST);
        return response;
    }
}
