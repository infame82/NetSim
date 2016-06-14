package org.uag.netsim.core.layer.phy.PD;

import org.uag.netsim.core.layer.LayerTcpRequest;

public class PDRequest implements LayerTcpRequest<PDRequest.PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7653727289853434854L;

	public enum PRIMITIVE{OP_1}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
