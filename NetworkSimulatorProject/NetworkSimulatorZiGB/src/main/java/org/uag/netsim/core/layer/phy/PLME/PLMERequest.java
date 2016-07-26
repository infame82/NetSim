package org.uag.netsim.core.layer.phy.PLME;

import java.util.List;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.Beacon.RF_CHANNEL;
import org.uag.netsim.core.device.DataPackage;
import org.uag.netsim.core.layer.LayerTcpRequest;

public class PLMERequest implements LayerTcpRequest<PLMERequest.PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8503158804221183335L;

	public enum PRIMITIVE{GET_CHANNELS,INCREASE_ENERGY}

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
