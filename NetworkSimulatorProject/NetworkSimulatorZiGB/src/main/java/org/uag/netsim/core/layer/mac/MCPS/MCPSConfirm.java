package org.uag.netsim.core.layer.mac.MCPS;

import org.uag.netsim.core.client.AbstractLayerTcpResponse;
import org.uag.netsim.core.device.DataPackage;

public class MCPSConfirm extends AbstractLayerTcpResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4229819085896592101L;
	
	private DataPackage data;

	public DataPackage getData() {
		return data;
	}

	public void setData(DataPackage data) {
		this.data = data;
	}
	
	

}
