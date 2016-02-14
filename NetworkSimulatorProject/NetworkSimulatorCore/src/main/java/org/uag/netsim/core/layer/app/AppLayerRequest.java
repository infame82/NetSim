package org.uag.netsim.core.layer.app;

import java.io.Serializable;

public class AppLayerRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4034397124071314509L;
	
	public enum PRIMITIVE{REQUEST_APP_NODE,DISCOVER}
	
	private PRIMITIVE primitive;

	public PRIMITIVE getPrimitive() {
		return primitive;
	}

	public void setPrimitive(PRIMITIVE primitive) {
		this.primitive = primitive;
	}
	
	

}
