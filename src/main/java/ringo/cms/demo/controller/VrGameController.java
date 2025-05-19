package ringo.cms.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ringo.cms.demo.model.VrGame;
import ringo.cms.demo.model.VrScene;
import ringo.cms.demo.repository.VrGameRepository;
import ringo.cms.demo.repository.VrSceneRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
@Tag(name = "VR Game", description = "VR Game management APIs")
public class VrGameController {
    
    @Autowired
    private VrGameRepository gameRepository;
    
    @Operation(summary = "Get all games", description = "Retrieve a list of all VR games")
    @ApiResponse(responseCode = "200", description = "Games found", 
                content = @Content(schema = @Schema(implementation = VrGame.class)))
    @GetMapping
    public ResponseEntity<Collection<VrGame>> getAllGames() {
        return ResponseEntity.ok(gameRepository.findAll());
    }
    
    @Operation(summary = "Get game by ID", description = "Retrieve a VR game by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Game found", 
                    content = @Content(schema = @Schema(implementation = VrGame.class))),
        @ApiResponse(responseCode = "404", description = "Game not found", 
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VrGame> getGameById(@Parameter(description = "Game ID") @PathVariable UUID id) {
        VrGame game = gameRepository.findById(id);
        if (game != null) {
            return ResponseEntity.ok(game);
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Create a game", description = "Add a new VR game")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Game created successfully", 
                    content = @Content(schema = @Schema(implementation = VrGame.class))),
        @ApiResponse(responseCode = "409", description = "Game ID already exists", 
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<VrGame> createGame(@Parameter(description = "Game to create") @RequestBody VrGame game) {
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
    
    @Operation(summary = "Update a game", description = "Update an existing VR game")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Game updated successfully", 
                    content = @Content(schema = @Schema(implementation = VrGame.class))),
        @ApiResponse(responseCode = "404", description = "Game not found", 
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VrGame> updateGame(
            @Parameter(description = "Game ID") @PathVariable UUID id, 
            @Parameter(description = "Updated game") @RequestBody VrGame game) {
        if (gameRepository.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        
        game.setId(id);
        game.setLastUpdate(new Date());
        gameRepository.save(game);
        return ResponseEntity.ok(game);
    }
}
