package edu.sjsu.carbonated.server;

import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * @author Michael Hari
 * Session Bean implementation class QuickProcessBean
 */
@Stateless
@Remote(QuickProcessBeanRemote.class)
public class QuickProcessBean implements QuickProcessBeanRemote {

    /**
     * Default constructor. 
     */
    public QuickProcessBean() {
        // TODO Auto-generated constructor stub
    }

	public String remoteJSONConvert(String filename) {
		// TODO Auto-generated method stub
		return "testing ant build";
	}
    
    

}
