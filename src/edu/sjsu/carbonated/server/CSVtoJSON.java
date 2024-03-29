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
	public String convertCSVFile(String filePath) {

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

		int numOfLinesToSkip = 12; // the reason why we skip is above

		BufferedReader br = null; // We buffer to make sure the string is read fully
		int i = 0;
		// create a file reference
		try {
			br = new BufferedReader(new FileReader(System.getProperty("jboss.server.temp.dir") + "/" + filePath));
			while (br.ready() && i < numOfLinesToSkip) {
				//System.out.println(br.readLine());
				br.readLine();
				i++;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONArray jsonArr = null;
		
		// convert csv to json object
		try {
			jsonArr = CDL.toJSONArray(new JSONTokener(br));

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return e1.getMessage();
		}

		//getGraph(fileName);
		
		String stockPath = filePath.split("\\.")[0];
		String[] fileNameSplit = stockPath.split("/");
		
		
		// return the resulting json string from the json object
		try {
			FileWriter fout = new FileWriter(System.getProperty("jboss.server.temp.dir") + "/" + filePath.split("\\.")[0] + ".json");
			fout.write("{\"" + fileNameSplit[fileNameSplit.length-1] + "\":"
					+ jsonArr.toString() + "}");
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return System.getProperty("jboss.server.temp.dir") + "/" + filePath.split("\\.")[0] + ".json";
		
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
	
//	public void getGraph(String fileName){
//		//http://www.androidsnippets.com/executing-a-http-post-request-with-httpclient
//		
//		int numOfLinesToSkip = 11; // the reason why we skip is above
//
//		BufferedReader br = null; // We buffer to make sure the string is read fully
//		// create a file reference
//		try {
//			br = new BufferedReader(new FileReader(System.getProperty("jboss.server.temp.dir") + "/" + fileName));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		int i = -1;
//		// skip ahead 12 lines of the file
//		try {
//			while (br.ready() && i < numOfLinesToSkip) {
//				System.out.println(br.readLine());
//				i++;
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		String strBuild = "";
//		
//		int lineLimit = 150,j = 0;
//		try {
//			while(br.ready() && j < lineLimit){
//				
//				String str = br.readLine();
//				String[] strParse = str.split(",");
//				strBuild += strParse[strParse.length-1] + ",";
//				j++;
//			}
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		strBuild = strBuild.substring(0, strBuild.length()-1); // remove the ,
//		
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpPost httpPost = new HttpPost("http://chart.apis.google.com/chart");
//
//		System.out.println(strBuild);
//		    // Add your data
//		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//		    nameValuePairs.add(new BasicNameValuePair("cht","lxy"));
//		    nameValuePairs.add(new BasicNameValuePair("chd", "t:-1|"+strBuild));
//		    nameValuePairs.add(new BasicNameValuePair("chs", "440x220"));
//		    try {
//				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//			    // Execute HTTP Post Request
//			    HttpResponse response = httpClient.execute(httpPost);
//			    
//			    response.getEntity().writeTo(new FileOutputStream(System.getProperty("jboss.server.temp.dir") + "/" + fileName + ".png") );
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ClientProtocolException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		   
//
//
//	}

}
