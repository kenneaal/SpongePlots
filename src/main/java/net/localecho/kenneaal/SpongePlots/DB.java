package net.localecho.kenneaal.SpongePlots;

import java.io.IOException;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import ninja.leaping.configurate.ConfigurationNode;

public class DB {

	final static Logger logger = LoggerFactory.getLogger(SpongePlots.class);

	public static Logger getLogger(){
		return logger;
	}

	
	public static Connection getConnection(ConfigurationNode config){
		Connection mycon;
		mycon = null;
		getLogger().info("[SpongePlots/DB]: Got called.");
		//SpongePlots.doLog(0,"Well, shit. We just started a SQL connection!");
		/* try {
			int port = getConfigManager()
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		logger.info("Children list: "+config.getChildrenList());
		return mycon;
	}
}
