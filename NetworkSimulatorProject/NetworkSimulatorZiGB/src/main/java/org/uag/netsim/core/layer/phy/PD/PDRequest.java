package org.uag.netsim.core.layer.phy.PD;

import java.util.List;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.DataPackage;
import org.uag.netsim.core.device.Beacon.RF_CHANNEL;
import org.uag.netsim.core.layer.LayerTcpRequest;

public class PDRequest implements LayerTcpRequest<PDRequest.PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7653727289853434854L;

	public enum PRIMITIVE{TRANSMIT}

    private PRIMITIVE primitive;
    
private RF_CHANNEL channel;
    
    private List<Beacon> beacons;
    
    private DataPackage data;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }

	public RF_CHANNEL getChannel() {
		return channel;
	}

	public void setChannel(RF_CHANNEL channel) {
		this.channel = channel;
	}

	public List<Beacon> getBeacons() {
		return beacons;
	}

	public void setBeacons(List<Beacon> beacons) {
		this.beacons = beacons;
	}

	public DataPackage getData() {
		return data;
	}

	public void setData(DataPackage data) {
		this.data = data;
	}
    
    
}
