package org.uag.netsim.core.layer.app.APSME;

import java.util.List;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.layer.LayerTcpRequest;

public class APSMERequest implements LayerTcpRequest<APSMERequest.PRIMITIVE>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6243964111139315937L;

	public enum PRIMITIVE{ADD_NEIGHBOR}
	
	 private List<Beacon> beacons;

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }

	public List<Beacon> getBeacons() {
		return beacons;
	}

	public void setBeacons(List<Beacon> beacons) {
		this.beacons = beacons;
	}
    
    
}
