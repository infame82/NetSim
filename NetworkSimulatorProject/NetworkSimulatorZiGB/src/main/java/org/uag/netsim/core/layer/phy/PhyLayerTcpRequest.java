package org.uag.netsim.core.layer.phy;

import org.uag.netsim.core.layer.LayerTcpRequest;

public class PhyLayerTcpRequest implements LayerTcpRequest<PhyLayerTcpRequest.PRIMITIVE> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2613467303373155759L;


	public enum PRIMITIVE{OP_1}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }


    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
