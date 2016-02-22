package org.uag.netsim.core.layer.app;

import org.uag.netsim.core.layer.LayerRequest;


public class AppLayerRequest implements LayerRequest<AppLayerRequest.PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4034397124071314509L;
	
	public enum PRIMITIVE{REQUEST_NODE,DISCOVER}
	
	private PRIMITIVE primitive;

	public PRIMITIVE getPrimitive() {
		return primitive;
	}

	public void setPrimitive(PRIMITIVE primitive) {
		this.primitive = primitive;
	}
	
	

}
