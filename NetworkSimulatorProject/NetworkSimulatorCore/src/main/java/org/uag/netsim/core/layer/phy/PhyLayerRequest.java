package org.uag.netsim.core.layer.phy;

import org.uag.netsim.core.layer.AbstractLayerRequest;

public class PhyLayerRequest extends AbstractLayerRequest<PhyLayerRequest.LAYER_PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1886819596803456214L;

	public enum LAYER_PRIMITIVE{OP_1}
	private LAYER_PRIMITIVE layerPrimitive;

	public LAYER_PRIMITIVE getLayerPrimitive() {
		return layerPrimitive;
	}

	public void setLayerPrimitive(LAYER_PRIMITIVE layerPrimitive) {
		this.layerPrimitive = layerPrimitive;
	}
}
