package org.uag.netsim.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.layer.AbstractLayerNode;
import org.uag.netsim.core.layer.DefaultLayerTcpConnection;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.app.AppLayerNode;
import org.uag.netsim.core.layer.mac.MacLayerNode;
import org.uag.netsim.core.layer.network.NetworkLayerNode;
import org.uag.netsim.core.layer.phy.PhyLayerNode;

@SuppressWarnings("rawtypes")
@Component("serverLayerNode")
@Scope("prototype")
public class ServerLayerNode extends AbstractLayerNode<ServerLayerRequestDispatcher
,ServerLayerTcpRequestDispatcher
,DefaultLayerTcpConnection
,ServerLayerClient> implements ServerLayerCommands{

	@Autowired 
	private ApplicationContext _appCtx;
	private ThreadPoolExecutor _nodePool;
	private List<LayerNode> _serverNodes;
	private MainI mainInterface;
	
	public ServerLayerNode() {
		super(ServerLayerRequestDispatcher.class,ServerLayerTcpRequestDispatcher.class,DefaultLayerTcpConnection.class,ServerLayerClient.class);
		_nodePool = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(50);
		_serverNodes = new ArrayList<LayerNode>();
	}

	public static int MIN_PORT_RANGE = 9292;
	public static int MAX_PORT_RANGE = 9292;
	
	@PostConstruct
	public void construct() throws Exception{
		minPortRange = MIN_PORT_RANGE;
		maxPortRange = MAX_PORT_RANGE;
		super.init();
	}
	public void setMainInterface(MainI mainInterface){
		this.mainInterface = mainInterface;
	}

	@Override
	public boolean startNodes() throws Exception {
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
		return true;
	}

	@Override
	public boolean stopNodes() throws Exception {
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
		release();
		return true;
	}
	
	public void release() throws Exception{
		_nodePool.shutdown();
//		while (!_nodePool.awaitTermination(2, TimeUnit.SECONDS));
		super.release();
		mainInterface.release();
	}
	
	private List<LayerNode> getPhyNodes(){
		List<LayerNode> nodes = new ArrayList<LayerNode>();
		for(LayerNode node:_serverNodes){
			if(node instanceof PhyLayerNode){
				nodes.add(node);
			}
		}
		return nodes;
	}
	private List<LayerNode> getNetworkNodes(){
		List<LayerNode> nodes = new ArrayList<LayerNode>();
		for(LayerNode node:_serverNodes){
			if(node instanceof NetworkLayerNode){
				nodes.add(node);
			}
		}
		return nodes;
	}
	private List<LayerNode> getMacNodes(){
		List<LayerNode> nodes = new ArrayList<LayerNode>();
		for(LayerNode node:_serverNodes){
			if(node instanceof MacLayerNode){
				nodes.add(node);
			}
		}
		return nodes;
	}
	private List<LayerNode> getAppNodes(){
		List<LayerNode> nodes = new ArrayList<LayerNode>();
		for(LayerNode node:_serverNodes){
			if(node instanceof AppLayerNode){
				nodes.add(node);
			}
		}
		return nodes;
	}

}
