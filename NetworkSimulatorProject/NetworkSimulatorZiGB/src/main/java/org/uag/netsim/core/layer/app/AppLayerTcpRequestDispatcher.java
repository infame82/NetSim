package org.uag.netsim.core.layer.app;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.app.client.AppLayerTcpRequest;
import org.uag.netsim.core.layer.app.client.AppLayerTcpResponse;

public class AppLayerTcpRequestDispatcher
        extends AbstractLayerTcpRequestDispatcher<AppLayerTcpRequest,AppLayerTcpResponse>{


    public AppLayerTcpRequestDispatcher(LayerNode node,Socket socket) throws IOException {
        super(node,socket);
    }

    @Override
    protected AppLayerTcpResponse resolveRequest(AppLayerTcpRequest request){
        AppLayerTcpResponse response = new AppLayerTcpResponse();
        response.setStatus(AppLayerTcpResponse.STATUS.INVALID_REQUEST);
        return response;
    }
}
