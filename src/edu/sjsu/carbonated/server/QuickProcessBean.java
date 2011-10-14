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
    }

	public String remoteJSONConvert(String filename) {
		
		CSVtoJSON csvToJson = new CSVtoJSON();
		return csvToJson.convertCSVFile(filename);

	}
   
}
