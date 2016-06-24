package org.uag.netsim.core.layer.phy;

import org.uag.netsim.core.layer.LayerTcpRequest;

public class PhyLayerTcpRequest implements LayerTcpRequest<PhyLayerTcpRequest.PRIMITIVE> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2613467303373155759L;


	public enum PRIMITIVE{PD_SAP_OPEN,PD_SAP_CLOSE,PLME_SAP_OPEN,PLME_SAP_CLOSE}

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }


    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
}
