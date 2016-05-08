package org.uag.netsim.core.layer.mgmt;

import java.net.DatagramPacket;

import org.uag.netsim.core.layer.AbstractLayerRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;

/**
 * Created by david on 21/02/16.
 */
public class MgmtLayerRequestDispatcher extends AbstractLayerRequestDispatcher<MgmtLayerRequest> {

    public MgmtLayerRequestDispatcher(LayerNode node, DatagramPacket packet) {
        super(node,packet);
    }


    protected Object resolveLayerOperation(MgmtLayerRequest request) throws Exception{
        //byte[] responseContent = null;
        MgmtLayerResponse response = new MgmtLayerResponse();
        response.setStatus(MgmtLayerResponse.STATUS.INVALID_REQUEST);
        return response;
    }
}
