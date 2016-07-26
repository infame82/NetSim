package org.uag.netsim.core.layer.phy;

import java.util.List;

import org.uag.netsim.core.device.Beacon.RF_CHANNEL;

public interface PhyLayerPLMEOperations {

	boolean increaseEnergyLevel(RF_CHANNEL channel);
	
	List<RFChannel> getChannels();
	
	
}
