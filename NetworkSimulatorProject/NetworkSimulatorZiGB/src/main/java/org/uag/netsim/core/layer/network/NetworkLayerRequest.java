package org.uag.netsim.core.layer.network;

import org.uag.netsim.core.client.AbstractLayerRequest;

public class NetworkLayerRequest extends AbstractLayerRequest<NetworkLayerRequest.LAYER_PRIMITIVE>{

private static final long serialVersionUID = 4034397124071314509L;
	
	public enum LAYER_PRIMITIVE{NLME_SAP_OPEN,NLME_SAP_CLOSE,NLDE_SAP_OPEN,NLDE_SAP_CLOSE}
	private LAYER_PRIMITIVE layerPrimitive;

	public LAYER_PRIMITIVE getLayerPrimitive() {
		return layerPrimitive;
	}

	public void setLayerPrimitive(LAYER_PRIMITIVE layerPrimitive) {
		this.layerPrimitive = layerPrimitive;
	}
}
