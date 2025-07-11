package org.it.uniba.fox.Type;

import org.it.uniba.fox.Entity.Item;


public class ParserOutput {
    /**
     * The command type
     */
    private CommandType command;
    /**
     * The first item
     */
    private Item item1;
    /**
     * The second item
     */
    private Item item2;
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
     * Sets the first item.
     *
     * @param item1 the first item
     */
    public void setItem1(Item item1) {
        this.item1 = item1;
    }

    /**
     * Gets the first item.
     *
     * @return the first item
     */
    public Item getItem1() {
        return item1;
    }

    /**
     * Sets second item.
     *
     * @param item2 the second item
     */
    public void setItem2(Item item2) {
        this.item2 = item2;
    }

    /**
     * Gets second item.
     *
     * @return the second item
     */
    public Item getItem2() {
        return item2;
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
                (item1 != null ? item1.equals(that.item1) : that.item1 == null) &&
                (item2 != null ? item2.equals(that.item2) : that.item2 == null);
    }

    /**
     * Override of the hashcode method.
     *
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        int result = command != null ? command.hashCode() : 0;
        result = 31 * result + (item1 != null ? item1.hashCode() : 0);
        result = 31 * result + (item2 != null ? item2.hashCode() : 0);
        return result;
    }
}
