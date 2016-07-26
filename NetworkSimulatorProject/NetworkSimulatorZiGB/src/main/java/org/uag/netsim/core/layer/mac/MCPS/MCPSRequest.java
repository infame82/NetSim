package org.uag.netsim.core.layer.mac.MCPS;

import java.util.List;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.DataPackage;
import org.uag.netsim.core.layer.LayerTcpRequest;

public class MCPSRequest implements LayerTcpRequest<MCPSRequest.PRIMITIVE>{

private static final long serialVersionUID = 1L;
	
	public enum PRIMITIVE{TRANSMISSION}

    private PRIMITIVE primitive;
    private List<Beacon> beacons;
    private DataPackage data;

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

	public DataPackage getData() {
		return data;
	}

	public void setData(DataPackage data) {
		this.data = data;
	}
    
    
}
