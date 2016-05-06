package org.uag.netsim.core.layer.mgmt;

import javax.annotation.PostConstruct;

import org.uag.netsim.core.layer.AbstractLayerNode;

/**
 * Created by david on 21/02/16.
 */
public class MgmtLayerNode extends AbstractLayerNode<MgmtLayerRequestDispatcher,MgmtLayerTcpConnection> {

    public MgmtLayerNode() {
        super(MgmtLayerRequestDispatcher.class, MgmtLayerTcpConnection.class);
    }

    public static int MIN_PORT_RANGE = 9400;
    public static int MAX_PORT_RANGE = 9499;

    @PostConstruct
    public void construct() throws Exception{
        minPortRange = MIN_PORT_RANGE;
        maxPortRange = MAX_PORT_RANGE;
        init();
    }
}
