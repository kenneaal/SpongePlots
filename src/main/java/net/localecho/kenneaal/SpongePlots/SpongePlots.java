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
	ConfigurationNode config = null;	
	@Inject
	Logger logger;

	public Logger getLogger(){
		return logger;
	}

	@Inject
	@DefaultConfig(sharedRoot=true)
	private File defaultConfig;

	@Inject
	@DefaultConfig(sharedRoot=true)
	private ConfigurationLoader configManager;
	
	public File getDefaultConfig(){
		return defaultConfig;
	}
	public ConfigurationLoader getConfigManager(){
		return configManager;
	}
	
	@Subscribe
	public void onPreInitialization(PreInitializationEvent event){
		getLogger().info("[SpongePlots]: Starting up da SpongePlots.");
		
		if(!getDefaultConfig().exists()){
			try{
				getDefaultConfig().createNewFile();
				config = getConfigManager().load();
				
				config.getNode("ConfigVersion").setValue(1);
				config.getNode("DB","Host").setValue("127.0.0.1");
				config.getNode("DB","Port").setValue(3306);
				config.getNode("DB","Username").setValue("SpongePlots");
				config.getNode("DB","Password").setValue("YouReallyShouldChangeMe");
				config.getNode("DB","Configured").setValue(0);
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
		Connection dbcon = DB.getConnection(config.getNode("DB"));
		getLogger().info("[SpongePlots]: I called DB for a connection.");
	}

	public void doLog(int severity, String message){
		switch(severity){
		case 0: getLogger().info("[SpongePlots]: "+message);
		case 1: getLogger().error("[SpongePlots]: "+message);
		
		}
	}
}
