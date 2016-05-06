package org.uag.netsim.core.layer.mgmt;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.uag.netsim.core.DefaultCoreLog;
import org.uag.netsim.core.layer.LayerNode;
import org.uag.netsim.core.layer.LayerTcpConnectionHandler;

@ContextConfiguration(locations = { "classpath:META-INF/spring/spring-ctx.xml" })
public class MgmtLayerNodeTest extends AbstractTestNGSpringContextTests{

	@Autowired
	@Qualifier("mgmtLayerNode")
	private LayerNode node_01;
	
	@Autowired
	@Qualifier("mgmtLayerNode")
	private LayerNode node_02;
	
	private ThreadPoolExecutor requestExecutor;
	
	@BeforeTest
	public void initTest(){
		requestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(10);
	}
	
	@Test
	public void test() throws Exception{
		requestExecutor.execute(node_01);
		requestExecutor.execute(node_02);
		while(!node_01.isReady());
		while(!node_02.isReady());
		MgmtLayerClient client = new MgmtLayerClient(new DefaultCoreLog());		
		LayerTcpConnectionHandler tcpHandler = client.requestTcpNode();
		assert node_01.isReady() && node_02.isReady();
	}
	
	@AfterTest
	public void destroyTest(){
		node_01.release();
		node_02.release();
	}
}
