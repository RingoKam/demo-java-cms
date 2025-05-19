package ringo.cms.demo.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

/**
 * VR Game model representing a virtual reality game in the system.
 */
public class VrGame {
    private UUID id;
    private String name;
    private String description;
    private Integer version;
    private Date lastUpdate;

    public VrGame() {
    }

    public VrGame(UUID id, String name, String description, Integer version, Date lastUpdate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.version = version;
        this.lastUpdate = lastUpdate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
