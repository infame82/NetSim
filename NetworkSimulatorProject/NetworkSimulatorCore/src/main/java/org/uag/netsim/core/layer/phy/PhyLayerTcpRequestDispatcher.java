package org.uag.netsim.core.layer.phy;

import java.io.IOException;
import java.net.Socket;

import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;

public class PhyLayerTcpRequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<PhyLayerTcpRequest,PhyLayerTcpResponse>{

	public PhyLayerTcpRequestDispatcher(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    protected PhyLayerTcpResponse resolveRequest(PhyLayerTcpRequest request){
    	PhyLayerTcpResponse response = new PhyLayerTcpResponse();
        response.setStatus(PhyLayerTcpResponse.STATUS.INVALID_REQUEST);
        return response;
    }

}
