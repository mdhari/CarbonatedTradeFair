package edu.sjsu.carbonated.server;



import javax.ejb.Remote;

@Remote
public interface HeavyProcessBeanRemote {

	String remoteJSONConvert(String filename);
	
}
