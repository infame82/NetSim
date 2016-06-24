package org.uag.netsim.core.layer.phy.PLME;

import java.util.List;

import org.uag.netsim.core.client.AbstractLayerTcpResponse;
import org.uag.netsim.core.layer.phy.RFChannel;

public class PLMEConfirm extends AbstractLayerTcpResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7182645498289484888L;
	

	
	private List<RFChannel> channels;



	public List<RFChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<RFChannel> channels) {
		this.channels = channels;
	}
	
	

}
