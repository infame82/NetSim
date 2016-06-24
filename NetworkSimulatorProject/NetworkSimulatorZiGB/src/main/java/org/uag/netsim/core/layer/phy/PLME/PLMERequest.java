package org.uag.netsim.core.layer.phy.PLME;

import org.uag.netsim.core.layer.LayerTcpRequest;
import org.uag.netsim.core.layer.phy.RFChannel.RF_CHANNEL;

public class PLMERequest implements LayerTcpRequest<PLMERequest.PRIMITIVE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8503158804221183335L;

	public enum PRIMITIVE{GET_CHANNELS,INCREASE_ENERGY}

    private PRIMITIVE primitive;
    
    private RF_CHANNEL channel;

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
    
}
