package org.uag.netsim.core.layer.phy;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.layer.AbstractLayerNode;
import org.uag.netsim.core.layer.DefaultLayerTcpConnection;

@SuppressWarnings("rawtypes")
@Component("phyLayerNode")
@Scope("prototype")
public class PhyLayerNode extends AbstractLayerNode<PhyLayerRequestDispatcher
,PhyLayerTcpRequestDispatcher
,DefaultLayerTcpConnection
,PhyLayerClient> {
	

	public PhyLayerNode() {
		super(PhyLayerRequestDispatcher.class,PhyLayerTcpRequestDispatcher.class,DefaultLayerTcpConnection.class,PhyLayerClient.class);
	}

	public static int MIN_PORT_RANGE = 9500;
	public static int MAX_PORT_RANGE = 9509;
	
	@PostConstruct
	public void construct() throws Exception{
		minPortRange = MIN_PORT_RANGE;
		maxPortRange = MAX_PORT_RANGE;
		super.init();
		
	}
}
