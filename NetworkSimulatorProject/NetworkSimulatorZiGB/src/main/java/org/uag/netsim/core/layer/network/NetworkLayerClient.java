package org.uag.netsim.core.layer.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.client.AbstractLayerClient;
import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.DataPackage;
import org.uag.netsim.core.layer.LayerTcpConnectionHandler;
import org.uag.netsim.core.layer.LayerTcpResponse;
import org.uag.netsim.core.layer.LayerTcpResponse.STATUS;
import org.uag.netsim.core.layer.network.NLDE.NLDEConfirm;
import org.uag.netsim.core.layer.network.NLDE.NLDERequest;
import org.uag.netsim.core.layer.network.NLME.NLMEConfirm;
import org.uag.netsim.core.layer.network.NLME.NLMERequest;
import org.uag.netsim.core.layer.phy.RFChannel;

@Component("networkLayerClient")
@Scope("prototype")
public class NetworkLayerClient extends AbstractLayerClient<NetworkLayerRequest>
implements NetworkLayerNLMEOperations,NetworkLayerNLDEOperations{
	
	private LayerTcpConnectionHandler nlmeSAPHandler;
	private LayerTcpConnectionHandler nldeSAPHandler;

	public NetworkLayerClient() throws Exception {
		super(NetworkLayerRequest.class);
		nlmeSAPHandler = openNLMESAP();
		nldeSAPHandler = openNLDESAP();
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
	private NLMEConfirm sendNLMERequest(NLMERequest request) throws Exception{
		NLMEConfirm confirm = new NLMEConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		Socket socket = new Socket(nlmeSAPHandler.getHost(),nlmeSAPHandler.getPort());
		
		ObjectOutputStream out = new ObjectOutputStream(
				socket.getOutputStream());
		out.writeObject(request);
		out.flush();
		ObjectInputStream in = new ObjectInputStream(
				socket.getInputStream());
		confirm = (NLMEConfirm)in.readObject();
		out.close();
		socket.close();
		return confirm;
	}
	private NLDEConfirm sendNLDERequest(NLDERequest request) throws Exception{
		NLDEConfirm confirm = new NLDEConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		Socket socket = new Socket(nldeSAPHandler.getHost(),nldeSAPHandler.getPort());
		
		ObjectOutputStream out = new ObjectOutputStream(
				socket.getOutputStream());
		out.writeObject(request);
		out.flush();
		ObjectInputStream in = new ObjectInputStream(
				socket.getInputStream());
		confirm = (NLDEConfirm)in.readObject();
		out.close();
		socket.close();
		return confirm;
	}
	@Override
	public Beacon networkFormation(Beacon beacon) throws Exception{
		NLMERequest request = new NLMERequest();
		NLMEConfirm confirm = null;
		List<Beacon> beacons = new ArrayList<Beacon>();
		beacons.add(beacon);
		request.setBeacons(beacons);
		request.setPrimitive(NLMERequest.PRIMITIVE.REQUEST_NETWORK_FORMATION);
		confirm = sendNLMERequest(request);
		return confirm.getBeacons().get(0);
	}

	@Override
	public Map<RFChannel, List<Beacon>> discovery(Beacon beacon) throws Exception{
		NLMERequest request = new NLMERequest();
		NLMEConfirm confirm = null;
		List<Beacon> beacons = new ArrayList<Beacon>();
		beacons.add(beacon);
		request.setBeacons(beacons);
		request.setPrimitive(NLMERequest.PRIMITIVE.REQUEST_NETWORK_FORMATION);
		confirm = sendNLMERequest(request);
		return confirm.getAvailableNetworks();
	}
	@Override
	public List<Beacon> join(RFChannel channel,Beacon beacon,Beacon joinBeacon) throws Exception{
		NLMERequest request = new NLMERequest();
		NLMEConfirm confirm = null;
		List<Beacon> beacons = new ArrayList<Beacon>();
		beacons.add(beacon);
		beacons.add(joinBeacon);
		request.setBeacons(beacons);
		request.setChannel(channel);
		request.setPrimitive(NLMERequest.PRIMITIVE.NETWORK_JOIN);
		confirm = sendNLMERequest(request);
		return confirm.getBeacons();
	}
	@Override
	public boolean associate(Beacon beacon,List<Beacon> neighbors) throws Exception {
		NLMERequest request = new NLMERequest();
		NLMEConfirm confirm = null;
		List<Beacon> beacons = new ArrayList<Beacon>();
		beacons.add(beacon);
		beacons.addAll(neighbors);
		request.setBeacons(beacons);
		request.setPrimitive(NLMERequest.PRIMITIVE.ASSOCIATE);
		confirm = sendNLMERequest(request);
		return (confirm.getStatus() == STATUS.SUCCESS);
	}
	
	@Override
	public DataPackage transmitData(List<Beacon> beacons, Object data) throws Exception {
		NLDERequest request = new NLDERequest();
		NLDEConfirm confirm = null;
		request.setBeacons(beacons);
		request.setData(data);
		request.setPrimitive(NLDERequest.PRIMITIVE.TRANSMISSION);
		confirm = sendNLDERequest(request);
		return confirm.getData();
	}

	@Override
	public DataPackage retransmitData(List<Beacon> beacons,DataPackage data) throws Exception {
		NLDERequest request = new NLDERequest();
		NLDEConfirm confirm = null;
		request.setBeacons(beacons);
		request.setPack(data);
		request.setPrimitive(NLDERequest.PRIMITIVE.RETRANSMISSION);
		confirm = sendNLDERequest(request);
		return confirm.getData();
		
	}
	@Override
	public int requestExtenedPanId() throws Exception {
		NLMERequest request = new NLMERequest();
		NLMEConfirm confirm = null;
		request.setPrimitive(NLMERequest.PRIMITIVE.REQUEST_EXT_PAN_ID);
		confirm = sendNLMERequest(request);
		return confirm.getExtendedPanId();
	}
	
	
}
