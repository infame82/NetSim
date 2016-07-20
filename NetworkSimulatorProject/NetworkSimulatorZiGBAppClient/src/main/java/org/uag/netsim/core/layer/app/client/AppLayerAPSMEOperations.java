package org.uag.netsim.core.layer.app.client;

import org.uag.netsim.core.device.Beacon;

public interface AppLayerAPSMEOperations {

	boolean addNeigbor(Beacon beacon,Beacon neighbor) throws Exception;
}
