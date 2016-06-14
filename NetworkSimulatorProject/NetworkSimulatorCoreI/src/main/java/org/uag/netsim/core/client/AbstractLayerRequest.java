package org.uag.netsim.core.client;

import org.uag.netsim.core.layer.LayerRequest;

public abstract class AbstractLayerRequest<P extends Enum<P>> implements LayerRequest<P>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7895536119482512132L;
	
	protected PRIMITIVE primitive;
	protected String frame;
	
	@Override
	public PRIMITIVE getPrimitive() {
		return primitive;
	}

	@Override
	public void setPrimitive(PRIMITIVE primitive) {
		this.primitive = primitive;
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	
	
	

}
