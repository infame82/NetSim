package org.uag.netsim.core.layer.phy;

import java.util.List;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.Beacon.RF_CHANNEL;
import org.uag.netsim.core.device.DataPackage;

public interface PhyLayerPLMEOperations {

	boolean increaseEnergyLevel(RF_CHANNEL channel);
	
	List<RFChannel> getChannels();
	
	boolean transmit(RF_CHANNEL channel,List<Beacon> beacons,DataPackage data) throws Exception;
}
