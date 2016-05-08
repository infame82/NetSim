package org.uag.netsim.core.layer.mac;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.layer.AbstractLayerClient;

@Component("macLayerClient")
@Scope("prototype")
public class MacLayerClient extends AbstractLayerClient<MacLayerRequest>{

	public MacLayerClient() throws Exception {
		super(MacLayerRequest.class);
	}
	public MacLayerClient(ICoreLog log) throws Exception {
		super(MacLayerRequest.class,log);
	}
	
	@Override
	public int getMinPort(){
		return MacLayerNode.MIN_PORT_RANGE;
	}
	@Override
	public int getMaxPort() {
		return MacLayerNode.MAX_PORT_RANGE;
	}

}
