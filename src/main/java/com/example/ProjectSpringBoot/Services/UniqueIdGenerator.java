package com.example.ProjectSpringBoot.Services;

import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

public class UniqueIdGenerator extends IdentityGenerator {
    
     @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // Your custom ID generation logic here.
        // You can use an external service to generate unique IDs.
        // For this example, let's assume you have a custom service called UniqueIdService.
        Long uniqueId = UniqueIdService.generateUniqueId();
        return uniqueId != null ? uniqueId : super.generate(session, object);
    }
    
}
