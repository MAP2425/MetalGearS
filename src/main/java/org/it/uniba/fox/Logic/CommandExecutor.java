package org.it.uniba.fox.Logic;
import org.it.uniba.fox.Entity.Command;
import org.it.uniba.fox.Entity.Corridor;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.Entity.Item;
import org.it.uniba.fox.Entity.Character;
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
                        if ((p.getItem1() != null) && ((Item) p.getItem1()).getPicked()) {
                            game.addInventory((Item) p.getItem1());
                            game.getCurrentRoom().removeItem(p.getItem1().getName());
                            //gameLogic.executeTake((Item) p.getItem1());
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
                    if (game.getCurrentRoom().getItems().contains(p.getItem1())) {
                        if (p.getItem1() instanceof Character) {
                            gameLogic.talkToPersonage((Character) p.getItem1());
                        } else {
                            OutputDisplayManager.displayText("> Non puoi parlare con " + p.getItem1().getName() + "!");
                        }
                    } else {
                        OutputDisplayManager.displayText("> " + p.getItem1().getName() + " non è nella stanza!");
                    }
                });

        commandMap.put(new CommandExecutorKey(CommandType.USA, 1),
                p -> {
                    if (game.getInventory().contains(p.getItem1()) || game.getCurrentRoom().getItems().contains(p.getItem1())) {
                        String statusBeforeAction = String.valueOf(game.getCurrentRoom().getFree());
                        if (gameLogic.executeUseSingleItem((Item) p.getItem1())) {
                            //DatabaseConnection.printFromDB("Usa", game.getCurrentRoom().getName(), statusBeforeAction, "0", p.getItem1().getName(), "0");
                        } else {
                            OutputDisplayManager.displayText("> Non puoi usare " + p.getItem1().getName() + " da solo!");
                        }
                    } else {
                        OutputDisplayManager.displayText("> " + p.getItem1().getName() + " non è nell'inventario!");
                    }
                });

        commandMap.put(new CommandExecutorKey(CommandType.DAI, 2),
                p -> {
                    if (game.getInventory().contains(p.getItem1())) {
                        if (game.getCurrentRoom().getItems().contains(p.getItem2())) {
                            String statusBeforeAction = String.valueOf(game.getCurrentRoom().getFree());
                            if (p.getItem2() instanceof Character) {
                                if (gameLogic.executeGiveCombination((Item) p.getItem1(), (Character) p.getItem2())) {
                                    //DatabaseConnection.printFromDB("Dai", game.getCurrentRoom().getName(), statusBeforeAction, p.getItem2().getName(), p.getItem1().getName(), "0");
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
        int args = (p.getItem1() != null) ? ((p.getItem2() != null) ? 2 : 1) : 0;

        CommandExecutorKey key = new CommandExecutorKey(p.getCommand(), args);
        CommandBehavior behavior = commandMap.get(key);
        if (behavior != null) {
            behavior.execute(p);
        } else {
            OutputDisplayManager.displayText("> Non penso tu possa agire in questa maniera!");
        }
    }
}