package org.uag.netsim.core;

public interface ICoreLog {

	void sendError(String message);
	
	void sendInfo(String message);
	
	void sendDebug(String message);
	
	boolean IsEnabled();
	
	void setEnableLog(boolean enable);
	
}
