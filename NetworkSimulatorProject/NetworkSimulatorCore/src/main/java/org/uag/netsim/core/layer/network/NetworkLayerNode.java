package org.uag.netsim.core.layer.network;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.layer.AbstractLayerNode;
import org.uag.netsim.core.layer.DefaultLayerTcpConnection;
import org.uag.netsim.core.layer.mac.MacLayerClient;

@SuppressWarnings("rawtypes")
@Component("networkLayerNode")
@Scope("prototype")
public class NetworkLayerNode extends AbstractLayerNode<NetworkLayerRequestDispatcher
,NetworkLayerTcpRequestDispatcher
,DefaultLayerTcpConnection
,NetworkLayerClient> {
	
	@Autowired
	@Qualifier("macLayerClient")
	private MacLayerClient macClient;

	public NetworkLayerNode() {
		super(NetworkLayerRequestDispatcher.class,NetworkLayerTcpRequestDispatcher.class,DefaultLayerTcpConnection.class,NetworkLayerClient.class);
	}

	public static int MIN_PORT_RANGE = 9200;
	public static int MAX_PORT_RANGE = 9209;
	
	@PostConstruct
	public void construct() throws Exception{
		minPortRange = MIN_PORT_RANGE;
		maxPortRange = MAX_PORT_RANGE;
		super.init();
		
	}
	
}
