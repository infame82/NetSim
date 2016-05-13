package org.uag.netsim.core.layer.app;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.uag.netsim.core.layer.LayerNode;

@ContextConfiguration(locations = { "classpath:META-INF/spring/spring-ctx.xml" })
public class AppLayerNodeTest extends AbstractTestNGSpringContextTests{

	@Autowired
	@Qualifier("appLayerNode")
	private LayerNode appLayer_01;
	
	/*@Autowired
	@Qualifier("appLayerNode")
	private LayerNode appLayer_02;*/
	//
	private ThreadPoolExecutor requestExecutor;
		
	@Test
	public void test() throws Exception{
		//appLayer_01 = new AppLayerNode();
				requestExecutor = (ThreadPoolExecutor) Executors
						.newFixedThreadPool(10);
				requestExecutor.execute(appLayer_01);
				//requestExecutor.execute(appLayer_02);
				while(!appLayer_01.isReady());
				//while(!appLayer_02.isReady());

	}
	
	@AfterClass
	public void destroyTest(){
		appLayer_01.release();
		//appLayer_02.release();
	}
}
