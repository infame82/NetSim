package org.uag.netsim.core.layer.mac;

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
import org.uag.netsim.core.layer.LayerTcpConnectionHandler;
import org.uag.netsim.core.layer.LayerTcpResponse;
import org.uag.netsim.core.layer.mac.MLME.MLMEConfirm;
import org.uag.netsim.core.layer.mac.MLME.MLMERequest;
import org.uag.netsim.core.layer.phy.RFChannel;

@Component("macLayerClient")
@Scope("prototype")
public class MacLayerClient extends AbstractLayerClient<MacLayerRequest>
implements MacLayerMLMEOperations{
	
	private LayerTcpConnectionHandler mlmeSAPHandler;

	public MacLayerClient() throws Exception {
		super(MacLayerRequest.class);
		mlmeSAPHandler = openMLMESAP();
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
	
	private MLMEConfirm sendMLMERequest(MLMERequest request) throws Exception{
		MLMEConfirm confirm = new MLMEConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		Socket socket = new Socket(mlmeSAPHandler.getHost(),mlmeSAPHandler.getPort());
		
		ObjectOutputStream out = new ObjectOutputStream(
				socket.getOutputStream());
		out.writeObject(request);
		out.flush();
		ObjectInputStream in = new ObjectInputStream(
				socket.getInputStream());
		confirm = (MLMEConfirm)in.readObject();
		out.close();
		socket.close();
		return confirm;
	}
	
	@Override
	public List<RFChannel> energyDetectionScan() {
		MLMERequest request = new MLMERequest();
		MLMEConfirm confirm = null;
		request.setPrimitive(MLMERequest.PRIMITIVE.ENERGY_DETECTION_SCAN);
		try {
			confirm = sendMLMERequest(request);
			if(confirm.getStatus()==LayerTcpResponse.STATUS.SUCCESS){
				return confirm.getChannels();
			}
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new MLMEConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return null;
	}
	@Override
	public Map<RFChannel,List<Beacon>> activeScan(List<RFChannel> channels,Beacon beacon) {
		MLMERequest request = new MLMERequest();
		MLMEConfirm confirm = null;
		List<Beacon> beacons = new ArrayList<Beacon>();
		beacons.add(beacon);
		request.setPrimitive(MLMERequest.PRIMITIVE.ACTIVE_SCAN);
		request.setBeacons(beacons);
		request.setChannels(channels);
		try {
			confirm = sendMLMERequest(request);
			if(confirm.getStatus()==LayerTcpResponse.STATUS.SUCCESS){
				return confirm.getActiveChannels();
			}
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new MLMEConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return null;
	}
	@Override
	public boolean setPANId(RFChannel channel,Beacon beacon) {
		List<RFChannel> channels = new ArrayList<RFChannel>();
		channels.add(channel);
		List<Beacon> beacons = new ArrayList<Beacon>();
		beacons.add(beacon);
		MLMERequest request = new MLMERequest();
		MLMEConfirm confirm = null;
		request.setPrimitive(MLMERequest.PRIMITIVE.SET_PAN_ID);
		request.setChannels(channels);
		request.setBeacons(beacons);
		try {
			confirm = sendMLMERequest(request);
			if(confirm.getStatus()==LayerTcpResponse.STATUS.SUCCESS){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new MLMEConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return false;
	}
	@Override
	public List<Beacon> association(Beacon beacon,Beacon joinBeacon) {
		List<Beacon> response = new ArrayList<Beacon>();
		
		List<Beacon> beacons = new ArrayList<Beacon>();
		beacons.add(beacon);
		beacons.add(joinBeacon);
		MLMERequest request = new MLMERequest();
		request.setBeacons(beacons);
		MLMEConfirm confirm = null;
		request.setPrimitive(MLMERequest.PRIMITIVE.ASSOCIATION);
		try {
			confirm = sendMLMERequest(request);
			response = confirm.getBeacons();
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new MLMEConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return response;
	}
	@Override
	public boolean transmission(Beacon beacon) {
		List<Beacon> beacons = new ArrayList<Beacon>();
		beacons.add(beacon);
		MLMERequest request = new MLMERequest();
		MLMEConfirm confirm = null;
		request.setBeacons(beacons);
		request.setPrimitive(MLMERequest.PRIMITIVE.TRANSMISSION);
		try {
			confirm = sendMLMERequest(request);
			if(confirm.getStatus()==LayerTcpResponse.STATUS.SUCCESS){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new MLMEConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return false;
		
	}
	@Override
	public boolean start(RFChannel channel,Beacon beacon) {
		List<RFChannel> channels = new ArrayList<RFChannel>();
		channels.add(channel);
		List<Beacon> beacons = new ArrayList<Beacon>();
		beacons.add(beacon);
		MLMERequest request = new MLMERequest();
		MLMEConfirm confirm = null;
		request.setPrimitive(MLMERequest.PRIMITIVE.START);
		request.setBeacons(beacons);
		request.setChannels(channels);
		try {
			confirm = sendMLMERequest(request);
			if(confirm.getStatus()==LayerTcpResponse.STATUS.SUCCESS){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new MLMEConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return false;
	}
	@Override
	public Map<RFChannel,List<Beacon>> getRegisteredNetworks() {
		MLMERequest request = new MLMERequest();
		MLMEConfirm confirm = null;
		request.setPrimitive(MLMERequest.PRIMITIVE.GET_REGISTERED_NETWORKS);
		try {
			confirm = sendMLMERequest(request);
			return confirm.getActiveChannels();
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new MLMEConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return null;
	}
	@Override
	public Map<String,List<Beacon>> getRegisteredDevices() {
		MLMERequest request = new MLMERequest();
		MLMEConfirm confirm = null;
		request.setPrimitive(MLMERequest.PRIMITIVE.GET_REGISTERED_DEVICES);
		try {
			confirm = sendMLMERequest(request);
			return confirm.getActiveDevices();
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new MLMEConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return null;
	}
	@Override
	public boolean registerDevice(Beacon beacon) {
		List<Beacon> beacons = new ArrayList<Beacon>();
		beacons.add(beacon);
		MLMERequest request = new MLMERequest();
		MLMEConfirm confirm = null;
		request.setPrimitive(MLMERequest.PRIMITIVE.REGISTER_DEVICE);
		request.setBeacons(beacons);
		try {
			confirm = sendMLMERequest(request);
			if(confirm.getStatus()==LayerTcpResponse.STATUS.SUCCESS){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new MLMEConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return false;
	}
	@Override
	public long getExtendedAddress() {
		MLMERequest request = new MLMERequest();
		MLMEConfirm confirm = null;
		request.setPrimitive(MLMERequest.PRIMITIVE.GET_EXTENDED_ADDRESS);
		try {
			confirm = sendMLMERequest(request);
			return confirm.getExtendedAddress();
		} catch (Exception e) {
			e.printStackTrace();
			confirm = new MLMEConfirm();
			confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		}
		return -1L;
	}

}
