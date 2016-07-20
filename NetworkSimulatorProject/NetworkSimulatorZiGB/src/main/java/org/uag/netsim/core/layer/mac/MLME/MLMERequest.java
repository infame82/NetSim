package org.uag.netsim.core.layer.mac.MLME;

import java.util.List;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.layer.LayerTcpRequest;
import org.uag.netsim.core.layer.phy.RFChannel;

public class MLMERequest implements LayerTcpRequest<MLMERequest.PRIMITIVE>{

	private static final long serialVersionUID = 1L;
	
	public enum PRIMITIVE{ENERGY_DETECTION_SCAN,ACTIVE_SCAN,SET_PAN_ID,ASSOCIATION,TRANSMISSION,START,GET_REGISTERED_NETWORKS,GET_REGISTERED_DEVICES,REGISTER_DEVICE,GET_EXTENDED_ADDRESS}

    private PRIMITIVE primitive;
    private List<Beacon> beacons;
    
    private List<RFChannel> channels;
    

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }
    
    
	public List<RFChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<RFChannel> channels) {
		this.channels = channels;
	}

	public List<Beacon> getBeacons() {
		return beacons;
	}

	public void setBeacons(List<Beacon> beacons) {
		this.beacons = beacons;
	}


	
	
}
