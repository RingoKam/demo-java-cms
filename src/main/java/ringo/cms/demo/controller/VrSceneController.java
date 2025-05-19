package ringo.cms.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ringo.cms.demo.model.VrScene;
import ringo.cms.demo.repository.VrSceneRepository;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/scenes")
public class VrSceneController {
    
    @Autowired
    private VrSceneRepository sceneRepository;
    
    @GetMapping("/")
    public ResponseEntity<Collection<VrScene>> getAllScenes() {
        return ResponseEntity.ok(sceneRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VrScene> getSceneById(@PathVariable UUID id) {
        VrScene scene = sceneRepository.findById(id);
        if (scene != null) {
            return ResponseEntity.ok(scene);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity<Collection<VrScene>> getScenesByGameId(@PathVariable UUID gameId) {
        return ResponseEntity.ok(sceneRepository.findByGameId(gameId));
    }
    
    @PostMapping
    public ResponseEntity<VrScene> createScene(@RequestBody VrScene scene) {
        if (scene.getId() != null && sceneRepository.findById(scene.getId()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (scene.getId() == null) {
            scene.setId(UUID.randomUUID());
        }
        scene.setLastUpdate(new Date());
        sceneRepository.save(scene);
        return ResponseEntity.status(HttpStatus.CREATED).body(scene);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VrScene> updateScene(@PathVariable UUID id, @RequestBody VrScene scene) {
        if (sceneRepository.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        
        scene.setId(id);
        scene.setLastUpdate(new Date());
        sceneRepository.save(scene);
        return ResponseEntity.ok(scene);
    }
}
