package org.uag.netsim.core.layer.app.APSDE;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.layer.LayerTcpRequest;

public class APSDERequest implements LayerTcpRequest<APSDERequest.PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7369401285485699110L;

	public enum PRIMITIVE{TRANSMIT}
	private Object data;
	private Beacon beacon;

    private PRIMITIVE primitive;

    public PRIMITIVE getPrimitive() {
        return primitive;
    }

    public void setPrimitive(PRIMITIVE primitive) {
        this.primitive = primitive;
    }

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Beacon getBeacon() {
		return beacon;
	}

	public void setBeacon(Beacon beacon) {
		this.beacon = beacon;
	}
    
    
}
