package org.uag.netsim.core.layer.phy.PLME;

import org.uag.netsim.core.layer.LayerTcpRequest;

public class PLMERequest implements LayerTcpRequest<PLMERequest.PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8503158804221183335L;

	public enum PRIMITIVE{OP_1}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
