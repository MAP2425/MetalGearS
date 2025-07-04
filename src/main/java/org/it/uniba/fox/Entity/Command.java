package org.it.uniba.fox.Entity;
import java.util.List;
import  org.it.uniba.fox.Type.CommandType;


public class Command {

    private final String name;

    private final List <String> aliases;

    private final CommandType type;

    public Command(String name, List <String> aliases, CommandType type){
        this.name=name;
        this.aliases=aliases;
        this.type=type;
    }

    public String getName(){
        return  this.name;
    }

    public List <String> getAliases(){
        return this.aliases;
    }

    public CommandType getType(){
        return this.type;
    }
}
