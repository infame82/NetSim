package org.uag.netsim.core.layer.network.NLDE;

import org.uag.netsim.core.client.AbstractLayerTcpResponse;
import org.uag.netsim.core.device.DataPackage;

public class NLDEConfirm extends AbstractLayerTcpResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6396662024368925290L;
	
	private DataPackage data;

	public DataPackage getData() {
		return data;
	}

	public void setData(DataPackage data) {
		this.data = data;
	}
	
	

}
