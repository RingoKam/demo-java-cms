package ringo.cms.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ringo.cms.demo.model.VrScene;
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
@RequestMapping("/api/scenes")
@Tag(name = "VR Scene", description = "VR Scene management APIs")
public class VrSceneController {
    
    @Autowired
    private VrSceneRepository sceneRepository;
    
    @Operation(summary = "Get all scenes", description = "Retrieve a list of all VR scenes")
    @ApiResponse(responseCode = "200", description = "Scenes found", 
                content = @Content(schema = @Schema(implementation = VrScene.class)))
    @GetMapping("/")
    public ResponseEntity<Collection<VrScene>> getAllScenes() {
        return ResponseEntity.ok(sceneRepository.findAll());
    }
    
    @Operation(summary = "Get scene by ID", description = "Retrieve a VR scene by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Scene found", 
                    content = @Content(schema = @Schema(implementation = VrScene.class))),
        @ApiResponse(responseCode = "404", description = "Scene not found", 
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VrScene> getSceneById(@Parameter(description = "Scene ID") @PathVariable UUID id) {
        VrScene scene = sceneRepository.findById(id);
        if (scene != null) {
            return ResponseEntity.ok(scene);
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Get scenes by game", description = "Retrieve all scenes in a specific game")
    @ApiResponse(responseCode = "200", description = "Scenes found", 
                content = @Content(schema = @Schema(implementation = VrScene.class)))
    @GetMapping("/game/{gameId}")
    public ResponseEntity<Collection<VrScene>> getScenesByGameId(
            @Parameter(description = "Game ID") @PathVariable UUID gameId) {
        return ResponseEntity.ok(sceneRepository.findByGameId(gameId));
    }
    
    @Operation(summary = "Create a scene", description = "Add a new VR scene")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Scene created successfully", 
                    content = @Content(schema = @Schema(implementation = VrScene.class))),
        @ApiResponse(responseCode = "409", description = "Scene ID already exists", 
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<VrScene> createScene(@Parameter(description = "Scene to create") @RequestBody VrScene scene) {
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
    
    @Operation(summary = "Update a scene", description = "Update an existing VR scene")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Scene updated successfully", 
                    content = @Content(schema = @Schema(implementation = VrScene.class))),
        @ApiResponse(responseCode = "404", description = "Scene not found", 
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VrScene> updateScene(
            @Parameter(description = "Scene ID") @PathVariable UUID id, 
            @Parameter(description = "Updated scene") @RequestBody VrScene scene) {
        if (sceneRepository.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        
        scene.setId(id);
        scene.setLastUpdate(new Date());
        sceneRepository.save(scene);
        return ResponseEntity.ok(scene);
    }
}
