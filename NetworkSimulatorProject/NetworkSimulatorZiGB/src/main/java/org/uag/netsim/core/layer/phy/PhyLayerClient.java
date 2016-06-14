package org.uag.netsim.core.layer.phy;

import java.net.DatagramPacket;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.client.AbstractLayerClient;
import org.uag.netsim.core.layer.LayerTcpConnectionHandler;

@Component("phyLayerClient")
@Scope("prototype")
public class PhyLayerClient extends AbstractLayerClient<PhyLayerRequest>{

	public PhyLayerClient() throws Exception {
		super(PhyLayerRequest.class);
	}
	public PhyLayerClient(ICoreLog log) throws Exception {
		super(PhyLayerRequest.class,log);
	}
	

	@Override
	public int getMinPort(){
		return PhyLayerNode.MIN_PORT_RANGE;
	}
	@Override
	public int getMaxPort() {
		return PhyLayerNode.MAX_PORT_RANGE;
	}
	
	public LayerTcpConnectionHandler openPDSAP() throws Exception{
		PhyLayerRequest request = new PhyLayerRequest();
		request.setLayerPrimitive(PhyLayerRequest.LAYER_PRIMITIVE.PD_SAP_OPEN);
		DatagramPacket response = sendRequest(request);
		LayerTcpConnectionHandler tcpHandler = (LayerTcpConnectionHandler)ObjectSerializer.unserialize(response.getData());
		return tcpHandler;
	}
	
	public boolean closePDSAP(int port) throws Exception{
		PhyLayerRequest request = new PhyLayerRequest();
		request.setLayerPrimitive(PhyLayerRequest.LAYER_PRIMITIVE.PD_SAP_CLOSE);
		request.setFrame(""+port);
		DatagramPacket response = sendRequest(request);
		PhyLayerResponse confirm = (PhyLayerResponse)ObjectSerializer.unserialize(response.getData());
		return (confirm.getStatus()== PhyLayerResponse.STATUS.SUCCESS);
	}
	
	public LayerTcpConnectionHandler openPLMESAP() throws Exception{
		PhyLayerRequest request = new PhyLayerRequest();
		request.setLayerPrimitive(PhyLayerRequest.LAYER_PRIMITIVE.PLME_SAP_OPEN);
		DatagramPacket response = sendRequest(request);
		LayerTcpConnectionHandler tcpHandler = (LayerTcpConnectionHandler)ObjectSerializer.unserialize(response.getData());
		return tcpHandler;
	}
	
	public boolean closePLMESAP(int port) throws Exception{
		PhyLayerRequest request = new PhyLayerRequest();
		request.setLayerPrimitive(PhyLayerRequest.LAYER_PRIMITIVE.PLME_SAP_CLOSE);
		request.setFrame(""+port);
		DatagramPacket response = sendRequest(request);
		PhyLayerResponse confirm = (PhyLayerResponse)ObjectSerializer.unserialize(response.getData());
		return (confirm.getStatus()== PhyLayerResponse.STATUS.SUCCESS);
	}
}
