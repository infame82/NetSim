package org.uag.netsim.core.layer.app.APSDE;

import org.uag.netsim.core.client.AbstractLayerTcpResponse;
import org.uag.netsim.core.device.DataPackage;

public class APSDEConfirm extends AbstractLayerTcpResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3717324695743511108L;

	private DataPackage dataPack;

	public DataPackage getDataPack() {
		return dataPack;
	}

	public void setDataPack(DataPackage dataPack) {
		this.dataPack = dataPack;
	}
	
	

}
