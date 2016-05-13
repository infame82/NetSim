package org.uag.netsim.core.layer.mac;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.layer.AbstractLayerNode;
import org.uag.netsim.core.layer.DefaultLayerTcpConnection;
import org.uag.netsim.core.layer.phy.PhyLayerClient;

@SuppressWarnings("rawtypes")
@Component("macLayerNode")
@Scope("prototype")
public class MacLayerNode extends AbstractLayerNode<MacLayerRequestDispatcher,MacLayerTcpRequestDispatcher
,DefaultLayerTcpConnection
,MacLayerClient> {

	@Autowired
	@Qualifier("phyLayerClient")
	private PhyLayerClient phyClient;
	
	public MacLayerNode() {
		super(MacLayerRequestDispatcher.class,MacLayerTcpRequestDispatcher.class ,DefaultLayerTcpConnection.class,MacLayerClient.class);
	}

	public static int MIN_PORT_RANGE = 9100;
	public static int MAX_PORT_RANGE = 9109;
	
	@PostConstruct
	public void construct() throws Exception{
		minPortRange = MIN_PORT_RANGE;
		maxPortRange = MAX_PORT_RANGE;
		super.init();
	}

}
