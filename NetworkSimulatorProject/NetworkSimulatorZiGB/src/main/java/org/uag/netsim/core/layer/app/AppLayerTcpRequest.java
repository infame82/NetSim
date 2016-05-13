package org.uag.netsim.core.layer.app;

import org.uag.netsim.core.layer.LayerTcpRequest;

/**
 * Created by david on 21/02/16.
 */
public class AppLayerTcpRequest implements LayerTcpRequest<AppLayerTcpRequest.PRIMITIVE> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4693288834840993424L;


	public enum PRIMITIVE{OP_1}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }


    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
