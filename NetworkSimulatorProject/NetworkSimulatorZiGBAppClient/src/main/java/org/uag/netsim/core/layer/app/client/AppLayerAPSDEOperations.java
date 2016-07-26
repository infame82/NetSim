package org.uag.netsim.core.layer.app.client;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.DataPackage;

public interface AppLayerAPSDEOperations {

	public DataPackage transmitData(Beacon beacon,Object data) throws Exception;
}
