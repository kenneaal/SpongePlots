package net.localecho.kenneaal.SpongePlots;

//import java.io.IOException;
import java.sql.Connection;

import static net.localecho.kenneaal.SpongePlots.SpongePlots.getLogger;
//import static net.localecho.kenneaal.SpongePlots.SpongePlots.getConfigManager;
//import ninja.leaping.configurate.ConfigurationNode;

public class DB {

	public static Connection getConnection(){
		Connection mycon;
		mycon = null;
		getLogger().info("Well shit, we just tried to start a SQL connection!");
		/* try {
			int port = getConfigManager()
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		return mycon;
	}
}
