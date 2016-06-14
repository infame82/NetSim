package org.uag.netsim.core.layer.network.NLME;

import org.uag.netsim.core.layer.LayerTcpRequest;

public class NLMERequest implements LayerTcpRequest<NLMERequest.PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum PRIMITIVE{OP_1}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
