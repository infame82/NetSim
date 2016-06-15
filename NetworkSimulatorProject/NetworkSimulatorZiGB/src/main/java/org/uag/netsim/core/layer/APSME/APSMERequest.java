package org.uag.netsim.core.layer.APSME;

import org.uag.netsim.core.layer.LayerTcpRequest;

public class APSMERequest implements LayerTcpRequest<APSMERequest.PRIMITIVE>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6243964111139315937L;

	public enum PRIMITIVE{OP_1}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
