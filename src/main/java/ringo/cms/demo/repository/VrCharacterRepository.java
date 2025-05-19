package ringo.cms.demo.repository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ringo.cms.demo.model.VrCharacter;
import ringo.cms.demo.model.VrScene;

@Repository
public class VrCharacterRepository {

    private final Map<UUID, Object> contentStore = new java.util.HashMap<>();
    
    @Autowired
    private VrSceneRepository sceneRepository;

    public VrCharacter findById(UUID id) {
        return (VrCharacter) contentStore.get(id);
    }

    public Collection<VrCharacter> findAll() {
        return contentStore.values().stream()
                .filter(VrCharacter.class::isInstance)
                .map(VrCharacter.class::cast)
                .toList();
    }

    public void save(VrCharacter character) {
        contentStore.put(character.getId(), character);
    }

    public Collection<VrCharacter> findCollection(Collection<UUID> ids) {
        return ids.stream()
                .map(id -> findById(id))
                .filter(character -> character != null)
                .toList();
    }
    
    public Collection<VrCharacter> findBySceneId(UUID sceneId) {
        return contentStore.values().stream()
                .filter(VrCharacter.class::isInstance)
                .map(VrCharacter.class::cast)
                .filter(character -> character.getSceneId().equals(sceneId))
                .toList();
    }
    
    public Collection<VrCharacter> findByGameId(UUID gameId) {
        // Get all scenes for this game
        Collection<VrScene> gameScenes = sceneRepository.findByGameId(gameId);
        
        // Get the scene IDs
        Collection<UUID> sceneIds = gameScenes.stream()
                .map(VrScene::getId)
                .collect(Collectors.toList());
        
        // Find characters in all these scenes
        return contentStore.values().stream()
                .filter(VrCharacter.class::isInstance)
                .map(VrCharacter.class::cast)
                .filter(character -> sceneIds.contains(character.getSceneId()))
                .toList();
    }
    
    public boolean exists(UUID id) {
        return contentStore.containsKey(id);
    }
}
