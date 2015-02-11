/*
 * This file is part of SpongePlots, licensed under the MIT License (MIT).
 *
 * Copyright (c) Kenneth Aalberg <http://github.com/kenneaal/SpongePlots>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.localecho.kenneaal.SpongePlots;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.entity.living.player.PlayerJoinEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.util.event.Subscribe;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.Server;

import com.google.common.base.Optional;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import com.google.inject.Guice;
import com.google.inject.Inject;

import net.localecho.kenneaal.SpongePlots.DB;
import net.localecho.kenneaal.SpongePlots.Commands;

@Plugin(id = SpongePlots.NAME, name="SpongePlots", version="0.1")

public class SpongePlots {
	private Game game;
	public static final String NAME = "SpongePlots";
	private ConfigurationNode config = null;	
	private static Logger logger;
	private Optional<PluginContainer> pluginContainer;
	
	public SpongePlots() {
	}
	
	public static Logger getLogger() {
		return logger;
	}

	@Inject
	@DefaultConfig(sharedRoot=true)
	private File defaultConfig;

	@Inject
	@DefaultConfig(sharedRoot=true)
	private ConfigurationLoader configManager;
	
	public File getDefaultConfig() {
		return defaultConfig;
	}
	public ConfigurationLoader getConfigManager() {
		return configManager;
	}

	@Subscribe
	public void onPlayerJoin(PlayerJoinEvent event){
		event.getPlayer().sendMessage("Welcome to a SpongePlots powered server!");
	}
	
	@Subscribe
	public void onPreInitialization(PreInitializationEvent event) {
		game = event.getGame();		
		pluginContainer = game.getPluginManager().getPlugin(SpongePlots.NAME);
		logger=game.getPluginManager().getLogger(pluginContainer.get());
		getLogger().info("[SpongePlots]: SpongePlots is initializing.");

		try{
		if(!getDefaultConfig().exists()) {
				getDefaultConfig().createNewFile();
				config = getConfigManager().load();
				config.getNode("configVersion").setValue(1);
				config.getNode("DB","host").setValue("127.0.0.1");
				config.getNode("DB","port").setValue(3306);
				config.getNode("DB","username").setValue("SpongePlots");
				config.getNode("DB","password").setValue("YouReallyShouldChangeMe");
				config.getNode("DB","database").setValue("SpongePlots");
				config.getNode("DB","configured").setValue(false);
				config.getNode("DB","requiredVersion").setValue(1);
				getConfigManager().save(config);
				getLogger().info(String.format("[%s]: Created default configuration, SpongePlots will not run until you have edited this file!",SpongePlots.NAME));
			}
			config = getConfigManager().load();
			getLogger().info(String.format("[%s]: Loaded master configuration.",SpongePlots.NAME));
		}catch (IOException exception) {
				getLogger().error("[SpongePlots]: Couldn't create default configuration file!");
			}
		}

	@Subscribe
	public void onInitialization(InitializationEvent event) {
		Connection db; 
		if(config.getNode("DB","configured").getBoolean()!=true){
			getLogger().info("[SpongePlots] You haven't configured %s's database. Exiting. Value is "+config.getNode("DB","configured").getBoolean());
			return;
		}
		getLogger().info("[SpongePlots]: Connecting to backend.");
		db = DB.getConnection(config.getNode("DB"));
		if(db==null) {
			getLogger().error("[SpongePlots]: Critical error: Couldn't get a valid database connection. Going inert.");
			return;
		}
		getLogger().info("[SpongePlots]: Connected to database.");
		DB.createTables(db, 1);
		/* try{
			ResultSet rs = db.prepareStatement("SELECT dbversion from master").executeQuery();
			
		}*/
		ser
	}
	
	@Subscribe
	public void OnServerStared(ServerStartedEvent event) {
		getLogger().info("[SpongePlots]: Ready and willing.");
		// Start registering some commands here, yes?		
		CommandService cmdService = game.getCommandDispatcher();
		cmdService.register(pluginContainer, Commands.MyTestCommand(server),"message","broadcast");
	}
}
