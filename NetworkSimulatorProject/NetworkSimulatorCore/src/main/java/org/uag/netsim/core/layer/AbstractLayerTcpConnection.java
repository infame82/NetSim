package org.uag.netsim.core.layer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class AbstractLayerTcpConnection implements LayerTcpConnection{

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
	
	public int getPort(){
		return socket.getLocalPort();
	}
	
	public InetAddress getHost(){
		return socket.getInetAddress();
	}
	
	public boolean isBusy() {
		return requestExecutor.getActiveCount()>=MAX_THREADS;
	}
	
	public int getActiveCount(){
		return requestExecutor.getActiveCount();
	}
	
	public void run(){
		ready = true;
	}
}
