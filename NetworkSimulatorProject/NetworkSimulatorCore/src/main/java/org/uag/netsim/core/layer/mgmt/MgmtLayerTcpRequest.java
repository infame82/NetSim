package org.uag.netsim.core.layer.mgmt;

import org.uag.netsim.core.layer.LayerTcpRequest;

import java.io.Serializable;

/**
 * Created by david on 21/02/16.
 */
public class MgmtLayerTcpRequest implements LayerTcpRequest<MgmtLayerTcpRequest.PRIMITIVE> {

    public enum PRIMITIVE{OP_1}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
