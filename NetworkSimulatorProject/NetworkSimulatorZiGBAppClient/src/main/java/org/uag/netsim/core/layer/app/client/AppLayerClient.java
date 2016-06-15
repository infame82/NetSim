package org.uag.netsim.core.layer.app.client;

import java.net.DatagramPacket;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.client.AbstractLayerClient;
import org.uag.netsim.core.layer.LayerTcpConnectionHandler;

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
	
	public LayerTcpConnectionHandler openAPSMESAP() throws Exception{
		AppLayerRequest request = new AppLayerRequest();
		request.setLayerPrimitive(AppLayerRequest.LAYER_PRIMITIVE.APSME_SAP_OPEN);
		DatagramPacket response = sendRequest(request);
		LayerTcpConnectionHandler tcpHandler = (LayerTcpConnectionHandler)ObjectSerializer.unserialize(response.getData());
		return tcpHandler;
	}
	
	public boolean closeAPSMESAP(int port) throws Exception{
		AppLayerRequest request = new AppLayerRequest();
		request.setLayerPrimitive(AppLayerRequest.LAYER_PRIMITIVE.APSME_SAP_CLOSE);
		request.setFrame(""+port);
		DatagramPacket response = sendRequest(request);
		AppLayerResponse confirm = (AppLayerResponse)ObjectSerializer.unserialize(response.getData());
		return (confirm.getStatus()== AppLayerResponse.STATUS.SUCCESS);
	}
	
	public LayerTcpConnectionHandler openAPSDESAP() throws Exception{
		AppLayerRequest request = new AppLayerRequest();
		request.setLayerPrimitive(AppLayerRequest.LAYER_PRIMITIVE.APSDE_SAP_OPEN);
		DatagramPacket response = sendRequest(request);
		LayerTcpConnectionHandler tcpHandler = (LayerTcpConnectionHandler)ObjectSerializer.unserialize(response.getData());
		return tcpHandler;
	}
	
	public boolean closeAPSDESAP(int port) throws Exception{
		AppLayerRequest request = new AppLayerRequest();
		request.setLayerPrimitive(AppLayerRequest.LAYER_PRIMITIVE.APSDE_SAP_CLOSE);
		request.setFrame(""+port);
		DatagramPacket response = sendRequest(request);
		AppLayerResponse confirm = (AppLayerResponse)ObjectSerializer.unserialize(response.getData());
		return (confirm.getStatus()== AppLayerResponse.STATUS.SUCCESS);
	}
}
