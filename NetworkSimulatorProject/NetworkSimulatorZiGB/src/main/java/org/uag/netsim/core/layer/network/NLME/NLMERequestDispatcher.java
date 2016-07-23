package org.uag.netsim.core.layer.network.NLME;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.device.DataPackage;
import org.uag.netsim.core.layer.AbstractLayerTcpRequestDispatcher;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpResponse;
import org.uag.netsim.core.layer.network.NetworkLayerNode;
import org.uag.netsim.core.layer.phy.RFChannel;

public class NLMERequestDispatcher 
extends AbstractLayerTcpRequestDispatcher<NLMERequest,NLMEConfirm>{

	public NLMERequestDispatcher(LayerNode node,Socket socket) throws IOException {
		super(node,socket);
	}

	@Override
	protected NLMEConfirm resolveRequest(NLMERequest request) {
		NLMEConfirm confirm = new NLMEConfirm();
		confirm.setStatus(LayerTcpResponse.STATUS.INVALID_REQUEST);
		NetworkLayerNode node =  (NetworkLayerNode)super.node;
		
		switch(request.getPrimitive()){
		case  ASSOCIATE:
			Beacon abeacon = request.getBeacons().get(0);
			List<Beacon> neighbors = request.getBeacons().subList(1, request.getBeacons().size());
			try {
				if(node.associate(abeacon, neighbors)){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
			}
			break;
		case NETWORK_DISCOVERY:
			try {
				Map<RFChannel, List<Beacon>> availableNetworks = node.discovery(request.getBeacons().get(0));
				confirm.setAvailableNetworks(availableNetworks);
				confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
			} catch (Exception e1) {
				e1.printStackTrace();
				confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
			}
			break;
		case NETWORK_JOIN:
			try {
				List<Beacon> beacons = node.join(request.getChannel(), request.getBeacons().get(0), request.getBeacons().get(1));
				confirm.setBeacons(beacons);
				confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
			} catch (Exception e1) {
				e1.printStackTrace();
				confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
			}
			break;
		case REQUEST_EXT_PAN_ID:
			try {
				confirm.setExtendedPanId(node.requestExtenedPanId());
				confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
			} catch (Exception e1) {
				confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
				e1.printStackTrace();
			}
			break;
		case REQUEST_NETWORK_FORMATION:
			try {
				Beacon beacon = node.networkFormation(request.getBeacons().get(0));
				List<Beacon> beacons = new ArrayList<Beacon>();
				beacons.add(beacon);
				confirm.setBeacons(beacons);
				confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
			}
			break;
		case RETRANSMISSION:
			try {
				DataPackage data = node.retransmitData(request.getBeacons(), request.getPack());
				if(data!=null){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
					confirm.setData(data);
				}else{
					confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
				}
			} catch (Exception e) {
				confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
				e.printStackTrace();
			}
			break;
		case TRANSMISSION:
			try {
				DataPackage data = node.transmitData(request.getBeacons(), request.getData());
				if(data!=null){
					confirm.setStatus(LayerTcpResponse.STATUS.SUCCESS);
					confirm.setData(data);
				}else{
					confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
				}
			} catch (Exception e) {
				confirm.setStatus(LayerTcpResponse.STATUS.ERROR);
				e.printStackTrace();
			}
			break;
			default:
				break;
		}
		
		return null;
	}

}
