package org.uag.netsim.core.layer.app;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;

public class AppLayerTcpRequestDispatcher
        extends AbstractLayerTcpRequestDispatcher<AppLayerTcpRequest,AppLayerTcpResponse>{


    public AppLayerTcpRequestDispatcher(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    protected AppLayerTcpResponse resolveRequest(AppLayerTcpRequest request){
        AppLayerTcpResponse response = new AppLayerTcpResponse();
        response.setStatus(AppLayerTcpResponse.STATUS.INVALID_REQUEST);
        return response;
    }
}
