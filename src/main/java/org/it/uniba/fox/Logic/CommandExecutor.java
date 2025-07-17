package org.it.uniba.fox.Logic;
import org.it.uniba.fox.DB_Web.DatabaseConnection;
import org.it.uniba.fox.Entity.Character;
import org.it.uniba.fox.Entity.Command;
import org.it.uniba.fox.Entity.Corridor;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.Entity.Item;
import org.it.uniba.fox.Entity.Agent;
import org.it.uniba.fox.InteractionManager.OutputDisplayManager;
import org.it.uniba.fox.Type.CommandBehavior;
import org.it.uniba.fox.Type.CommandExecutorKey;
import org.it.uniba.fox.Type.CommandType;
import org.it.uniba.fox.Type.ParserOutput;

import java.util.HashMap;
import java.util.Set;

public class CommandExecutor {

    private Game game;
    private HashMap<CommandExecutorKey, CommandBehavior> commandMap;
    private GameLogic gameLogic;

    private CommandBehavior createDirectionCommandBehavior(CommandType direction) {
        return p -> {
            Corridor corridor = game.getCorridorsMap().stream()
                    .filter(c -> c.getStartingRoom().getName().equals(game.getCurrentRoom().getName()) && c.getDirection() == direction)
                    .findFirst()
                    .orElse(null);

            if (corridor != null && !corridor.isLocked()) {
                game.setCurrentRoom(corridor.getArrivingRoom());
            } else if (corridor != null && corridor.isLocked()) {
                OutputDisplayManager.displayText("> Il corridio verso " + direction + " è bloccato!");
            } else {
                OutputDisplayManager.displayText("> Non c'è un corridoio verso " + direction + "!");
            }
        };
    }

