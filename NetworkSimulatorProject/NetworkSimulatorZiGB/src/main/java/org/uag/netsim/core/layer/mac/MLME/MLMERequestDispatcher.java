package org.uag.netsim.core.layer.mac.MLME;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpResponse;
import org.uag.netsim.core.layer.mac.MacLayerNode;
import org.uag.netsim.core.layer.phy.RFChannel;

public class MLMERequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<MLMERequest,MLMEConfirm>{

	public MLMERequestDispatcher(LayerNode node, Socket socket) throws IOException {
		super(node, socket);
	}


	@Override
	protected MLMEConfirm resolveRequest(MLMERequest request) {
		MLMEConfirm confirm = new MLMEConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		MacLayerNode node = (MacLayerNode)super.node;
		switch(request.getPrimitive()){
			case ENERGY_DETECTION_SCAN:
				List<RFChannel> channels = node.energyDetectionScan();
				confirm.setChannels(channels);
				confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
				break;
			case ACTIVE_SCAN:
				Map<RFChannel,List<Beacon>> activeChannels = node.activeScan(request.getChannels(),request.getBeacons().get(0));
				confirm.setActiveChannels(activeChannels);
				confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
				break;
			case SET_PAN_ID:
				if(node.setPANId(request.getChannels().get(0),request.getBeacons().get(0))){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
				}
				break;
			case ASSOCIATION:
			try {
				List<Beacon> beacons = node.association(request.getBeacons().get(0),request.getBeacons().get(1));
				confirm.setBeacons(beacons);
				confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
				break;
			case TRANSMISSION:
				try {
					if(node.transmission(request.getBeacons().get(0))){
						confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case START:
				if(node.start(request.getChannels().get(0),request.getBeacons().get(0))){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
				}
				break;
			case GET_REGISTERED_NETWORKS:				
				Map<RFChannel,List<Beacon>> networks = node.getRegisteredNetworks();
				if(networks!=null){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
					confirm.setActiveChannels(networks);
				}
				break;
			case GET_REGISTERED_DEVICES:
				Map<String,List<Beacon>> devices = node.getRegisteredDevices();
				if(devices!=null){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
					confirm.setActiveDevices(devices);
				}
				break;
			case REGISTER_DEVICE:
				if(node.registerDevice(request.getBeacons().get(0))){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
				}
				break;
			case GET_EXTENDED_ADDRESS:
				confirm.setExtendedAddress(node.getExtendedAddress());
				confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
				break;
			default:
					break;
		}

		return confirm;
	}
}
