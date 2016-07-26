package org.uag.netsim.core.layer.phy;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.client.AbstractLayerClient;
import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.Beacon.RF_CHANNEL;
import org.uag.netsim.core.device.DataPackage;
import org.uag.netsim.core.layer.LayerTcpConnectionHandler;
import org.uag.netsim.core.layer.LayerTcpResponse;
import org.uag.netsim.core.layer.phy.PD.PDConfirm;
import org.uag.netsim.core.layer.phy.PD.PDRequest;
import org.uag.netsim.core.layer.phy.PLME.PLMEConfirm;
import org.uag.netsim.core.layer.phy.PLME.PLMERequest;

@Component("phyLayerClient")
@Scope("prototype")
public class PhyLayerClient extends AbstractLayerClient<PhyLayerRequest>
implements PhyLayerPLMEOperations,PhyLayerPDOperations{
	
	private LayerTcpConnectionHandler plmeSAPHandler;
	private LayerTcpConnectionHandler pdSAPHandler;

	public PhyLayerClient() throws Exception {
		super(PhyLayerRequest.class);
		plmeSAPHandler = openPLMESAP();
		pdSAPHandler = openPDSAP();
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
	
	private PLMEConfirm sendPLMERequest(PLMERequest request) throws Exception{
		PLMEConfirm confirm = new PLMEConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		Socket socket = new Socket(plmeSAPHandler.getHost(),plmeSAPHandler.getPort());
		
		ObjectOutputStream out = new ObjectOutputStream(
				socket.getOutputStream());
		out.writeObject(request);
		out.flush();
		ObjectInputStream in = new ObjectInputStream(
				socket.getInputStream());
		confirm = (PLMEConfirm)in.readObject();
		out.close();
		socket.close();
		return confirm;
	}
	private PDConfirm sendPDRequest(PDRequest request) throws Exception{
		PDConfirm confirm = new PDConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		Socket socket = new Socket(pdSAPHandler.getHost(),pdSAPHandler.getPort());
		
		ObjectOutputStream out = new ObjectOutputStream(
				socket.getOutputStream());
		out.writeObject(request);
		out.flush();
		ObjectInputStream in = new ObjectInputStream(
				socket.getInputStream());
		confirm = (PDConfirm)in.readObject();
		out.close();
		socket.close();
		return confirm;
	}
	@Override
	public boolean increaseEnergyLevel(RF_CHANNEL channel) {
		PLMERequest request = new PLMERequest();
		PLMEConfirm confirm = null;
		request.setChannel(channel);
		request.setPrimitive(PLMERequest.PRIMITIVE.INCREASE_ENERGY);		
		try {
			confirm = sendPLMERequest(request);
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new PLMEConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return confirm.getStatus()==LayerTcpResponse.STATUS.SUCCESS;
	}
	@Override
	public List<RFChannel> getChannels() {
		PLMERequest request = new PLMERequest();
		PLMEConfirm confirm = null;
		request.setPrimitive(PLMERequest.PRIMITIVE.GET_CHANNELS);
		try {
			confirm = sendPLMERequest(request);
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new PLMEConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return confirm.getChannels();
	}
	@Override
	public boolean transmit(RF_CHANNEL channel, List<Beacon> beacons, DataPackage data) throws Exception {
		PDRequest request = new PDRequest();
		PDConfirm confirm = null;
		request.setChannel(channel);
		request.setBeacons(beacons);
		request.setData(data);
		try {
			confirm = sendPDRequest(request);
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new PDConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return confirm.getStatus()==LayerTcpResponse.STATUS.SUCCESS;
	}
}
