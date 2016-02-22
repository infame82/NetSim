package org.uag.netsim.core.layer;

import java.io.Serializable;

/**
 * Created by david on 21/02/16.
 */
public interface LayerTcpResponse extends Serializable {
    public enum STATUS{INVALID_REQUEST,SUCCESS};

    STATUS getStatus();

    String getData();
}
