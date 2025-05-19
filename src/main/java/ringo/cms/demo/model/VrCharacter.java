package ringo.cms.demo.model;

import java.util.Date;
import java.util.UUID;

/**
 * VR Character model representing a character within a VR scene.
 */
public class VrCharacter {
    private UUID id;
    private UUID sceneId;
    private String name;
    private Integer version;
    private Date lastUpdate;

    public VrCharacter() {
    }

    public VrCharacter(UUID id, UUID sceneId, String name, Integer version, Date lastUpdate) {
        this.id = id;
        this.sceneId = sceneId;
        this.name = name;
        this.version = version;
        this.lastUpdate = lastUpdate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSceneId() {
        return sceneId;
    }

    public void setSceneId(UUID sceneId) {
        this.sceneId = sceneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
