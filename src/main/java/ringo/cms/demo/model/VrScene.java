package ringo.cms.demo.model;

import java.util.Date;
import java.util.UUID;

/**
 * VR Scene model representing a scene within a VR game.
 */
public class VrScene {
    private UUID id;
    private UUID gameId;
    private String name;
    private Integer sequence;
    private Integer version;
    private Date lastUpdate;

    public VrScene() {
    }

    public VrScene(UUID id, UUID gameId, String name, Integer sequence, Integer version, Date lastUpdate) {
        this.id = id;
        this.gameId = gameId;
        this.name = name;
        this.sequence = sequence;
        this.version = version;
        this.lastUpdate = lastUpdate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
