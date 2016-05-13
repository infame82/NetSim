package org.uag.netsim.core.layer.phy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.client.AbstractLayerClient;

@Component("phyLayerClient")
@Scope("prototype")
public class PhyLayerClient extends AbstractLayerClient<PhyLayerRequest>{

	public PhyLayerClient() throws Exception {
		super(PhyLayerRequest.class);
	}
	public PhyLayerClient(ICoreLog log) throws Exception {
		super(PhyLayerRequest.class,log);
	}
	

	@Override
	public int getMinPort(){
		return PhyLayerNode.MIN_PORT_RANGE;
	}
	@Override
	public int getMaxPort() {
		return PhyLayerNode.MAX_PORT_RANGE;
	}
}
