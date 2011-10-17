package edu.sjsu.carbonated.server;

import java.util.UUID;

import javax.ejb.Singleton;

@Singleton
public class UniqueIDGenerator {
	
    public String getUniqueID() {
        return UUID.randomUUID().toString();
    }

}
