package org.it.uniba.fox.Entity;
import java.util.List;
import  org.it.uniba.fox.Type.CommandType;

/**
 * Represents a command in the game, with its name, aliases, and type.
 */
public class Command {

    /**
     * The name of the command.
     */
    private final String name;

    /**
     * The aliases for the command.
     */
    private final List <String> aliases;

    /**
     * The type of the command.
     */
    private final CommandType type;

    /**
     * Constructs a Command with the specified name, aliases, and type.
     *
     * @param name the name of the command
     * @param aliases the list of aliases for the command
     * @param type the type of the command
     */
    public Command(String name, List <String> aliases, CommandType type){
        this.name=name;
        this.aliases=aliases;
        this.type=type;
    }

    /**
     * Returns the name of the command.
     *
     * @return the command name
     */
    public String getName(){
        return  this.name;
    }

    /**
     * Returns the list of aliases for the command.
     *
     * @return the list of command aliases
     */
    public List <String> getAliases(){
        return this.aliases;
    }

    /**
     * Returns the type of the command.
     *
     * @return the command type
     */
    public CommandType getType(){
        return this.type;
    }
}
