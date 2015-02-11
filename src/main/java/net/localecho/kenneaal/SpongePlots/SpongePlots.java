package net.localecho.kenneaal.SpongePlots;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.util.event.Subscribe;
import org.spongepowered.api.service.config.DefaultConfig;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import com.google.inject.Inject;

import net.localecho.kenneaal.SpongePlots.DB;

@Plugin(id = "SpongePlots", name="SpongePlots", version="0,1")

public class SpongePlots {
	Game game;
	
	@Inject
	static Logger logger;

	public static Logger getLogger(){
		return logger;
	}

	@Inject
	@DefaultConfig(sharedRoot=false)
	private File defaultConfig;

	@Inject
	@DefaultConfig(sharedRoot=false)
	static private ConfigurationLoader configManager;
	
	public File getDefaultConfig(){
		return defaultConfig;
	}
	public static ConfigurationLoader getConfigManager(){
		return configManager;
	}
	
	@Subscribe
	public void onPreInitialization(PreInitializationEvent event){
		getLogger().info("[SpongePlots]: Starting up da SpongePlots.");
		ConfigurationNode config = null;
		
		if(!getDefaultConfig().exists()){
			try{
				getDefaultConfig().createNewFile();
				config = getConfigManager().load();
				
				config.getNode("version").setValue(1);
				config.getNode("DBprovider").setValue("MySQL");
				config.getNode("SQLHost").setValue("127.0.0.1");
				config.getNode("SQLUsername").setValue("SpongePlots");
				config.getNode("SQLPassword").setValue("YouReallyShouldChangeMe");
				config.getNode("SQLPort").setValue(3306);
				config.getNode("DBconfigured").setValue(0);
				getConfigManager().save(config);
				getLogger().info("[SpongePlots]: Created default configuration, SpongePlots will not run until you have edited this file!");
			} catch (IOException exception){
				getLogger().error("Couldn't create default configuration file!");
			}
		int version = config.getNode("version").getInt();
		getLogger().info("Configfile version is now "+version+".");
		}
	}
	@Subscribe
	public void onServerStart(ServerStartedEvent event){
		// Here it is!
		getLogger().info("[SpongePlots]: Up and running.");
		Connection dbcon = DB.getConnection();
		getLogger().info("[SpongePlots]: I called DB for a connection.");
	}

	
}
