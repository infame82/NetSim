package org.uag.netsim.core.client;

import org.uag.netsim.core.layer.LayerResponse;

/**
 * Created by david on 21/02/16.
 */
public abstract class AbstractLayerResponse implements  LayerResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7245226051276560931L;
	private STATUS status;
    private String data;


    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

 
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
