package org.uag.netsim.core.layer.mgmt;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.layer.AbstractLayerClient;

@Component("mgmtLayerClient")
@Scope("prototype")
public class MgmtLayerClient extends AbstractLayerClient<MgmtLayerRequest>{

	public MgmtLayerClient() throws Exception {
		super(MgmtLayerRequest.class);
	}
	public MgmtLayerClient(ICoreLog log) throws Exception {
		super(MgmtLayerRequest.class,log);
	}
	
	@Override
	public int getMinPort(){
		return MgmtLayerNode.MIN_PORT_RANGE;
	}
	@Override
	public int getMaxPort() {
		return MgmtLayerNode.MAX_PORT_RANGE;
	}

}
