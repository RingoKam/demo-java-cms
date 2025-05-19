package ringo.cms.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ringo.cms.demo.model.VrCharacter;
import ringo.cms.demo.repository.VrCharacterRepository;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/characters")
public class VrCharacterController {
    
    @Autowired
    private VrCharacterRepository characterRepository;
    
    @GetMapping
    public ResponseEntity<Collection<VrCharacter>> getAllCharacters() {
        return ResponseEntity.ok(characterRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VrCharacter> getCharacterById(@PathVariable UUID id) {
        VrCharacter character = characterRepository.findById(id);
        if (character != null) {
            return ResponseEntity.ok(character);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/scene/{sceneId}")
    public ResponseEntity<Collection<VrCharacter>> getCharactersBySceneId(@PathVariable UUID sceneId) {
        Collection<VrCharacter> characters = characterRepository.findBySceneId(sceneId);
        return ResponseEntity.ok(characters);
    }
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity<Collection<VrCharacter>> getCharactersByGameId(@PathVariable UUID gameId) {
        Collection<VrCharacter> characters = characterRepository.findByGameId(gameId);
        return ResponseEntity.ok(characters);
    }
    
    @PostMapping
    public ResponseEntity<VrCharacter> createCharacter(@RequestBody VrCharacter character) {
        if (character.getId() == null) {
            character.setId(UUID.randomUUID());
        } else if (characterRepository.exists(character.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        character.setLastUpdate(new Date());
        characterRepository.save(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(character);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VrCharacter> updateCharacter(
            @PathVariable UUID id, 
            @RequestBody VrCharacter character) {
        
        if (!characterRepository.exists(id)) {
            return ResponseEntity.notFound().build();
        }
        
        character.setId(id);
        character.setLastUpdate(new Date());
        characterRepository.save(character);
        return ResponseEntity.ok(character);
    }
}
