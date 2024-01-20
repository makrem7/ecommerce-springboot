package com.luv2code.ecommerce.config;


import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config){
        HttpMethod[] theUnsupportedMethods ={HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metadata,httpMethods)-> httpMethods.disable(theUnsupportedMethods))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedMethods));
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metadata,httpMethods)-> httpMethods.disable(theUnsupportedMethods))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedMethods));
    }

}
