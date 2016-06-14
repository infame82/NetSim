package org.uag.netsim.core.layer.network;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

public class NetworkLayerTcpRequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<NetworkLayerTcpRequest,NetworkLayerTcpResponse>{

	public NetworkLayerTcpRequestDispatcher(LayerNode node,Socket socket) throws IOException {
        super(node,socket);
    }

    @Override
    protected NetworkLayerTcpResponse resolveRequest(NetworkLayerTcpRequest request){
    	NetworkLayerTcpResponse response = new NetworkLayerTcpResponse();
        response.setStatus(NetworkLayerTcpResponse.STATUS.INVALID_REQUEST);
        return response;
    }
}
