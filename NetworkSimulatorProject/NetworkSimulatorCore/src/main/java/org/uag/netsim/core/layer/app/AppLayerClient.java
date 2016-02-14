package org.uag.netsim.core.layer.app;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.layer.AbstractLayerClient;

@Component("appLayerClient")
@Scope("prototype")
public class AppLayerClient extends AbstractLayerClient{
	
	
	public AppLayerClient() throws Exception {
		super();
	}
	
	public byte[] getDiscoverRequest() throws IOException{
		AppLayerRequest request = new AppLayerRequest();
		request.setPrimitive(AppLayerRequest.PRIMITIVE.DISCOVER);
		byte[] data = ObjectSerializer.serialize(request);
		return data;
	}
	
}
