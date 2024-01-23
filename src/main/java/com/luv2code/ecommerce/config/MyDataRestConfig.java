package com.luv2code.ecommerce.config;


import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private EntityManager entityManager ;
    @Autowired
    public  MyDataRestConfig(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config , CorsRegistry cors){
        HttpMethod[] theUnsupportedMethods ={HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metadata,httpMethods)-> httpMethods.disable(theUnsupportedMethods))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedMethods));
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metadata,httpMethods)-> httpMethods.disable(theUnsupportedMethods))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedMethods));

        //call an internal method
        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        List<Class> entityClasses = new ArrayList<>();

        for (EntityType entityType : entities) {
            entityClasses.add(entityType.getJavaType());
        }

        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }

}
