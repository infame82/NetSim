package org.uag.netsim.core.layer;

import java.net.DatagramPacket;

import org.uag.netsim.core.ObjectSerializer;

public abstract class AbstractLayerRequestDispatcher implements LayerRequestDispatcher{
	protected DatagramPacket packet;
	protected LayerNode node;
	
	public AbstractLayerRequestDispatcher(LayerNode node,DatagramPacket packet) {
		this.packet = packet;
		this.node = node;
	}
	public void run() {
		try {
			Object obj = ObjectSerializer.unserialize(packet.getData());
			resolveRequest(obj);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public abstract void resolveRequest(Object obj) throws Exception;
}
