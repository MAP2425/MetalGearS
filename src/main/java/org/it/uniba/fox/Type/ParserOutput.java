package org.it.uniba.fox.Type;

import org.it.uniba.fox.Entity.Agent;
import org.it.uniba.fox.Entity.Item;

/**
 * The class representing the parser output.
 * It extends the CommandExecutorKey class by adding the number of arguments.
 */
public class ParserOutput {
    /**
     * The command type
     */
    private CommandType command;
    /**
     * The first argument (can be Item or Agent)
     */
    private Object arg1;
    /**
     * The second argument (can be Item or Agent)
     */
    private Object arg2;
    /**
     * The number of arguments.
     */
    private int args;

    /**
     * Constructor of the class.
     */
    public ParserOutput() {
        args = 0;
    }

    /**
     * Gets the number of arguments.
     *
     * @return the args
     */
    public int getArgs() {
        return args;
    }

    /**
     * Sets the number of arguments.
     *
     * @param args the args
     */
    public void setArgs(int args) {
        this.args = args;
    }

    /**
     * Sets command type.
     *
     * @param command the command type
     */
    public void setCommand(CommandType command) {
        this.command = command;
    }

    /**
     * Gets command type.
     *
     * @return the command type
     */
    public CommandType getCommand() {
        return command;
    }

    /**
     * Sets the first argument.
     *
     * @param arg1 the first argument (Item or Agent)
     */
    public void setArg1(Object arg1) {
        this.arg1 = arg1;
    }

    /**
     * Gets the first argument.
     *
     * @return the first argument
     */
    public Object getArg1() {
        return arg1;
    }

    /**
     * Sets second argument.
     *
     * @param arg2 the second argument (Item or Agent)
     */
    public void setArg2(Object arg2) {
        this.arg2 = arg2;
    }

    /**
     * Gets second argument.
     *
     * @return the second argument
     */
    public Object getArg2() {
        return arg2;
    }

    /**
     * Gets the first argument as Item if it is an Item.
     *
     * @return the first item or null if not an Item
     */
    public Item getItem1() {
        return arg1 instanceof Item ? (Item) arg1 : null;
    }

    /**
     * Gets the second argument as Item if it is an Item.
     *
     * @return the second item or null if not an Item
     */
    public Item getItem2() {
        return arg2 instanceof Item ? (Item) arg2 : null;
    }

    /**
     * Gets the first argument as Agent if it is a Agent.
     *
     * @return the first Agent or null if not a Agent
     */
    public Agent getAgent1() {
        return arg1 instanceof Agent ? (Agent) arg1 : null;
    }

    /**
     * Gets the second argument as Agent if it is a Agent.
     *
     * @return the second Agent or null if not a Agent
     */
    public Agent getAgent2() {
        return arg2 instanceof Agent ? (Agent) arg2 : null;
    }

    /**
     * Override of the equals method.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParserOutput)) return false;
        ParserOutput that = (ParserOutput) o;
        return command == that.command &&
                (arg1 != null ? arg1.equals(that.arg1) : that.arg1 == null) &&
                (arg2 != null ? arg2.equals(that.arg2) : that.arg2 == null);
    }

    /**
     * Override of the hashcode method.
     *
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        int result = command != null ? command.hashCode() : 0;
        result = 31 * result + (arg1 != null ? arg1.hashCode() : 0);
        result = 31 * result + (arg2 != null ? arg2.hashCode() : 0);
        return result;
    }
}
