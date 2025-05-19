package ringo.cms.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ringo.cms.demo.model.VrGame;
import ringo.cms.demo.model.VrScene;
import ringo.cms.demo.repository.VrGameRepository;
import ringo.cms.demo.repository.VrSceneRepository;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
public class VrGameController {
    
    @Autowired
    private VrGameRepository gameRepository;
    
    @GetMapping
    public ResponseEntity<Collection<VrGame>> getAllGames() {
        return ResponseEntity.ok(gameRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VrGame> getGameById(@PathVariable UUID id) {
        VrGame game = gameRepository.findById(id);
        if (game != null) {
            return ResponseEntity.ok(game);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<VrGame> createGame(@RequestBody VrGame game) {
        if (game.getId() != null && gameRepository.findById(game.getId()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        // Generate ID if not provided
        if (game.getId() == null) {
            game.setId(UUID.randomUUID());
        }
        
        game.setLastUpdate(new Date());
        gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VrGame> updateGame(@PathVariable UUID id, @RequestBody VrGame game) {
        if (gameRepository.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        
        game.setId(id);
        game.setLastUpdate(new Date());
        gameRepository.save(game);
        return ResponseEntity.ok(game);
    }
}
