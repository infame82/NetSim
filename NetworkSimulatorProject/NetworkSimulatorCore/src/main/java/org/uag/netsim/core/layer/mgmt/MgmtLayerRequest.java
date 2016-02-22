package org.uag.netsim.core.layer.mgmt;

import org.uag.netsim.core.layer.LayerRequest;

import java.io.Serializable;

/**
 * Created by david on 21/02/16.
 */
public class MgmtLayerRequest implements LayerRequest<MgmtLayerRequest.PRIMITIVE> {

    public enum PRIMITIVE{REQUEST_NODE,DISCOVER}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
