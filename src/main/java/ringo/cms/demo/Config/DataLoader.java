package ringo.cms.demo.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ringo.cms.demo.model.VrCharacter;
import ringo.cms.demo.model.VrGame;
import ringo.cms.demo.model.VrScene;
import ringo.cms.demo.repository.VrGameRepository;
import ringo.cms.demo.repository.VrSceneRepository;
import ringo.cms.demo.repository.VrCharacterRepository;

import java.util.Date;
import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {

    private VrGameRepository gameRepository;
    private VrSceneRepository sceneRepository;
    private VrCharacterRepository characterRepository;

    public DataLoader(VrGameRepository gameRepository, VrSceneRepository sceneRepository, VrCharacterRepository characterRepository) {
        this.gameRepository = gameRepository;
        this.sceneRepository = sceneRepository;
        this.characterRepository = characterRepository;
    }

    @Override
    public void run(String... args) {
        // Create Game 1
        VrGame game1 = new VrGame();
        game1.setId(UUID.randomUUID()); // Generate new UUID
        game1.setName("Grade 1 Chemistry");
        game1.setDescription("Chemistry basics for Grade 1 students");
        game1.setVersion(1);
        game1.setLastUpdate(new Date());
        gameRepository.save(game1);

        VrScene scene1 = new VrScene();
        scene1.setId(UUID.randomUUID()); // Generate new UUID
        scene1.setGameId(game1.getId());
        scene1.setName("Space Station");
        scene1.setSequence(1);
        scene1.setVersion(1);
        scene1.setLastUpdate(new Date());
        sceneRepository.save(scene1);

        VrScene scene2 = new VrScene();
        scene2.setId(UUID.randomUUID()); // Generate new UUID
        scene2.setGameId(game1.getId());
        scene2.setName("Alien Planet");
        scene2.setSequence(2);
        scene2.setVersion(1);
        scene2.setLastUpdate(new Date());
        sceneRepository.save(scene2);

        // Create Characters for Scene 1
        VrCharacter char1 = new VrCharacter();
        char1.setId(UUID.randomUUID()); // Generate new UUID
        char1.setSceneId(scene1.getId());
        char1.setName("Jack Smith");
        char1.setVersion(1);
        char1.setLastUpdate(new Date());
        characterRepository.save(char1);

        VrCharacter char2 = new VrCharacter();
        char2.setId(UUID.randomUUID()); // Generate new UUID
        char2.setSceneId(scene1.getId());
        char2.setName("Holly Johnson");
        char2.setVersion(1);
        char2.setLastUpdate(new Date());
        characterRepository.save(char2);

        // Create Characters for Scene 2
        VrCharacter char3 = new VrCharacter();
        char3.setId(UUID.randomUUID()); // Generate new UUID
        char3.setSceneId(scene2.getId());
        char3.setName("Allen Smith");
        char3.setVersion(1);
        char3.setLastUpdate(new Date());
        characterRepository.save(char3);

        // Create Game 2
        VrGame game2 = new VrGame();
        game2.setId(UUID.randomUUID()); // Generate new UUID
        game2.setName("Grade 3 History");
        game2.setDescription("An adventure in a medieval world, fit for grade 3 students");
        game2.setVersion(1);
        game2.setLastUpdate(new Date());
        gameRepository.save(game2);

        // Create Scenes for Game 2
        VrScene scene3 = new VrScene();
        scene3.setId(UUID.randomUUID()); // Generate new UUID
        scene3.setGameId(game2.getId());
        scene3.setName("Castle Prince");
        scene3.setSequence(1);
        scene3.setVersion(1);
        scene3.setLastUpdate(new Date());
        sceneRepository.save(scene3);

        VrScene scene4 = new VrScene();
        scene4.setId(UUID.randomUUID()); // Generate new UUID
        scene4.setGameId(game2.getId());
        scene4.setName("Leo Li");
        scene4.setSequence(2);
        scene4.setVersion(1);
        scene4.setLastUpdate(new Date());
        sceneRepository.save(scene4);

        // Create Characters for Scene 3
        VrCharacter char4 = new VrCharacter();
        char4.setId(UUID.randomUUID()); // Generate new UUID
        char4.setSceneId(scene3.getId());
        char4.setName("Peter Arthur");
        char4.setVersion(1);
        char4.setLastUpdate(new Date());
        characterRepository.save(char4);

        VrCharacter char5 = new VrCharacter();
        char5.setId(UUID.randomUUID()); // Generate new UUID
        char5.setSceneId(scene3.getId());
        char5.setName("Gege Lancelot");
        char5.setVersion(1);
        char5.setLastUpdate(new Date());
        characterRepository.save(char5);

        // Create Characters for Scene 4
        VrCharacter char6 = new VrCharacter();
        char6.setId(UUID.randomUUID()); // Generate new UUID
        char6.setSceneId(scene4.getId());
        char6.setName("Holly Ferguson");
        char6.setVersion(1);
        char6.setLastUpdate(new Date());
        characterRepository.save(char6);

        System.out.println("Sample data loaded successfully!");
    }
}
