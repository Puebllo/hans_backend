/*
 * package com.pueblo.software.repository;
 * 
 * import java.io.Serializable;
 * 
 * import javax.persistence.EntityManager; import
 * javax.persistence.PersistenceContext;
 * 
 * import org.springframework.data.jpa.repository.JpaRepository;
 * 
 * import com.pueblo.software.interfaces.EntityInterface;
 * 
 * public abstract class AbstractRepository<T extends EntityInterface, S extends
 * Serializable> implements JpaRepository<T, S>{
 * 
 * @PersistenceContext EntityManager entityManager;
 * 
 * public EntityManager getEM() { return entityManager; }
 * 
 * }
 */