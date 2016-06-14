package org.uag.netsim.core.layer.phy;

import org.uag.netsim.core.client.AbstractLayerRequest;

public class PhyLayerRequest extends AbstractLayerRequest<PhyLayerRequest.LAYER_PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1886819596803456214L;

	public enum LAYER_PRIMITIVE{PD_SAP_OPEN,PD_SAP_CLOSE,PLME_SAP_OPEN,PLME_SAP_CLOSE}
	private LAYER_PRIMITIVE layerPrimitive;

	public LAYER_PRIMITIVE getLayerPrimitive() {
		return layerPrimitive;
	}

	public void setLayerPrimitive(LAYER_PRIMITIVE layerPrimitive) {
		this.layerPrimitive = layerPrimitive;
	}
}
