package org.uag.netsim.admin;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.uag.netsim.core.layer.LayerNode;

@Component
public class Main implements MainI{
	

	@Autowired
	@Qualifier("serverLayerClient")
	private ServerLayerClient _serverClient;
	final static Logger logger = Logger.getLogger(Main.class);
	
	@Autowired 
	private ApplicationContext appCtx;
	
	private ThreadPoolExecutor executor;
	
	public Main(){
		executor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(1);
	}
	
	private void start(String args[]) throws Exception{
		if (args.length == 0){
			logger.error("You must to provide a command (asadmin)");
			return;
		}
		String cmd = args[0];
		if(cmd.equals("asadmin")){
			if(args.length==1){
				logger.error("You must to provide a directive (start,stop)");
			}
			String directive = args[1];
			if(directive.equals("start")){
				if(_serverClient.getNodeHandlers().size()>0){
					logger.error("Server nodes already started");
				}else{
					ServerLayerNode serverNode = (ServerLayerNode)appCtx.getBean("serverLayerNode");
					serverNode.setMainInterface(this);
					executor.execute(serverNode);
					while(!serverNode.isReady());
					if(_serverClient.startNodes()){
						logger.info("Server has started succesfully");
					}else{
						logger.error("Error initializing server");
					}					
				}
			}else if(directive.equals("stop")){
				if(_serverClient.getNodeHandlers().size()==0){
					logger.error("Server has not started");
				}else if(_serverClient.stopNodes()){
					logger.info("Server stopped succesfully");
				}else{
					logger.error("Error stopping server");
				}
			}else{
				logger.error("Directive not recognized["+ directive+ "], You must to provide a directive (start,stop)");
			}
		}
	}

	public static void main(String args[]) throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/spring-ctx.xml");
		Main p = context.getBean(Main.class);
        p.start(args);
        
	}

	@Override
	public void release() throws InterruptedException {
		executor.shutdown();
		while (!executor.awaitTermination(2, TimeUnit.SECONDS));
	}
}
