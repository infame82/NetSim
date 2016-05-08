package org.uag.netsim.core.layer.app;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.layer.AbstractLayerNode;
import org.uag.netsim.core.layer.DefaultLayerTcpConnection;

@SuppressWarnings("rawtypes")
@Component("appLayerNode")
@Scope("prototype")
public class AppLayerNode extends AbstractLayerNode<AppLayerRequestDispatcher
,AppLayerTcpRequestDispatcher
,DefaultLayerTcpConnection
,AppLayerClient> {

	
	public AppLayerNode() {
		super(AppLayerRequestDispatcher.class,AppLayerTcpRequestDispatcher.class,DefaultLayerTcpConnection.class,AppLayerClient.class);
	}

	public static int MIN_PORT_RANGE = 9300;
	public static int MAX_PORT_RANGE = 9309;
	
	@PostConstruct
	public void construct() throws Exception{
		minPortRange = MIN_PORT_RANGE;
		maxPortRange = MAX_PORT_RANGE;
		super.init();
	}
	

	
}