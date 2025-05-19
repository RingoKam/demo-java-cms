package ringo.cms.demo.repository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import ringo.cms.demo.model.VrGame;

@Repository
public class VrGameRepository {

    private final Map<UUID, Object> contentStore = new java.util.HashMap<>();

    public VrGame findById(UUID id) {
        return (VrGame) contentStore.get(id);
    }

    public Collection<VrGame> findAll() {
        return contentStore.values().stream()
                .filter(VrGame.class::isInstance)
                .map(VrGame.class::cast)
                .toList();
    }

    public void save(VrGame game) {
        contentStore.put(game.getId(), game);
    }

    public Collection<VrGame> findCollection(Collection<UUID> ids) {
        return ids.stream()
                .map(this::findById)
                .filter(game -> game != null)
                .toList();
    }
}
