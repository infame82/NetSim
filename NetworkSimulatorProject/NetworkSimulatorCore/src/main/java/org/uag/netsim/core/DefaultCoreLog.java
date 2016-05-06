package org.uag.netsim.core;

public class DefaultCoreLog implements ICoreLog {
	
	private boolean IsEnabled = false;

	@Override
	synchronized public void sendError(String message) {
		if(IsEnabled)
			System.err.println(message);
	}

	@Override
	synchronized public void sendInfo(String message) {
		if(IsEnabled)
			System.out.println(message);
	}

	@Override
	synchronized public void sendDebug(String message) {
		if(IsEnabled)
			System.out.println(message);
	}

	@Override
	 public boolean IsEnabled() {
		return IsEnabled;
	}
	
	 public void setEnableLog(boolean enable){
		this.IsEnabled = enable;
	}

}
