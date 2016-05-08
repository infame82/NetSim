package org.uag.netsim.core.layer.mgmt;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.layer.AbstractLayerNode;
import org.uag.netsim.core.layer.DefaultLayerTcpConnection;

/**
 * Created by david on 21/02/16.
 */
@SuppressWarnings("rawtypes")
@Component("mgmtLayerNode")
@Scope("prototype")
public class MgmtLayerNode extends AbstractLayerNode<MgmtLayerRequestDispatcher,MgmtLayerTcpRequestDispatcher,DefaultLayerTcpConnection,MgmtLayerClient> {

	
    public MgmtLayerNode() {
        super(MgmtLayerRequestDispatcher.class,MgmtLayerTcpRequestDispatcher.class, DefaultLayerTcpConnection.class,MgmtLayerClient.class);
    }

    public static int MIN_PORT_RANGE = 9400;
    public static int MAX_PORT_RANGE = 9409;

    @PostConstruct
    public void construct() throws Exception{
        minPortRange = MIN_PORT_RANGE;
        maxPortRange = MAX_PORT_RANGE;
        init();
    }
}
