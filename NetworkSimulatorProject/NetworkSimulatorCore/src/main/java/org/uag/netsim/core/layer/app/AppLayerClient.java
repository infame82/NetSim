package org.uag.netsim.core.layer.app;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.layer.AbstractLayerClient;

@Component("appLayerClient")
@Scope("prototype")
public class AppLayerClient extends AbstractLayerClient<AppLayerRequest>{
	
	
	
	public AppLayerClient() throws Exception {
		super(AppLayerRequest.class);
	}
	public AppLayerClient(ICoreLog log) throws Exception {
		super(AppLayerRequest.class,log);
	}
	

	@Override
	public int getMinPort(){
		return AppLayerNode.MIN_PORT_RANGE;
	}
	@Override
	public int getMaxPort() {
		return AppLayerNode.MAX_PORT_RANGE;
	}
	
	
	
}
