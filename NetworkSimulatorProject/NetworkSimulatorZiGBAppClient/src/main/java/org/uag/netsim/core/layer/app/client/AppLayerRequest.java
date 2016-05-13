package org.uag.netsim.core.layer.app.client;

import org.uag.netsim.core.client.AbstractLayerRequest;

public class AppLayerRequest extends AbstractLayerRequest<AppLayerRequest.LAYER_PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4034397124071314509L;
	
	public enum LAYER_PRIMITIVE{OP_1}
	private LAYER_PRIMITIVE layerPrimitive;

	public LAYER_PRIMITIVE getLayerPrimitive() {
		return layerPrimitive;
	}

	public void setLayerPrimitive(LAYER_PRIMITIVE layerPrimitive) {
		this.layerPrimitive = layerPrimitive;
	}

}
