package net.localecho.kenneaal.SpongePlots;

import java.util.Collections;
import java.util.List;

import org.spongepowered.api.Server;
import org.spongepowered.api.text.message.Messages;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;

public class Commands implements CommandCallable{
	private Server server;
	private final Optional<String> desc= Optional.of("Displays a message to all players");
	private final Optional<String> help = Optional.of("Displays a message to all players. It has no colors!");
	
	public boolean MyTestCommand(Server server){
		this.server = server;
		return true;
	}

	public List<String> getSuggestions(CommandSource source, String arguments)
			throws CommandException {
		return Collections.emptyList();
	}

	public boolean call(CommandSource source, String arguments,
			List<String> parents) throws CommandException {
		server.broadcastMessage(Messages.of(arguments));
		return false;
	}

	public boolean testPermission(CommandSource source) {
		return source.hasPermission("example.exampleCommand");
	}

	public Optional<String> getShortDescription() {
		// TODO Auto-generated method stub
		return desc;
	}

	public Optional<String> getHelp() {
		// TODO Auto-generated method stub
		return help;
	}

	public String getUsage() {
		// TODO Auto-generated method stub
		return "/<command> <message>";
	}
}
