package org.uag.netsim.core.layer;

import java.io.Serializable;

/**
 * Created by david on 21/02/16.
 */
public interface LayerResponse extends Serializable{

    public enum STATUS{INVALID_REQUEST,SUCCESS,ERROR};

    STATUS getStatus();

    String getData();
    
}
