package ringo.cms.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ringo.cms.demo.model.VrCharacter;

class VrCharacterRepositoryTest {

    private VrCharacterRepository characterRepository;
    private UUID testId;
    private UUID testSceneId;
    private VrCharacter testCharacter;

    @BeforeEach
    void setUp() {
        characterRepository = new VrCharacterRepository();
        testId = UUID.randomUUID();
        testSceneId = UUID.randomUUID();
        testCharacter = new VrCharacter(testId, testSceneId, "Test Character", 1, new Date());
    }

    @Test
    void testSaveAndFindById() {
        characterRepository.save(testCharacter);
        VrCharacter retrieved = characterRepository.findById(testId);
        
        assertNotNull(retrieved);
        assertEquals(testId, retrieved.getId());
        assertEquals(testSceneId, retrieved.getSceneId());
        assertEquals("Test Character", retrieved.getName());
    }

    @Test
    void testFindByIdNotFound() {
        VrCharacter retrieved = characterRepository.findById(UUID.randomUUID());
        assertNull(retrieved);
    }

    @Test
    void testFindAll() {
        UUID id2 = UUID.randomUUID();
        VrCharacter character2 = new VrCharacter(id2, testSceneId, "Second Character", 2, new Date());
        
        characterRepository.save(testCharacter);
        characterRepository.save(character2);
        
        Collection<VrCharacter> characters = characterRepository.findAll();
        
        assertEquals(2, characters.size());
        assertTrue(characters.stream().anyMatch(c -> c.getId().equals(testId)));
        assertTrue(characters.stream().anyMatch(c -> c.getId().equals(id2)));
    }

    @Test
    void testFindBySceneId() {
        UUID id2 = UUID.randomUUID();
        UUID anotherSceneId = UUID.randomUUID();
        VrCharacter character2 = new VrCharacter(id2, testSceneId, "Same Scene Character", 2, new Date());
        VrCharacter character3 = new VrCharacter(UUID.randomUUID(), anotherSceneId, "Different Scene", 1, new Date());
        
        characterRepository.save(testCharacter);
        characterRepository.save(character2);
        characterRepository.save(character3);
        
        Collection<VrCharacter> characters = characterRepository.findBySceneId(testSceneId);
        
        assertEquals(2, characters.size());
        assertTrue(characters.stream().allMatch(c -> c.getSceneId().equals(testSceneId)));
        assertTrue(characters.stream().anyMatch(c -> c.getId().equals(testId)));
        assertTrue(characters.stream().anyMatch(c -> c.getId().equals(id2)));
    }
}
