package org.uag.netsim.core.layer;

/**
 * Created by david on 21/02/16.
 */
public class AbstractLayerTcpResponse implements LayerTcpResponse {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4984972992022526727L;
	private STATUS status;
    private String data;

   

    public void setData(String data) {
        this.data = data;
    }



	public STATUS getStatus() {
		return status;
	}



	public String getData() {
		return data;
	}
	


    public void setStatus(STATUS status) {
        this.status = status;
    }


}
