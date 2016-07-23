package org.uag.netsim.core.layer.network;

import java.util.List;
import java.util.Map;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.DataPackage;
import org.uag.netsim.core.layer.phy.RFChannel;

public interface NetworkLayerNLMEOperations {
	Beacon networkFormation(Beacon beacon) throws Exception;
	Map<RFChannel, List<Beacon>> discovery(Beacon beacon) throws Exception;
	List<Beacon> join(RFChannel channel,Beacon beacon,Beacon joinBeacon) throws Exception;
	boolean associate(Beacon beacon,List<Beacon> beacons) throws Exception;
	DataPackage transmitData(List<Beacon> beacons,Object data) throws Exception;
	DataPackage retransmitData(List<Beacon> beacons,DataPackage data) throws Exception;
	int requestExtenedPanId() throws Exception;
}
