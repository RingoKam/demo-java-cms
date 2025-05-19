package ringo.cms.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ringo.cms.demo.model.VrCharacter;
import ringo.cms.demo.repository.VrCharacterRepository;

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
@RequestMapping("/api/characters")
@Tag(name = "VR Character", description = "VR Character management APIs")
public class VrCharacterController {
    
    @Autowired
    private VrCharacterRepository characterRepository;
    
    @Operation(summary = "Get all characters", description = "Retrieve a list of all VR characters")
    @ApiResponse(responseCode = "200", description = "Characters found", 
                content = @Content(schema = @Schema(implementation = VrCharacter.class)))
    @GetMapping
    public ResponseEntity<Collection<VrCharacter>> getAllCharacters() {
        return ResponseEntity.ok(characterRepository.findAll());
    }
    
    @Operation(summary = "Get character by ID", description = "Retrieve a VR character by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Character found", 
                    content = @Content(schema = @Schema(implementation = VrCharacter.class))),
        @ApiResponse(responseCode = "404", description = "Character not found", 
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VrCharacter> getCharacterById(@Parameter(description = "Character ID") @PathVariable UUID id) {
        VrCharacter character = characterRepository.findById(id);
        if (character != null) {
            return ResponseEntity.ok(character);
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Get characters by scene", description = "Retrieve all characters in a specific scene")
    @ApiResponse(responseCode = "200", description = "Characters found", 
                content = @Content(schema = @Schema(implementation = VrCharacter.class)))
    @GetMapping("/scene/{sceneId}")
    public ResponseEntity<Collection<VrCharacter>> getCharactersBySceneId(
            @Parameter(description = "Scene ID") @PathVariable UUID sceneId) {
        Collection<VrCharacter> characters = characterRepository.findBySceneId(sceneId);
        return ResponseEntity.ok(characters);
    }
    
    @Operation(summary = "Get characters by game", description = "Retrieve all characters in a specific game")
    @ApiResponse(responseCode = "200", description = "Characters found", 
                content = @Content(schema = @Schema(implementation = VrCharacter.class)))
    @GetMapping("/game/{gameId}")
    public ResponseEntity<Collection<VrCharacter>> getCharactersByGameId(
            @Parameter(description = "Game ID") @PathVariable UUID gameId) {
        Collection<VrCharacter> characters = characterRepository.findByGameId(gameId);
        return ResponseEntity.ok(characters);
    }
    
    @Operation(summary = "Create a character", description = "Add a new VR character")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Character created successfully", 
                    content = @Content(schema = @Schema(implementation = VrCharacter.class))),
        @ApiResponse(responseCode = "409", description = "Character ID already exists", 
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<VrCharacter> createCharacter(
            @Parameter(description = "Character to create") @RequestBody VrCharacter character) {
        if (character.getId() == null) {
            character.setId(UUID.randomUUID());
        } else if (characterRepository.exists(character.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        character.setLastUpdate(new Date());
        characterRepository.save(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(character);
    }
    
    @Operation(summary = "Update a character", description = "Update an existing VR character")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Character updated successfully", 
                    content = @Content(schema = @Schema(implementation = VrCharacter.class))),
        @ApiResponse(responseCode = "404", description = "Character not found", 
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VrCharacter> updateCharacter(
            @Parameter(description = "Character ID") @PathVariable UUID id, 
            @Parameter(description = "Updated character") @RequestBody VrCharacter character) {
        
        if (!characterRepository.exists(id)) {
            return ResponseEntity.notFound().build();
        }
        
        character.setId(id);
        character.setLastUpdate(new Date());
        characterRepository.save(character);
        return ResponseEntity.ok(character);
    }
}
