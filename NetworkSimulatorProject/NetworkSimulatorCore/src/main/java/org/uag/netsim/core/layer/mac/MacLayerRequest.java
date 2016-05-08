package org.uag.netsim.core.layer.mac;

import org.uag.netsim.core.layer.AbstractLayerRequest;

public class MacLayerRequest extends AbstractLayerRequest<MacLayerRequest.LAYER_PRIMITIVE>{

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
