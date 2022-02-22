package com.pueblo.software.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.pueblo.software.interfaces.EntityInterface;
 
public abstract class AbstractDao<PK extends Serializable, T extends EntityInterface> {
     
	
	@PersistenceContext
	EntityManager entityManager;
	
    private final Class<T> persistentClass;
     
    @SuppressWarnings("unchecked")
    public AbstractDao(){
        this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
     
    //@Autowired
    private SessionFactory sessionFactory;
 
    protected Session getSession(){
    	if (sessionFactory ==null) {
    		Session session = entityManager.unwrap(Session.class);
    		sessionFactory = session.getSessionFactory();
		}
        return sessionFactory.getCurrentSession();
    }
 
    public T findById(PK key) {
        return (T) getSession().get(persistentClass, key);
    }
 
    public void persist(T entity) {
        if (entity.getId()!=null) {
            update(entity);
        }else {
            getSession().persist(entity);
        }
    }
 
    public void update(T entity) {
        getSession().update(entity);
    }
 
    public void delete(T entity) {
        getSession().delete(entity);
    }
     
/*    protected Criteria createEntityCriteria(){
        return getSession().createCriteria(persistentClass);
    }
 */
    protected EntityManager getEm() {
    	return entityManager;
    }
}