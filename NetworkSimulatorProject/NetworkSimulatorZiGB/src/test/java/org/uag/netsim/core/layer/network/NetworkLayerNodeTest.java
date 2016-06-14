package org.uag.netsim.core.layer.network;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.uag.netsim.core.DefaultCoreLog;
import org.uag.netsim.core.layer.LayerTcpConnectionHandler;

@ContextConfiguration(locations = { "classpath:META-INF/spring/spring-ctx.xml" })
public class NetworkLayerNodeTest extends AbstractTestNGSpringContextTests{

	/*@Autowired
	@Qualifier("networkLayerNode")
	private LayerNode node_01;
	
	@Autowired
	@Qualifier("networkLayerNode")
	private LayerNode node_02;
	
private ThreadPoolExecutor requestExecutor;*/
	
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
	public void test() throws Exception{
		
		NetworkLayerClient client = 
				new NetworkLayerClient(new DefaultCoreLog());
		LayerTcpConnectionHandler nlmeHandler = client.openNLMESAP();
		client.closeNLMESAP(nlmeHandler.getPort());
		//LayerTcpConnectionHandler tcpHandler = client.requestTcpNode();
		//assert node_01.isReady() && node_02.isReady();
	}
	
	@AfterMethod
	public void destroyTest() throws Exception{
		/*node_01.release();
		node_02.release();*/
	}
}
