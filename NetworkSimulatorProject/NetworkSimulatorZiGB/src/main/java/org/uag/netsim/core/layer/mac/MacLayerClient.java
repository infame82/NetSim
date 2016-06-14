package org.uag.netsim.core.layer.mac;

import java.net.DatagramPacket;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.client.AbstractLayerClient;
import org.uag.netsim.core.layer.LayerTcpConnectionHandler;

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
	
	public LayerTcpConnectionHandler openMCPSSAP() throws Exception{
		MacLayerRequest request = new MacLayerRequest();
		request.setLayerPrimitive(MacLayerRequest.LAYER_PRIMITIVE.MCPS_SAP_OPEN);
		DatagramPacket response = sendRequest(request);
		LayerTcpConnectionHandler tcpHandler = (LayerTcpConnectionHandler)ObjectSerializer.unserialize(response.getData());
		return tcpHandler;
	}
	
	public boolean closeMCPSSAP(int port) throws Exception{
		MacLayerRequest request = new MacLayerRequest();
		request.setLayerPrimitive(MacLayerRequest.LAYER_PRIMITIVE.MCPS_SAP_CLOSE);
		request.setFrame(""+port);
		DatagramPacket response = sendRequest(request);
		MacLayerResponse confirm = (MacLayerResponse)ObjectSerializer.unserialize(response.getData());
		return (confirm.getStatus()== MacLayerResponse.STATUS.SUCCESS);
	}
	
	public LayerTcpConnectionHandler openMLMESAP() throws Exception{
		MacLayerRequest request = new MacLayerRequest();
		request.setLayerPrimitive(MacLayerRequest.LAYER_PRIMITIVE.MLME_SAP_OPEN);
		DatagramPacket response = sendRequest(request);
		LayerTcpConnectionHandler tcpHandler = (LayerTcpConnectionHandler)ObjectSerializer.unserialize(response.getData());
		return tcpHandler;
	}
	
	public boolean closeNLDESAP(int port) throws Exception{
		MacLayerRequest request = new MacLayerRequest();
		request.setLayerPrimitive(MacLayerRequest.LAYER_PRIMITIVE.MLME_SAP_CLOSE);
		request.setFrame(""+port);
		DatagramPacket response = sendRequest(request);
		MacLayerResponse confirm = (MacLayerResponse)ObjectSerializer.unserialize(response.getData());
		return (confirm.getStatus()== MacLayerResponse.STATUS.SUCCESS);
	}

}
