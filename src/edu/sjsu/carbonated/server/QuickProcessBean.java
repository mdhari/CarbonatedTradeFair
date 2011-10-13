package edu.sjsu.carbonated.server;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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
