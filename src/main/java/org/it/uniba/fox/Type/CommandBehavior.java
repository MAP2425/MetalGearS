package org.it.uniba.fox.Type;

/**
 * The interface of the command behavior.
 */
public class CommandBehavior {
    /**
     * The method that executes the command.
     *
     * @param parsedText the parsed text
     */
    void execute(ParserOutput parsedText);
}
