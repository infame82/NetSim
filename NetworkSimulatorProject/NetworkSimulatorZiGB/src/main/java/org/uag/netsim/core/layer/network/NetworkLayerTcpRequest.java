package org.uag.netsim.core.layer.network;

import org.uag.netsim.core.layer.LayerTcpRequest;

public class NetworkLayerTcpRequest implements LayerTcpRequest<NetworkLayerTcpRequest.PRIMITIVE> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2338994531047809181L;


	public enum PRIMITIVE{OP_1}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }


    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
