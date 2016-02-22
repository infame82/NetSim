package org.uag.netsim.core.layer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class AbstractLayerTcpConnection<D extends LayerTcpRequestDispatcher> implements LayerTcpConnection{

	private final Class<D> dispatcherClass;

	private ServerSocket socket;
	private ThreadPoolExecutor requestExecutor;
	private boolean ready;
	public static int MAX_THREADS = 10;
	
	public AbstractLayerTcpConnection(Class<D> dispatcherClass,int port) throws IOException{
		this.dispatcherClass = dispatcherClass;
		requestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(MAX_THREADS);
		ready = false;
		socket = new ServerSocket(port);
		
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
		requestExecutor.shutdownNow();
		ready = false;
		try {
			if(socket!=null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public int getActiveCount(){
		return requestExecutor.getActiveCount();
	}
	
	public void run(){
		ready = true;
		try {
			while (ready) {
				LayerTcpRequestDispatcher dispatcher  =
						dispatcherClass.getConstructor(Socket.class).newInstance(socket.accept());
				requestExecutor.execute(dispatcher);
			}
		}catch(Exception e){

		}
	}
}
