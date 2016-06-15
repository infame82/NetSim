package org.uag.netsim.core.layer.app.APSDE;

import org.uag.netsim.core.layer.LayerTcpRequest;

public class APSDERequest implements LayerTcpRequest<APSDERequest.PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7369401285485699110L;

	public enum PRIMITIVE{OP_1}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
