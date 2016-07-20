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
