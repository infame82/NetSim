package org.uag.netsim.core.layer.network;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.layer.AbstractLayerClient;

@Component("networkLayerClient")
@Scope("prototype")
public class NetworkLayerClient extends AbstractLayerClient<NetworkLayerRequest>{

	public NetworkLayerClient() throws Exception {
		super(NetworkLayerRequest.class);
	}
	public NetworkLayerClient(ICoreLog log) throws Exception {
		super(NetworkLayerRequest.class,log);
	}
	

	@Override
	public int getMinPort(){
		return NetworkLayerNode.MIN_PORT_RANGE;
	}
	@Override
	public int getMaxPort() {
		return NetworkLayerNode.MAX_PORT_RANGE;
	}
}
