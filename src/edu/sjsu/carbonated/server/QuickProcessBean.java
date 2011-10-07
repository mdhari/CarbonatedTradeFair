package edu.sjsu.carbonated.server;

import javax.ejb.Stateless;

/**
 * @author Michael Hari
 * Session Bean implementation class QuickProcessBean
 */
@Stateless
public class QuickProcessBean implements QuickProcessBeanRemote {

    /**
     * Default constructor. 
     */
    public QuickProcessBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public String remoteJSONConvert(String filename) {
		// TODO Auto-generated method stub
		return "testing ant build";
	}
    
    

}
