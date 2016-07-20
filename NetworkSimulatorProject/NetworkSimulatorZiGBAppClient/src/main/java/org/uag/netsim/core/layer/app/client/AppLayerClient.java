package org.uag.netsim.core.layer.app.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.ICoreLog;
import org.uag.netsim.core.ObjectSerializer;
import org.uag.netsim.core.client.AbstractLayerClient;
import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.layer.LayerTcpConnectionHandler;
import org.uag.netsim.core.layer.LayerTcpResponse;
import org.uag.netsim.core.layer.LayerTcpResponse.STATUS;
import org.uag.netsim.core.layer.app.APSME.APSMEConfirm;
import org.uag.netsim.core.layer.app.APSME.APSMERequest;

@Component("appLayerClient")
@Scope("prototype")
public class AppLayerClient extends AbstractLayerClient<AppLayerRequest>
implements AppLayerAPSMEOperations
{
	
	public static int MIN_PORT_RANGE = 9300;
	public static int MAX_PORT_RANGE = 9309;
	
	private LayerTcpConnectionHandler apsmeSAPHandler;
	
	public AppLayerClient() throws Exception {
		super(AppLayerRequest.class);
		apsmeSAPHandler = openAPSMESAP();
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
	
	private APSMEConfirm sendAPSMERequest(APSMERequest request) throws Exception{
		APSMEConfirm confirm = new APSMEConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		Socket socket = new Socket(apsmeSAPHandler.getHost(),apsmeSAPHandler.getPort());
		
		ObjectOutputStream out = new ObjectOutputStream(
				socket.getOutputStream());
		out.writeObject(request);
		out.flush();
		ObjectInputStream in = new ObjectInputStream(
				socket.getInputStream());
		confirm = (APSMEConfirm)in.readObject();
		out.close();
		socket.close();
		return confirm;
	}
	@Override
	public boolean addNeigbor(Beacon beacon,Beacon neighbor) throws Exception {
		APSMERequest request = new APSMERequest();
		APSMEConfirm confirm = null;
		List<Beacon> beacons = new ArrayList<Beacon>();
		beacons.add(beacon);
		beacons.add(neighbor);
		request.setBeacons(beacons);
		request.setPrimitive(APSMERequest.PRIMITIVE.ADD_NEIGHBOR);
		confirm = sendAPSMERequest(request);
		return (confirm.getStatus() == STATUS.SUCCESS);
	}
}
