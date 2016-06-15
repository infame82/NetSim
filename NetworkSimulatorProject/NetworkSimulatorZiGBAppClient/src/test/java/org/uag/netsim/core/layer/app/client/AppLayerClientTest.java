package org.uag.netsim.core.layer.app.client;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import org.uag.netsim.core.DefaultCoreLog;
import org.uag.netsim.core.layer.LayerTcpConnectionHandler;

@ContextConfiguration(locations = { "classpath:META-INF/spring/spring-ctx.xml" })
public class AppLayerClientTest extends AbstractTestNGSpringContextTests{


	@Test(threadPoolSize = 2000, invocationCount = 1)
	public void test() throws Exception{
		
	//	for(int i=0;i<3;i++)
		AppLayerClient client = new AppLayerClient(new DefaultCoreLog());		
		LayerTcpConnectionHandler h = client.openAPSDESAP();
		client.closeAPSDESAP(h.getPort());
		//LayerTcpConnectionHandler tcpHandler = client.requestTcpNode();
		//assert appLayer_01.isReady() ;//&& appLayer_02.isReady();
	}
	

}
