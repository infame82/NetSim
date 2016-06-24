package org.uag.netsim.core.layer.network.NLME.NETWORKFORMATION;

import java.io.Serializable;

public class Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2732829813481343728L;

	/**
	 * The five most significant bits (b27,...,b31) are reserved. The 27 least
significant bits (b0, b1,... b26) indicate which channels are to be scanned in
preparation for starting a network (1=scan, 0=do not scan) for each of the
27 valid channels (see [B1]).
	 */
	private byte[] scanChannels;
	
	/**
	 * A value used to calculate the length of time to spend scanning each channel.
The time spent scanning each channel is (aBaseSuperframeDuration * (2n + 1))
symbols, where n is the value of the ScanDuration parameter (see [B1]).
Valid Range: 0-14
	 */
	private int scanDuration;
	
	/**
	 * The beacon order of the network that the higher layers wish to form.
Valid Range: 0-15
	 **/
	private int beaconOrder;
	
	/**
	 * The superframe order of the network that the higher layers wish to form.
Valid Range: 0-15
	 */
	private int superFrameOrder;
	
	/**
	 * If this value is TRUE, the NLME will request that the ZigBee coordinator is
started supporting battery life extension mode; If this value is FALSE, the
NLME will request that the ZigBee coordinator is started without supporting
battery life extension mode.
	 */
	private boolean batteryLifeExtension;
	
	public Request(byte[] scanChannels,int scanDuration
			,int beaconOrder,int superFrameOrder,boolean batteryLifeExtension){
		this.scanChannels = scanChannels;
		this.scanDuration = scanDuration;
		this.beaconOrder = beaconOrder;
		this.superFrameOrder = superFrameOrder;
		this.batteryLifeExtension = batteryLifeExtension;
	}

	public byte[] getScanChannels() {
		return scanChannels;
	}

	public void setScanChannels(byte[] scanChannels) {
		this.scanChannels = scanChannels;
	}

	public int getScanDuration() {
		return scanDuration;
	}

	public void setScanDuration(int scanDuration) {
		this.scanDuration = scanDuration;
	}

	public int getBeaconOrder() {
		return beaconOrder;
	}

	public void setBeaconOrder(int beaconOrder) {
		this.beaconOrder = beaconOrder;
	}

	public int getSuperFrameOrder() {
		return superFrameOrder;
	}

	public void setSuperFrameOrder(int superFrameOrder) {
		this.superFrameOrder = superFrameOrder;
	}

	public boolean isBatteryLifeExtension() {
		return batteryLifeExtension;
	}

	public void setBatteryLifeExtension(boolean batteryLifeExtension) {
		this.batteryLifeExtension = batteryLifeExtension;
	}
	
	
	
}
