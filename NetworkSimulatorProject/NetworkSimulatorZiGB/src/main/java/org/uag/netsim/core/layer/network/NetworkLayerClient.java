package org.uag.netsim.core.layer.network;

import java.net.DatagramPacket;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.client.AbstractLayerClient;
import org.uag.netsim.core.layer.LayerTcpConnectionHandler;

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
	
	public LayerTcpConnectionHandler openNLMESAP() throws Exception{
		NetworkLayerRequest request = new NetworkLayerRequest();
		request.setLayerPrimitive(NetworkLayerRequest.LAYER_PRIMITIVE.NLME_SAP_OPEN);
		DatagramPacket response = sendRequest(request);
		LayerTcpConnectionHandler tcpHandler = (LayerTcpConnectionHandler)ObjectSerializer.unserialize(response.getData());
		return tcpHandler;
	}
	
	public boolean closeNLMESAP(int port) throws Exception{
		NetworkLayerRequest request = new NetworkLayerRequest();
		request.setLayerPrimitive(NetworkLayerRequest.LAYER_PRIMITIVE.NLME_SAP_CLOSE);
		request.setFrame(""+port);
		DatagramPacket response = sendRequest(request);
		NetworkLayerResponse confirm = (NetworkLayerResponse)ObjectSerializer.unserialize(response.getData());
		return (confirm.getStatus()== NetworkLayerResponse.STATUS.SUCCESS);
	}
	
	public LayerTcpConnectionHandler openNLDESAP() throws Exception{
		NetworkLayerRequest request = new NetworkLayerRequest();
		request.setLayerPrimitive(NetworkLayerRequest.LAYER_PRIMITIVE.NLDE_SAP_OPEN);
		DatagramPacket response = sendRequest(request);
		LayerTcpConnectionHandler tcpHandler = (LayerTcpConnectionHandler)ObjectSerializer.unserialize(response.getData());
		return tcpHandler;
	}
	
	public boolean closeNLDESAP(int port) throws Exception{
		NetworkLayerRequest request = new NetworkLayerRequest();
		request.setLayerPrimitive(NetworkLayerRequest.LAYER_PRIMITIVE.NLDE_SAP_CLOSE);
		request.setFrame(""+port);
		DatagramPacket response = sendRequest(request);
		NetworkLayerResponse confirm = (NetworkLayerResponse)ObjectSerializer.unserialize(response.getData());
		return (confirm.getStatus()== NetworkLayerResponse.STATUS.SUCCESS);
	}
	
}
