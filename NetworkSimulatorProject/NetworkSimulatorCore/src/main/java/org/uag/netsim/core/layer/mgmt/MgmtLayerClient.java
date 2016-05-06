package org.uag.netsim.core.layer.mgmt;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.layer.AbstractLayerClient;

@Component("mgmtLayerClient")
@Scope("prototype")
public class MgmtLayerClient extends AbstractLayerClient{

	public MgmtLayerClient() throws Exception {
		super();
	}
	public MgmtLayerClient(ICoreLog log) throws Exception {
		super(log);
	}
	
	public byte[] getDiscoverRequest() throws IOException{
		MgmtLayerRequest request = new MgmtLayerRequest();
		request.setPrimitive(MgmtLayerRequest.PRIMITIVE.DISCOVER);
		byte[] data = ObjectSerializer.serialize(request);
		return data;
	}

	@Override
	public byte[] getTcpNodeRequest() throws IOException {
		MgmtLayerRequest request = new MgmtLayerRequest();
		request.setPrimitive(MgmtLayerRequest.PRIMITIVE.REQUEST_NODE);
		byte[] data = ObjectSerializer.serialize(request);
		return data;
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
