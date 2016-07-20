package org.uag.netsim.core.layer.mac;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.uag.netsim.core.device.Beacon;
import org.uag.netsim.core.layer.phy.RFChannel;

@ContextConfiguration(locations = { "classpath:META-INF/spring/spring-ctx.xml" })
public class MacLayerNodeTest extends AbstractTestNGSpringContextTests{

	@Autowired
	@Qualifier("macLayerClient")
	private MacLayerClient client ;
	/*@Autowired
	@Qualifier("macLayerNode")
	private LayerNode node_01;
	
	@Autowired
	@Qualifier("macLayerNode")
	private LayerNode node_02;
	
private ThreadPoolExecutor requestExecutor;
*/	
	@BeforeMethod
	public void initTest(){
		/*requestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(10);
		requestExecutor.execute(node_01);
		requestExecutor.execute(node_02);
		while(!node_01.isReady());
		while(!node_02.isReady());*/
	}
	
	@Test
	public void energyDetectionScanTest() throws Exception{
		List<RFChannel> channels = client.energyDetectionScan();
		assert channels!=null;
	}
	
	@Test
	public void activeScanTest(){
		Beacon beacon = new Beacon();
		beacon.setLocation(new Point(5,5));
		beacon.setPotency(5);
		List<RFChannel> channels = client.energyDetectionScan();
		Map<RFChannel,List<Beacon>> activeChannels = client.activeScan(channels, beacon);
		assert activeChannels!=null;
	}
	
	@AfterMethod
	public void destroyTest() throws Exception{
		/*node_01.release();
		node_02.release();*/
	}
}
