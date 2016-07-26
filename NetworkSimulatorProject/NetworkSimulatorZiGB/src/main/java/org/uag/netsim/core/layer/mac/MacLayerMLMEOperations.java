package org.uag.netsim.core.layer.mac;

import java.util.List;
import java.util.Map;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.layer.phy.RFChannel;

public interface MacLayerMLMEOperations {
	
	static final int ACCEPTABLE_ENERGY_LEVEL = 10;

	List<RFChannel> energyDetectionScan();
	Map<RFChannel,List<Beacon>> activeScan(List<RFChannel> channels,Beacon beacon);
	boolean setPANId(RFChannel channel,Beacon beacon);
	List<Beacon> association(Beacon beacon,Beacon joinBeacon) throws Exception;
	boolean start(RFChannel channel,Beacon beacon);
	
	Map<RFChannel,List<Beacon>> getRegisteredNetworks();
	Map<String,List<Beacon>> getRegisteredDevices();
	boolean registerDevice(Beacon beacon);
	long getExtendedAddress();
}
