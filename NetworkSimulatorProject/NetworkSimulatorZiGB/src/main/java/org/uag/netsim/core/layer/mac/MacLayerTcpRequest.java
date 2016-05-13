package org.uag.netsim.core.layer.mac;

import org.uag.netsim.core.layer.LayerTcpRequest;

public class MacLayerTcpRequest implements LayerTcpRequest<MacLayerTcpRequest.PRIMITIVE> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8909448625819069324L;


	public enum PRIMITIVE{OP_1}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }


    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
