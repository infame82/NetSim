package org.uag.netsim.core.layer.app;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.layer.AbstractLayerClient;

@Component("appLayerClient")
@Scope("prototype")
public class AppLayerClient extends AbstractLayerClient{
	
	
	public AppLayerClient() throws Exception {
		super();
	}
	public AppLayerClient(ICoreLog log) throws Exception {
		super(log);
	}
	
	public byte[] getDiscoverRequest() throws IOException{
		AppLayerRequest request = new AppLayerRequest();
		request.setPrimitive(AppLayerRequest.PRIMITIVE.DISCOVER);
		byte[] data = ObjectSerializer.serialize(request);
		return data;
	}

	@Override
	public byte[] getTcpNodeRequest() throws IOException {
		AppLayerRequest request = new AppLayerRequest();
		request.setPrimitive(AppLayerRequest.PRIMITIVE.REQUEST_NODE);
		byte[] data = ObjectSerializer.serialize(request);
		return data;
	}
	
}
