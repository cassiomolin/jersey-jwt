package com.cassiomolin.example.common.persistence;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * CDI producer for the JPA {@link EntityManager}.
 *
 * @author cassiomolin
 */
@ApplicationScoped
public class EntityManagerProducer {

    private EntityManagerFactory factory;

    @PostConstruct
    public void init() {
        factory = Persistence.createEntityManagerFactory("default");
    }

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return factory.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @PreDestroy
    public void destroy() {
        if (factory.isOpen()) {
            factory.close();
        }
    }

    // Hack to force the EntityManager provider (Hibernate in this case) to create
    // tables and populate the database when the application starts up
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        factory.createEntityManager().close();
    }
}