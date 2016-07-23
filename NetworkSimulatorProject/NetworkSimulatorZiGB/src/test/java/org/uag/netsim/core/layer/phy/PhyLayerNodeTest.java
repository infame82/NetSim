package org.uag.netsim.core.layer.phy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.uag.netsim.core.device.Beacon.RF_CHANNEL;

@ContextConfiguration(locations = { "classpath:META-INF/spring/spring-ctx.xml" })
public class PhyLayerNodeTest extends AbstractTestNGSpringContextTests{


	@Autowired
	@Qualifier("phyLayerClient")
	private PhyLayerClient client;
	
	@BeforeMethod
	public void initTest(){

	}
	
	@Test
	public void increaseEnergyLevelTest() throws Exception{
		assert client.increaseEnergyLevel(RF_CHANNEL.CH_11);
	}
	
	@Test
	public void getChannelsTest() throws Exception{
		List<RFChannel> channels =  client.getChannels();
		assert channels!=null && channels.size()>0;
	}
	
	@AfterMethod
	public void destroyTest() throws Exception{

	}
}
