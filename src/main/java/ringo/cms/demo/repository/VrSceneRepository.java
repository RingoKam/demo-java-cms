package ringo.cms.demo.repository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import ringo.cms.demo.model.VrScene;

@Repository
public class VrSceneRepository {

    private final Map<UUID, Object> contentStore = new java.util.HashMap<>();

    public VrScene findById(UUID id) {
        return (VrScene) contentStore.get(id);
    }

    public Collection<VrScene> findAll() {
        return contentStore.values().stream()
                .filter(VrScene.class::isInstance)
                .map(VrScene.class::cast)
                .toList();
    }

    public void save(VrScene scene) {
        contentStore.put(scene.getId(), scene);
    }

    public Collection<VrScene> findCollection(Collection<UUID> ids) {
        // will revisit this 
        return ids.stream()
                .map(this::findById)
                .filter(scene -> scene != null)
                .toList();
    }
    
    public Collection<VrScene> findByGameId(UUID gameId) {
        return contentStore.values().stream()
                .filter(VrScene.class::isInstance)
                .map(VrScene.class::cast)
                .filter(scene -> scene.getGameId().equals(gameId))
                .toList();
    }
}
