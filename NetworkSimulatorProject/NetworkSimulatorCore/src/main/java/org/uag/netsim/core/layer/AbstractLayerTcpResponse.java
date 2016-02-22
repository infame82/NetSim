package org.uag.netsim.core.layer;

/**
 * Created by david on 21/02/16.
 */
public class AbstractLayerTcpResponse implements LayerTcpResponse {
    private STATUS status;
    private String data;

    @Override
    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    @Override
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
