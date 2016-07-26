package org.uag.netsim.core.layer.network;

import java.util.List;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.DataPackage;

public interface NetworkLayerNLDEOperations {
	DataPackage retransmitData(List<Beacon> beacons,DataPackage data) throws Exception;
	int requestExtenedPanId() throws Exception;
}
