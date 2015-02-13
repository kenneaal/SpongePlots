package net.localecho.kenneaal.SpongePlots;

import java.util.Arrays;
import java.util.List;

import org.spongepowered.api.Server;
import org.spongepowered.api.text.message.Messages;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;

public class Commands implements CommandCallable {
    private final Optional<Server> server;
    private final Optional<String> desc = Optional
            .of("Provides commands for SpongePlots.");
    private final Optional<String> help = Optional
            .of("Provides commands for SpongePlots.");

    public Commands() {
        this.server = SpongePlots.getGame().getServer();
    }

    public boolean call(CommandSource source, String arguments,
            List<String> parents) throws CommandException {
        this.server.get().broadcastMessage(
                Messages.of("SpongePlots: " + arguments));
        return false;
    }

    public Optional<String> getHelp() {
        // TODO Auto-generated method stub
        return this.help;
    }

    public Optional<String> getShortDescription() {
        // TODO Auto-generated method stub
        return this.desc;
    }

    public List<String> getSuggestions(CommandSource source, String arguments)
            throws CommandException {
        return Arrays.asList("foo", "bar", "baz");
    }

    public String getUsage() {
        // TODO Auto-generated method stub
        return "/<command> <message>";
    }

    public boolean testPermission(CommandSource source) {
        return source.hasPermission("SpongePlots.mainCommand");
    }
}
