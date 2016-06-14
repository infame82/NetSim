package org.uag.netsim.admin;

import java.net.DatagramPacket;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.client.AbstractLayerClient;
import org.uag.netsim.core.layer.LayerResponse;

@Component("serverLayerClient")
@Scope("prototype")
public class ServerLayerClient extends AbstractLayerClient<ServerLayerRequest>
implements ServerLayerCommands{

	private final static int PORT = 9292;
	
	public ServerLayerClient() throws Exception {
		super(ServerLayerRequest.class);	
	}
	
	public ServerLayerClient(ICoreLog log) throws Exception {
		super(ServerLayerRequest.class,log);
	}
		
	@Override
	public int getMinPort(){
		return PORT;
	}
	@Override
	public int getMaxPort() {
		return PORT;
	}

	@Override
	public boolean startNodes() throws Exception {
		ServerLayerRequest request = new ServerLayerRequest();
		request.setLayerPrimitive(ServerLayerRequest.LAYER_PRIMITIVE.START_NODES);
		DatagramPacket responseP = sendRequest(request);
		ServerLayerResponse response = (ServerLayerResponse)ObjectSerializer.unserialize(responseP.getData());
		return response.getStatus() == LayerResponse.STATUS.SUCCESS;
	}

	@Override
	public boolean stopNodes() throws Exception {
		ServerLayerRequest request = new ServerLayerRequest();
		request.setLayerPrimitive(ServerLayerRequest.LAYER_PRIMITIVE.STOP_NODES);
		DatagramPacket responseP = sendRequest(request);
		ServerLayerResponse response = (ServerLayerResponse)ObjectSerializer.unserialize(responseP.getData());
		return response.getStatus() == LayerResponse.STATUS.SUCCESS;
	}
	
	
}
