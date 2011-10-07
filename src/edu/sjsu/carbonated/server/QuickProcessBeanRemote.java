package edu.sjsu.carbonated.server;
import javax.ejb.Remote;


public interface QuickProcessBeanRemote {

	String remoteJSONConvert(String filename);
	
}
