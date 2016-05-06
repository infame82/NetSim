package org.uag.netsim.core.layer.app;

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
public class AppLayerNodeTest extends AbstractTestNGSpringContextTests{

	@Autowired
	@Qualifier("appLayerNode")
	private LayerNode appLayer_01;
	
	@Autowired
	@Qualifier("appLayerNode")
	private LayerNode appLayer_02;
	
	private ThreadPoolExecutor requestExecutor;
	
	@BeforeTest
	public void initTest(){
		requestExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(10);
	}
	
	@Test
	public void test() throws Exception{
		requestExecutor.execute(appLayer_01);
		requestExecutor.execute(appLayer_02);
		while(!appLayer_01.isReady());
		while(!appLayer_02.isReady());
		AppLayerClient client = new AppLayerClient(new DefaultCoreLog());		
		LayerTcpConnectionHandler tcpHandler = client.requestTcpNode();
		assert appLayer_01.isReady() && appLayer_02.isReady();
	}
	
	@AfterTest
	public void destroyTest(){
		appLayer_01.release();
		appLayer_02.release();
	}
}
