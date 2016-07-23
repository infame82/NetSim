package org.uag.netsim.core.device;

import java.awt.Point;
import java.io.Serializable;
import java.net.InetAddress;

public class Beacon implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 58324054455863864L;
	
	public enum DEVICE_TYPE{COORDINATOR,ROUTER,ENDPOINT};
	public enum RF_CHANNEL{CH_11(2.4000F),CH_12(2.4070F),CH_13(2.4140F),CH_14(2.4210F),CH_15(2.4280F),
		CH_16(2.4350F),CH_17(2.4420F),CH_18(2.4490F),CH_19(2.4F),CH_20(2.4560F),CH_21(2.4630F),CH_22(2.4700F),
		CH_23(2.4770F),CH_24(2.4835F);
		private float frequency;
		private RF_CHANNEL(float frequency) {
			this.frequency = frequency;
		}
		public float getFrecuency() {
			return frequency;
		}
	};
	
	private String id;
	private int panId;
	private int extendedPanId;
	
	private Point location;
	private int potency;
	private boolean allowJoin;
	private boolean coordinator;
	private boolean router;
	private InetAddress ip;
	private int port;
	
	private DEVICE_TYPE type = DEVICE_TYPE.ENDPOINT;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPanId() {
		return panId;
	}
	public void setPanId(int panId) {
		this.panId = panId;
	}
	public int getExtendedPanId() {
		return extendedPanId;
	}
	public void setExtendedPanId(int extendedPanId) {
		this.extendedPanId = extendedPanId;
	}
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public int getPotency() {
		return potency;
	}
	public void setPotency(int potency) {
		this.potency = potency;
	}
	public boolean isAllowJoin() {
		return allowJoin;
	}
	public void setAllowJoin(boolean allowJoin) {
		this.allowJoin = allowJoin;
	}
	public boolean isCoordinator() {
		return coordinator;
	}
	public void setCoordinator(boolean coordinator) {
		this.coordinator = coordinator;
	}
	public boolean isRouter() {
		return router;
	}
	public void setRouter(boolean router) {
		this.router = router;
	}
	public InetAddress getIp() {
		return ip;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public DEVICE_TYPE getType() {
		return type;
	}
	public void setType(DEVICE_TYPE type) {
		this.type = type;
	}
	
	
	
}
