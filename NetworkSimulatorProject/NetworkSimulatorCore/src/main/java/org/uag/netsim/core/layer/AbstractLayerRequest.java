package org.uag.netsim.core.layer;

public abstract class AbstractLayerRequest<P extends Enum<P>> implements LayerRequest<P>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7895536119482512132L;
	
	protected PRIMITIVE primitive;
	@Override
	public PRIMITIVE getPrimitive() {
		return primitive;
	}

	@Override
	public void setPrimitive(PRIMITIVE primitive) {
		this.primitive = primitive;
	}

}
