package org.uag.netsim.core.layer.mgmt;

import java.net.DatagramPacket;

import org.uag.netsim.core.layer.AbstractLayerRequestDispatcher;
import org.uag.netsim.core.layer.DefaultLayerNodeHandler;
import org.uag.netsim.core.layer.DefaultLayerTcpConnectionHandler;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpConnection;

/**
 * Created by david on 21/02/16.
 */
public class MgmtLayerRequestDispatcher extends AbstractLayerRequestDispatcher<MgmtLayerRequest> {

    public MgmtLayerRequestDispatcher(LayerNode node, DatagramPacket packet) {
        super(node,packet);
    }


    protected Object resolveRequest(MgmtLayerRequest request) throws Exception{
        //byte[] responseContent = null;
        MgmtLayerResponse response = new MgmtLayerResponse();
        response.setStatus(MgmtLayerResponse.STATUS.INVALID_REQUEST);
        if(request.getPrimitive() == MgmtLayerRequest.PRIMITIVE.REQUEST_NODE){
            LayerTcpConnection tcpConnection = node.openTcpConnection();
            return new DefaultLayerTcpConnectionHandler(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.getActiveCount());
        }else if(request.getPrimitive() == MgmtLayerRequest.PRIMITIVE.DISCOVER){
            return new DefaultLayerNodeHandler(node.getHost(),node.getPort(),node.getActiveCount());
        }
        return response;
    }
}
