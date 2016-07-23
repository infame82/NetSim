package org.uag.netsim.core.layer.network.NLME;

import java.util.List;
import java.util.Map;

import org.uag.netsim.core.client.AbstractLayerTcpResponse;
import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.DataPackage;
import org.uag.netsim.core.layer.phy.RFChannel;

public class NLMEConfirm extends AbstractLayerTcpResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2411336082849982001L;

	private List<Beacon> beacons;
	private Map<RFChannel, List<Beacon>> availableNetworks;
	private int extendedPanId;
	private DataPackage data;
	

	public List<Beacon> getBeacons() {
		return beacons;
	}

	public void setBeacons(List<Beacon> beacons) {
		this.beacons = beacons;
	}

	public Map<RFChannel, List<Beacon>> getAvailableNetworks() {
		return availableNetworks;
	}

	public void setAvailableNetworks(Map<RFChannel, List<Beacon>> availableNetworks) {
		this.availableNetworks = availableNetworks;
	}

	public int getExtendedPanId() {
		return extendedPanId;
	}

	public void setExtendedPanId(int extendedPanId) {
		this.extendedPanId = extendedPanId;
	}

	public DataPackage getData() {
		return data;
	}

	public void setData(DataPackage data) {
		this.data = data;
	}
	
	
	
}
