package org.uag.netsim.core.layer.mac.MLME;

import java.util.List;
import java.util.Map;

import org.uag.netsim.core.client.AbstractLayerTcpResponse;
import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.layer.phy.RFChannel;

public class MLMEConfirm extends AbstractLayerTcpResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6730519340099845374L;
	
	private List<RFChannel> channels;
	private Map<RFChannel,List<Beacon>> activeChannels;
	private Map<String,List<Beacon>>  activeDevices;
	private long extendedAddress;
	

	public List<RFChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<RFChannel> channels) {
		this.channels = channels;
	}

	public Map<RFChannel, List<Beacon>> getActiveChannels() {
		return activeChannels;
	}

	public void setActiveChannels(Map<RFChannel, List<Beacon>> activeChannels) {
		this.activeChannels = activeChannels;
	}

	public long getExtendedAddress() {
		return extendedAddress;
	}

	public void setExtendedAddress(long extendedAddress) {
		this.extendedAddress = extendedAddress;
	}

	public Map<String, List<Beacon>> getActiveDevices() {
		return activeDevices;
	}

	public void setActiveDevices(Map<String, List<Beacon>> activeDevices) {
		this.activeDevices = activeDevices;
	}

}
