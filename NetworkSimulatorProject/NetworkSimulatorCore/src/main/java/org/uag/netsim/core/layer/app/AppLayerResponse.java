package org.uag.netsim.core.layer.app;

import java.io.Serializable;

public class AppLayerResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1223567492003791644L;
	
	public enum STATUS{INVALID_REQUEST,SUCCESS};
	
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
