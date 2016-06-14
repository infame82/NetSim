package org.uag.netsim.admin;

import org.uag.netsim.core.layer.LayerTcpRequest;

public class ServerLayerTcpRequest implements LayerTcpRequest<ServerLayerTcpRequest.PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7258454061627617004L;


	public enum PRIMITIVE{OP_1}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }


    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
