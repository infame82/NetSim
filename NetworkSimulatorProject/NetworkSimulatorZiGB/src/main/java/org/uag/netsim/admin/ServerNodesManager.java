package org.uag.netsim.admin;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.app.AppLayerNode;
import org.uag.netsim.core.layer.mac.MacLayerNode;
import org.uag.netsim.core.layer.network.NetworkLayerNode;
import org.uag.netsim.core.layer.phy.PhyLayerNode;

@Service("serverNodesManager")
public class ServerNodesManager implements Runnable{
	
	private static final int MSG_LENGHT = 512;
	
	@Autowired 
	private ApplicationContext _appCtx;
	private List<LayerNode> _serverNodes;
	private ThreadPoolExecutor _nodePool;
	private ThreadPoolExecutor _requestExecutor;
	private int port;
	private DatagramSocket socket;
	private boolean ready;
	final static Logger logger = Logger.getLogger(ServerNodesManager.class);
	
	
	class ServerRequestDispatcher implements Runnable{
		private DatagramPacket _packet;
		
		public ServerRequestDispatcher(DatagramPacket packet){
			this._packet = packet;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub			
		}
		
	}
	
	
	public ServerNodesManager(){
		_serverNodes = new ArrayList<LayerNode>();
		_nodePool = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(50);
		_requestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(10);
		port = -1;
		ready = false;
	}
		
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isRunning(){
		return _serverNodes.size()>0;
	}
	
	public void stop() throws Exception{
		for(LayerNode node:getAppNodes()){
			node.release();
		}
		for(LayerNode node:getNetworkNodes()){
			node.release();
		}
		for(LayerNode node:getMacNodes()){
			node.release();
		}
		for(LayerNode node:getPhyNodes()){
			node.release();
		}
	}
	
	public void start(){
		if(isRunning()){
			return;
		}
		LayerNode phyNode = (LayerNode)_appCtx.getBean("phyLayerNode");
		_nodePool.execute(phyNode);
		while(!phyNode.isReady());
		_serverNodes.add(phyNode);
		
		LayerNode macNode = (LayerNode)_appCtx.getBean("macLayerNode");
		_nodePool.execute(macNode);
		while(!macNode.isReady());
		_serverNodes.add(macNode);
		
		LayerNode ntwNode = (LayerNode)_appCtx.getBean("networkLayerNode");
		_nodePool.execute(ntwNode);
		while(!ntwNode.isReady());
		_serverNodes.add(ntwNode);
		
		LayerNode appNode = (LayerNode)_appCtx.getBean("appLayerNode");
		_nodePool.execute(appNode);
		while(!appNode.isReady());		
		_serverNodes.add(appNode);
	}
	
	public List<LayerNode> getPhyNodes(){
		List<LayerNode> nodes = new ArrayList<LayerNode>();
		for(LayerNode node:_serverNodes){
			if(node instanceof PhyLayerNode){
				nodes.add(node);
			}
		}
		return nodes;
	}
	public List<LayerNode> getNetworkNodes(){
		List<LayerNode> nodes = new ArrayList<LayerNode>();
		for(LayerNode node:_serverNodes){
			if(node instanceof NetworkLayerNode){
				nodes.add(node);
			}
		}
		return nodes;
	}
	public List<LayerNode> getMacNodes(){
		List<LayerNode> nodes = new ArrayList<LayerNode>();
		for(LayerNode node:_serverNodes){
			if(node instanceof MacLayerNode){
				nodes.add(node);
			}
		}
		return nodes;
	}
	public List<LayerNode> getAppNodes(){
		List<LayerNode> nodes = new ArrayList<LayerNode>();
		for(LayerNode node:_serverNodes){
			if(node instanceof AppLayerNode){
				nodes.add(node);
			}
		}
		return nodes;
	}

	@Override
	public void run() {
		byte[] buffer = new byte[MSG_LENGHT];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		try {
			if(port==-1){			
				socket = new DatagramSocket();
				port = socket.getPort();
			}else{
				socket = new DatagramSocket(port);
			}
			logger.info("Listening on Port "+port);
			ready = true;
			while(ready){
				socket.receive(packet);
				_requestExecutor.execute(new ServerRequestDispatcher(packet));
				buffer = new byte[MSG_LENGHT];
				packet = new DatagramPacket(buffer, buffer.length);
			}
		} catch ( IOException e) {
			e.printStackTrace();
		}		
	}
}
