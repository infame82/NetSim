package org.uag.netsim.core.layer.network.NLDE;

import java.util.List;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.DataPackage;
import org.uag.netsim.core.layer.LayerTcpRequest;

public class NLDERequest implements LayerTcpRequest<NLDERequest.PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum PRIMITIVE{TRANSMISSION,RETRANSMISSION}
	
	private DataPackage pack;
	private List<Beacon> beacons;
	private Object data;

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }

	public DataPackage getPack() {
		return pack;
	}

	public void setPack(DataPackage pack) {
		this.pack = pack;
	}

	public List<Beacon> getBeacons() {
		return beacons;
	}

	public void setBeacons(List<Beacon> beacons) {
		this.beacons = beacons;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

    
}
