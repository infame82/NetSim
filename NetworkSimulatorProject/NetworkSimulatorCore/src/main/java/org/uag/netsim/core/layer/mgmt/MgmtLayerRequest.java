package org.uag.netsim.core.layer.mgmt;

import org.uag.netsim.core.layer.LayerRequest;

/**
 * Created by david on 21/02/16.
 */
public class MgmtLayerRequest implements LayerRequest<MgmtLayerRequest.PRIMITIVE> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5201841638731908161L;

	public enum PRIMITIVE{REQUEST_NODE,DISCOVER}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
