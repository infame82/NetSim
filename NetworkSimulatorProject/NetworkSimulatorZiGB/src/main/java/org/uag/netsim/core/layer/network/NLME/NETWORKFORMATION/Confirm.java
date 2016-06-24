package org.uag.netsim.core.layer.network.NLME.NETWORKFORMATION;

import java.io.Serializable;

public class Confirm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1844476942527369551L;

	public enum STATUS{INVALID_REQUEST,STARTUP_FAILURE};
	
	private STATUS status;
	
	public Confirm(STATUS status){
		this.status = status;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}
	
	
}
