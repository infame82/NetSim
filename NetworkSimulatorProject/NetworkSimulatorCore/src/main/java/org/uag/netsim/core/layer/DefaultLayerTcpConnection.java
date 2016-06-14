package org.uag.netsim.core.layer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DefaultLayerTcpConnection< TD extends LayerTcpRequestDispatcher> implements LayerTcpConnection{

	private final Class<TD> dispatcherClass;

	private ServerSocket socket;
	private ThreadPoolExecutor requestExecutor;
	private boolean ready;
	public static int MAX_THREADS = 10;
	protected LayerNode node;
	
	public DefaultLayerTcpConnection(LayerNode node,Class<TD> dispatcherClass) throws IOException{
		this.dispatcherClass = dispatcherClass;
		requestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		ready = false;
		socket = new ServerSocket(0);
		this.node = node;
	}
	
	public int getPort(){
		return socket.getLocalPort();
	}
	
	public InetAddress getHost(){
		try {
			return InetAddress.getLocalHost();
		}catch(Exception e){
			return socket.getInetAddress();
		}
	}
	
	public boolean isBusy() {
		return requestExecutor.getActiveCount()>=MAX_THREADS;
	}

	public void release(){
		
		ready = false;
		try {
			if(socket!=null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		requestExecutor.shutdownNow();
	}
	
	public int getActiveCount(){
		return requestExecutor.getActiveCount();
	}
	
	public void run(){
		ready = true;
		try {
			while (ready) {
				LayerTcpRequestDispatcher dispatcher  =
						dispatcherClass.getConstructor(LayerNode.class,Socket.class).newInstance(node,socket.accept());
				requestExecutor.execute(dispatcher);
			}
		}catch(Exception e){

		}
	}
}
