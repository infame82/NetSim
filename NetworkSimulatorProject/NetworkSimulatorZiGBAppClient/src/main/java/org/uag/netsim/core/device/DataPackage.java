package org.uag.netsim.core.device;

import java.io.Serializable;

import org.uag.netsim.core.device.Beacon.RF_CHANNEL;

public class DataPackage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6557184494420686521L;
	private int id;
	private int expiration;
	private RF_CHANNEL channel;
	
	private Object data;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getExpiration() {
		return expiration;
	}
	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public RF_CHANNEL getChannel() {
		return channel;
	}
	public void setChannel(RF_CHANNEL channel) {
		this.channel = channel;
	}
}
