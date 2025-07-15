package org.it.uniba.fox.Logic;
import org.it.uniba.fox.DB_Web.DatabaseConnection;
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
                    if (game.getInventory().contains(p.getItem1())) {
                        OutputDisplayManager.displayText("> Hai già " + p.getItem1().getName() + " nell'inventario!");
                    } else if (game.getCurrentRoom().getItems().contains(p.getItem1())) {
                        if (p.getItem1() != null && p.getItem1().isPickable()) {
                            game.addInventory(p.getItem1());
                            game.getCurrentRoom().removeItem(p.getItem1().getName());
                            OutputDisplayManager.displayText("> Hai raccolto: " + p.getItem1().getName() + "!");
                        } else {
                            OutputDisplayManager.displayText("> Non puoi raccogliere " + p.getItem1().getName() + "!");
                        }
                    } else {
                        OutputDisplayManager.displayText("> Non c'è " + p.getItem1().getName() + " nella stanza!");
                    }
                });


        commandMap.put(new CommandExecutorKey(CommandType.PARLA, 1),
                p -> {
                    // Ottieni il personaggio
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
                    System.out.println("DEBUG - Agente trovato: " + agent.getName());
                    boolean isInRoom = game.getCurrentRoom().getAgents().stream()
                            .anyMatch(a -> a.getName().equalsIgnoreCase(agent.getName()));
                    System.out.println("DEBUG - Agente nella stanza: " + isInRoom);

                    if (!isInRoom) {
                        OutputDisplayManager.displayText("> " + agent.getName() + " non è nella stanza!");
                        return;
                    }

                    // Verifica se l'agente può parlare
                    System.out.println("DEBUG - Agente può parlare: " + agent.isTalkable());
                    if (!agent.isTalkable()) {
                        OutputDisplayManager.displayText("> " + agent.getName() + " non può parlare!");
                        return;
                    }

                    // Esegui il dialogo
                    System.out.println("DEBUG - Inizio dialogo con: " + agent.getName());
                    gameLogic.talkToPersonage(agent);
                }
        );

        commandMap.put(new CommandExecutorKey(CommandType.OSSERVA, 1),
                p -> {
                    if (game.getCurrentRoom().getItems().contains(p.getItem1())) {
                        DatabaseConnection.printFromDB("Osserva", game.getCurrentRoom().getName(), String.valueOf(game.getCurrentRoom().getFree()),"0",p.getItem1().getName());
                    } else if (game.getInventory().contains(p.getItem1())) {
                        OutputDisplayManager.displayText("> La tua borsa non è trasparente!");
                    } else {
                        OutputDisplayManager.displayText("> " + p.getItem1().getName() + " non è nella stanza!");
                    }
                });

        commandMap.put(new CommandExecutorKey(CommandType.USA, 1),
                p -> {
                    if (game.getInventory().contains(p.getItem1()) ) {
                        gameLogic.executeUseSingleItem(p.getItem1());
                        if (p.getItem1().isReusable()) {
                            game.getInventory().remove(p.getItem1());
                        } else {
                            OutputDisplayManager.displayText("> Hai usato: " + p.getItem1().getName() + "!");
                        }
                        DatabaseConnection.printFromDB("Usa", game.getCurrentRoom().getName(), String.valueOf(game.getCurrentRoom().getFree()), "0",p.getItem1().getName());
                    } else {
                        OutputDisplayManager.displayText("> Non puoi usare qualcosa che non possiedi!");
                    }
                });

        commandMap.put(new CommandExecutorKey(CommandType.DAI, 2),
                p -> {
                    if (game.getInventory().contains(p.getItem1())) {
                        if (game.getCurrentRoom().getItems().contains(p.getItem2())) {
                            String statusBeforeAction = String.valueOf(game.getCurrentRoom().getFree());
                            if (p.getItem2() instanceof Agent) {
                                if (gameLogic.executeGiveCombination((Item) p.getItem1(), (Agent) p.getItem2())) {
                                    DatabaseConnection.printFromDB("Usa", game.getCurrentRoom().getName(), String.valueOf(game.getCurrentRoom().getFree()), p.getItem1().getName().replaceAll("[^a-zA-Z0-9 ]", ""), "0");
                                } else {
                                    OutputDisplayManager.displayText("> Non puoi dare " + p.getItem1().getName() + " a " + p.getItem2().getName() + "!");
                                }
                            } else {
                                OutputDisplayManager.displayText("> " + p.getItem2().getName() + " non è un personaggio!");
                            }
                        } else {
                            OutputDisplayManager.displayText("> Se non è invisibile, allora " + p.getItem2().getName() + " non è qui con noi!");
                        }
                    } else {
                        OutputDisplayManager.displayText("> Non puoi dare qualcosa che non possiedi!");
                    }
                });

    }

    public void execute(ParserOutput p) {
        System.out.println("DEBUG - Comando: " + p.getCommand());
        System.out.println("DEBUG - Character1: " + p.getCharacter1());

        // Consideriamo sia Item che Character nel calcolo degli argomenti
        boolean hasArg1 = (p.getItem1() != null || p.getCharacter1() != null);
        boolean hasArg2 = (p.getItem2() != null || p.getCharacter2() != null);
        int args = hasArg1 ? (hasArg2 ? 2 : 1) : 0;

        System.out.println("DEBUG - Numero argomenti: " + args);

        CommandExecutorKey key = new CommandExecutorKey(p.getCommand(), args);
        CommandBehavior behavior = commandMap.get(key);

        System.out.println("DEBUG - Behavior trovato: " + (behavior != null));

        if (behavior != null) {
            behavior.execute(p);
        } else {
            OutputDisplayManager.displayText("> Non penso tu possa agire in questa maniera!");
        }
    }
}