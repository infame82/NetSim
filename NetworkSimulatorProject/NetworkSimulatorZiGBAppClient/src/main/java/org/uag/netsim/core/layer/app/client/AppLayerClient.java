package org.uag.netsim.core.layer.app.client;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.client.AbstractLayerClient;

@Component("appLayerClient")
@Scope("prototype")
public class AppLayerClient extends AbstractLayerClient<AppLayerRequest>{
	
	public static int MIN_PORT_RANGE = 9300;
	public static int MAX_PORT_RANGE = 9309;
	
	public AppLayerClient() throws Exception {
		super(AppLayerRequest.class);
	}
	public AppLayerClient(ICoreLog log) throws Exception {
		super(AppLayerRequest.class,log);
	}
	

	@Override
	public int getMinPort(){
		return MIN_PORT_RANGE;
	}
	@Override
	public int getMaxPort() {
		return MAX_PORT_RANGE;
	}
	
}
