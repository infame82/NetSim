package org.uag.netsim.core.layer.mac;

import java.util.List;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.DataPackage;

public interface MacLayerMCPSOperations {

	public DataPackage transmission(List<Beacon> beacons,DataPackage data) throws Exception;
}