    public CommandExecutor(Game game) {
        this.game = game;
        this.gameLogic = new GameLogic(game);
        commandMap = new HashMap<>();

        commandMap.put(new CommandExecutorKey(CommandType.NORD, 0),
                createDirectionCommandBehavior(CommandType.NORD));
        commandMap.put(new CommandExecutorKey(CommandType.EST, 0),
                createDirectionCommandBehavior(CommandType.EST));
        commandMap.put(new CommandExecutorKey(CommandType.SUD, 0),
                createDirectionCommandBehavior(CommandType.SUD));
        commandMap.put(new CommandExecutorKey(CommandType.OVEST, 0),
                createDirectionCommandBehavior(CommandType.OVEST));

        commandMap.put(new CommandExecutorKey(CommandType.OSSERVA, 0),
                p -> game.getCurrentRoom().printDescription());

        commandMap.put(new CommandExecutorKey(CommandType.CODEC, 0),
                p -> {
                    OutputDisplayManager.displayText("> Comandi disponibili:");
                    GameManager gameManager = new GameManager();
                    Set<Command> commands = gameManager.getAllCommands();
                    commands.forEach(c -> OutputDisplayManager.displayText(">  - " + c.getName()));
                    OutputDisplayManager.displayText("> (Hint: per ulteriori informazioni clicca sul punto interrogativo in alto)");
                }
        );

        commandMap.put(new CommandExecutorKey(CommandType.INVENTARIO, 0),
                p -> game.printInventory());

        commandMap.put(new CommandExecutorKey(CommandType.PRENDI, 1),
                p -> {
                    Object arg1 = p.getArg1();
                    if (arg1 instanceof Character) {
                        OutputDisplayManager.displayText("> Non puoi prendere un personaggio!");
                        return;
                    }
                    if (p.getItem1() == null) {
                        OutputDisplayManager.displayText("> Devi specificare cosa vuoi prendere!");
                        return;
                    }
                    if (game.getInventory().contains(p.getItem1())) {
                        OutputDisplayManager.displayText("> Hai già " + p.getItem1().getName() + " nell'inventario!");
                    } else if (game.getCurrentRoom().getItems().contains(p.getItem1())) {
                        if (p.getItem1().isPickable()) {
                            game.addInventory(p.getItem1());
                            game.getCurrentRoom().removeItem(p.getItem1().getName());
                            OutputDisplayManager.displayText("> Hai raccolto: " + p.getItem1().getName() + "!");
                        } else {
                            OutputDisplayManager.displayText("> Non puoi raccogliere " + p.getItem1().getName() + "!");
                        }
                    } else {
                        OutputDisplayManager.displayText("> Non c'è " + p.getItem1().getName() + " nella stanza!");
                    }
                }
        );

        commandMap.put(new CommandExecutorKey(CommandType.PARLA, 1),
                p -> {
                    Object character = p.getArg1();

                    if (character == null) {
                        OutputDisplayManager.displayText("> Devi specificare con chi vuoi parlare!");
                        return;
                    }

                    if (!(character instanceof Agent)) {
                        OutputDisplayManager.displayText("> Non puoi parlare con " + character.getClass().getSimpleName() + "!");
                        return;
                    }

                    Agent agent = (Agent) character;
                    boolean isInRoom = game.getCurrentRoom().getAgents().stream()
                            .anyMatch(a -> a.getName().equalsIgnoreCase(agent.getName()));

                    if (!isInRoom) {
                        OutputDisplayManager.displayText("> " + agent.getName() + " non è nella stanza!");
                        return;
                    }

                    if (!agent.isTalkable()) {
                        OutputDisplayManager.displayText("> " + agent.getName() + " non può parlare!");
                        return;
                    }

                    gameLogic.talkToPersonage(agent);
                }
        );

        commandMap.put(new CommandExecutorKey(CommandType.OSSERVA, 1),
                p -> {
                    if (game.getCurrentRoom().getItems().contains(p.getItem1())) {
                        DatabaseConnection.printFromDB("Osserva", game.getCurrentRoom().getName(), String.valueOf(game.getCurrentRoom().getFree()), "0", p.getItem1().getName());
                    } else if (game.getInventory().contains(p.getItem1())) {
                        OutputDisplayManager.displayText("> La tua borsa non è trasparente!");
                    } else {
                        OutputDisplayManager.displayText("> " + p.getItem1().getName() + " non è nella stanza!");
                    }
                });

        commandMap.put(new CommandExecutorKey(CommandType.USA, 1),
                p -> {
                    if (game.getInventory().contains(p.getItem1())) {
                        boolean used = gameLogic.executeUseSingleItem(p.getItem1());
                        if (!used) {
                            OutputDisplayManager.displayText("> Non succede nulla usando " + p.getItem1().getName() + ".");
                            return;
                        }
                        if (!p.getItem1().isReusable()) {
                            game.getInventory().remove(p.getItem1());
                        } else {
                            OutputDisplayManager.displayText("> Hai usato: " + p.getItem1().getName() + "!");
                        }
                        DatabaseConnection.printFromDB("Usa", game.getCurrentRoom().getName(), String.valueOf(game.getCurrentRoom().getFree()), "0", p.getItem1().getName());
                    } else {
                        OutputDisplayManager.displayText("> Non puoi usare qualcosa che non possiedi!");
                    }
                }
        );

        commandMap.put(new CommandExecutorKey(CommandType.DAI, 2),
                p -> {
                    if (!game.getInventory().contains(p.getItem1())) {
                        OutputDisplayManager.displayText("> Non puoi dare qualcosa che non possiedi!");
                        return;
                    }
                    if (p.getCharacter2() == null || !(p.getCharacter2() instanceof Agent)) {
                        OutputDisplayManager.displayText("> Devi specificare a chi vuoi dare l'oggetto!");
                        return;
                    }
                    Agent destinatario = (Agent) p.getCharacter2();
                    boolean isInRoom = game.getCurrentRoom().getAgents().stream()
                            .anyMatch(a -> a.getName().equalsIgnoreCase(destinatario.getName()));
                    if (!isInRoom) {
                        OutputDisplayManager.displayText("> " + destinatario.getName() + " non è qui con noi!");
                        return;
                    }
                    if (gameLogic.executeGiveCombination((Item) p.getItem1(), destinatario)) {
                        DatabaseConnection.printFromDB("Dai", game.getCurrentRoom().getName(), String.valueOf(game.getCurrentRoom().getFree()), "0", p.getItem1().getName().replaceAll("[^a-zA-Z0-9 ]", ""));
                    } else {
                        OutputDisplayManager.displayText("> Non puoi dare " + p.getItem1().getName() + " a " + destinatario.getName() + "!");
                    }
                }
        );
    }

    public void execute(ParserOutput p) {
        // Consideriamo sia Item che Character nel calcolo degli argomenti
        boolean hasArg1 = (p.getItem1() != null || p.getCharacter1() != null);
        boolean hasArg2 = (p.getItem2() != null || p.getCharacter2() != null);
        int args = hasArg1 ? (hasArg2 ? 2 : 1) : 0;

        CommandExecutorKey key = new CommandExecutorKey(p.getCommand(), args);
        CommandBehavior behavior = commandMap.get(key);

        if (behavior != null) {
            behavior.execute(p);
        } else {
            OutputDisplayManager.displayText("> Non penso tu possa agire in questa maniera!");
        }
    }
}