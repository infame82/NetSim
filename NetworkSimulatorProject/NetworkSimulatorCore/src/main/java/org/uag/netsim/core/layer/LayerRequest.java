package org.uag.netsim.core.layer;

import java.io.Serializable;

/**
 * Created by david on 21/02/16.
 */
public interface LayerRequest <P extends Enum<P>> extends Serializable {
	
	public enum PRIMITIVE{REQUEST_NODE,DISCOVER}

	PRIMITIVE getPrimitive();
    void setPrimitive(PRIMITIVE primitive);
    
    P getLayerPrimitive();
    void setLayerPrimitive(P primitive);
}