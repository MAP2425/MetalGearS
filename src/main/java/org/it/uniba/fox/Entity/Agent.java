package org.it.uniba.fox.Entity;

import java.util.List;
import java.util.Objects;

/**
 * Class that represents an agent in the game.
 */
public abstract class Agent {

    private String name;
    private List<String> aliases;
    private String description;

    private boolean isReusable;
    private boolean isPickable;
    private boolean isTalkable;

    public boolean isTalkable() {
        return isTalkable;
    }

    public void setTalkable(boolean talkable) {
        this.isTalkable = talkable;
    }

    public boolean isPickable() {
        return isPickable;
    }

    public void setPickable(boolean pickable) {
        this.isPickable = pickable;
    }

    public boolean isReusable() {
        return isReusable;
    }

    public void setReusable(boolean reusable) {
        this.isReusable = reusable;
    }

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agent)) return false;
        Agent agent = (Agent) o;
        return Objects.equals(name, agent.name) &&
                Objects.equals(aliases, agent.aliases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, aliases);
    }

    public abstract void getDescription(Room room);
}
