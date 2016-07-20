package org.uag.netsim.core.layer.network.NLME;

import java.util.List;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.layer.LayerTcpRequest;
import org.uag.netsim.core.layer.phy.RFChannel;

public class NLMERequest implements LayerTcpRequest<NLMERequest.PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum PRIMITIVE{REQUEST_NETWORK_FORMATION,NETWORK_DISCOVERY,NETWORK_JOIN,ASSOCIATE,TRANSMISSION,RETRANSMISSION,REQUEST_EXT_PAN_ID}

    private PRIMITIVE primitive;
    private List<Beacon> beacons;
    private RFChannel channel;

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

	public RFChannel getChannel() {
		return channel;
	}

	public void setChannel(RFChannel channel) {
		this.channel = channel;
	}

	
    
}
