package org.uag.netsim.core.layer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class AbstractLayerTcpConnection implements LayerTcpConnection,Runnable{

	private ServerSocket socket;
	private ThreadPoolExecutor requestExecutor;
	private boolean ready;
	public static int MAX_THREADS = 10;
	
	public AbstractLayerTcpConnection(int port) throws IOException{
		requestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		ready = false;
		socket = new ServerSocket(port);
	}
	
	public boolean isBusy() {
		return requestExecutor.getActiveCount()>=MAX_THREADS;
	}
	
	public int activeCount(){
		return requestExecutor.getActiveCount();
	}
	
	public void run(){
		ready = true;
	}
}
