package edu.sjsu.carbonated.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;




/**
 * @author Michael Hari Session Bean implementation class QuickProcessBean
 */

@Stateless
public class QuickProcessBean implements QuickProcessBeanRemote {
	@PersistenceContext(unitName="CabotUnitInfo")
	EntityManager em;
	
	/**
	 * Default constructor.
	 */
	public QuickProcessBean() {
	}

	public String remoteJSONConvert(String filename) {

		// CSVtoJSON csvToJson = new CSVtoJSON();
		// CSVtoChart csvToChart = new CSVtoChart();
		// csvToChart.createChartImage(filename);
		// return csvToJson.convertCSVFile(filename);
		return null;

	}
	
	// The main
	@Schedule(minute = "*/1", hour = "*")
	public void TimeHandler(Timer timer) {
		// this is ran for as long as the ejb is deployed
		
//		Request aRequest = new Request();
//		aRequest.setRequestBody("asdf");
//		aRequest.setRequestType(1);
//		aRequest.setRequestStatus(0);
//		em.persist(aRequest);
		
		//RequestManager reqMan = new RequestManager();
		
		//reqMan.addRequest("QPB", "AA|Alcoa Inc. Common Stock|N|AA|N|100|N|AA");
		
		System.out.println("QPB would be pulling jobs from database...");

		// go pull jobs from database
		List<Request> jobs = pullJobs();
		
		for(int i = 0; i < jobs.size(); i++){
			System.out.println(jobs.get(i).getJobId());
		}

		String jobId = "5";
		String stockName = "AA";
		String stockRequest = "AA|Alcoa Inc. Common Stock|N|AA|N|100|N|AA";

		// make a directory for each unique jobId
		makeClientDirectory(jobId);

		// call Perlexecute
		getCSV(jobId, stockRequest);

//		 generate JSON and images for each one
		 CSVtoJSON csvToJson = new CSVtoJSON();
//		
		 String jsonResult = csvToJson.convertCSVFile(jobId + "/historic/" + stockName + ".dat");
		
		 //System.out.println(jsonResult);
//		 
		 CSVtoChart csvToChart = new CSVtoChart();
//		
		 csvToChart.createChartImage(jobId + "/historic/" + stockName + ".dat");

		// put back into the database
		 setClientJobStatusToDone();

	}

	/**
	 * Will pull x amount of jobs from the database
	 */
	@SuppressWarnings("unchecked")
	private List<Request> pullJobs() {

		// Will take all jobs that are in the Pending state and are for QPB
		// Will change those jobs to In Progress state
		// a dynamic query using JPQL
		
	//	Query q = em.createNativeQuery("SELECT * FROM carbonatedtrademdb.request");
		Query q = em.createQuery("select r from edu.sjsu.carbonated.server.Request r where r.requestType=0");

		List<Request> r = q.getResultList();

		return r;

	}

	/**
	 * Will make a temporary directory in jboss' tmp directory for each job
	 * 
	 * @param takes
	 *            the jobId as the directory name to make
	 * @return True on success, false on failure to create directory
	 */
	private boolean makeClientDirectory(String jobId) {

		return new File(System.getProperty("jboss.server.temp.dir") + "/"
				+ jobId).mkdir();

	}

	/**
	 * Will use perl script to get dat files from yahoo finance
	 */
	private void getCSV(String jobId, String stockName) {
		
		String dummyStr = "AADR|WCM BNY Mellon Focused Growth ADR ETF|P|AADR|Y|100|N|AADR\n";
		
		// write the company_symbols.dat file that the perl script needs
		try {
			FileWriter fout = new FileWriter(System.getProperty("jboss.server.temp.dir")
					+ "/" + jobId + "/company_symbols.dat");
			fout.write(dummyStr); // to fix perl bug
			fout.write(stockName + "\n");
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PerlExecute.runperl("perl " + System.getProperty("jboss.server.data.dir")
							+ "/stock_data.pl -h " + System.getProperty("jboss.server.temp.dir") + "/" + jobId);

	}
	
	private void setClientJobStatusToDone(){
		
	}

}