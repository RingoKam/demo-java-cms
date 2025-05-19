package ringo.cms.demo.repository;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;

@Repository
public class ContentRepository {

    private final Map<String, Map<UUID, Object>> contentStore;

    public ContentRepository() {
        this.contentStore = new ConcurrentHashMap<>();
    }

    /**
     * Save an object to the repository.
     * The object must have getId() and getClass().getSimpleName() methods.
     * If getId() returns null, a new UUID will be assigned and set via setId() method.
     */
    public <T> T save(T content) {
        UUID id;
        String type;
        
        try {
            Method getIdMethod = content.getClass().getMethod("getId");
            id = (UUID) getIdMethod.invoke(content);
            
            if (id == null) {
                id = UUID.randomUUID();
                Method setIdMethod = content.getClass().getMethod("setId", UUID.class);
                setIdMethod.invoke(content, id);
            }
            
            type = content.getClass().getSimpleName();
        } catch (Exception e) {
            throw new IllegalArgumentException("Object must have getId() and setId() methods", e);
        }

        contentStore.computeIfAbsent(type, _ -> new ConcurrentHashMap<>())
                .put(id, content);
        
        return content;
    }

    /**
     * Find an object by its type and id
     */
    @SuppressWarnings("unchecked")
    public <T> T findById(String type, UUID id) {
        if (contentStore.containsKey(type)) {
            return (T) contentStore.get(type).get(id);
        }
        return null;
    }

    /**
     * Find a collection of objects by their ids for a given type
     */
    @SuppressWarnings("unchecked")
    public <T> Collection<T> findByIds(String type, Collection<UUID> ids) {
        if (!contentStore.containsKey(type)) {
            return new ArrayList<>();
        }
        
        Map<UUID, Object> typeStore = contentStore.get(type);
        
        return ids.stream()
                .map(typeStore::get)
                .filter(java.util.Objects::nonNull)
                .map(obj -> (T) obj)
                .collect(Collectors.toList());
    }
    
    /**
     * Find all objects of a given type
     */
    @SuppressWarnings("unchecked")
    public <T> Collection<T> findAllByType(String type) {
        if (!contentStore.containsKey(type)) {
            return new ArrayList<>();
        }
        
        return contentStore.get(type).values().stream()
            .map(obj -> (T) obj)
            .collect(Collectors.toList());
    }

    /**
     * Find all objects in the repository
     */
    public Collection<Object> findAll() {
        List<Object> allContent = new ArrayList<>();
        for (Map<UUID, Object> typeStore : contentStore.values()) {
            allContent.addAll(typeStore.values());
        }
        return allContent;
    }
    
    /**
     * Check if an object exists in the repository
     */
    public boolean exists(String type, UUID id) {
        return contentStore.containsKey(type) && contentStore.get(type).containsKey(id);
    }
    
    /**
     * Delete an object from the repository
     */
    public void delete(String type, UUID id) {
        if (contentStore.containsKey(type)) {
            contentStore.get(type).remove(id);
        }
    }
    
    /**
     * Delete all objects of a given type
     */
    public void deleteAll(String type) {
        contentStore.remove(type);
    }
    
    /**
     * Delete all objects from the repository
     */
    public void deleteAll() {
        contentStore.clear();
    }
    
    /**
     * Get all available types in the repository
     */
    public Collection<String> getAvailableTypes() {
        return new ArrayList<>(contentStore.keySet());
    }
    
    /**
     * Save an entity by class type rather than string name
     */
    public <T> T save(T content, Class<T> type) {
        return save(content);
    }

    public <T> T findById(Class<T> type, UUID id) {
        return findById(type.getSimpleName(), id);
    }
    
    public <T> Collection<T> findAllByType(Class<T> type) {
        return findAllByType(type.getSimpleName());
    }
    
    /**
     * Find entities by ids using class type
     */
    public <T> Collection<T> findByIds(Class<T> type, Collection<UUID> ids) {
        return findByIds(type.getSimpleName(), ids);
    }
}
