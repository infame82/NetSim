package org.uag.netsim.admin;

import org.uag.netsim.core.client.AbstractLayerRequest;

public class ServerLayerRequest extends AbstractLayerRequest<ServerLayerRequest.LAYER_PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 82426998167932306L;

	public enum LAYER_PRIMITIVE{START_NODES,STOP_NODES}
	private LAYER_PRIMITIVE layerPrimitive;

	public LAYER_PRIMITIVE getLayerPrimitive() {
		return layerPrimitive;
	}

	public void setLayerPrimitive(LAYER_PRIMITIVE layerPrimitive) {
		this.layerPrimitive = layerPrimitive;
	}
}
