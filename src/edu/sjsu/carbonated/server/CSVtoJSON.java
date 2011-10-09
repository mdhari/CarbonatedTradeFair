package edu.sjsu.carbonated.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;


/**
 * 
 * @author Michael Hari
 * @date Oct 5th, 2011
 * @version 1.0
 * 
 */

public class CSVtoJSON {

	public CSVtoJSON() {
	} // default constructor

	/**
	 * 
	 * @param filename
	 *            Requires a filename like AA.dat which currently looks in the
	 *            tmp directory on json
	 * @return JSON formatted string of {filename (without extension .dat)
	 *         [{data}]}
	 */
	public String convertCSVFile(String fileName) {

		// HTTP/1.1 200 OK
		// Cache-Control: private
		// Connection: close
		// Date: Fri, 30 Sep 2011 05:49:51 GMT
		// Content-Type: text/csv
		// Client-Date: Fri, 30 Sep 2011 05:49:53 GMT
		// Client-Peer: 67.195.146.181:80
		// Client-Response-Num: 1
		// Client-Transfer-Encoding: chunked
		// P3P: policyref="http://info.yahoo.com/w3c/p3p.xml",
		// CP="CAO DSP COR CUR ADM DEV TAI PSA PSD IVAi IVDi CONi TELo OTPi OUR DELi SAMi OTRi UNRi PUBi IND PHY ONL UNI PUR FIN COM NAV INT DEM CNT STA POL HEA PRE LOC GOV"

		int numOfLinesToSkip = 11; // the reason why we skip is above

		BufferedReader br = null; // We buffer to make sure the string is read fully
		// create a file reference
		try {
			br = new BufferedReader(new FileReader(System.getProperty("jboss.server.temp.dir") + "/" + fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

		JSONArray jsonArr = null;

		int i = 0;
		// skip ahead 11 lines of the file
		try {
			while (br.ready() && i < numOfLinesToSkip) {
				System.out.println(br.readLine());
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
		
		// convert csv to json object
		try {
			jsonArr = CDL.toJSONArray(new JSONTokener(br));

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return e1.getMessage();
		}

		// return the resulting json string from the json object
		return "{\"" + fileName.split("\\.")[0] + "\":"
				+ jsonArr.toString() + "}";
		
//		Logic for writing to file if needed		
//		String tempFolderPath = System.getProperty("jboss.server.temp.dir");  
//		
//		try {
//			FileWriter fout = new FileWriter(tempFolderPath + "/test.txt");
//			fout.write("Hello, im a test");
//			fout.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
	}

}
