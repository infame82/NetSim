package org.uag.netsim.core.layer;

import java.io.Serializable;

/**
 * Created by david on 21/02/16.
 */
public interface LayerRequest <P extends Enum<P>> extends Serializable {

    P getPrimitive();
    void setPrimitive(P primitive);
}