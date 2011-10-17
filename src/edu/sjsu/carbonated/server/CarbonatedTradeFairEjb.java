package edu.sjsu.carbonated.server;


import java.io.File;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Remote(CarbonatedTradeFairInterface.class)
public class CarbonatedTradeFairEjb implements CarbonatedTradeFairInterface {

	
	@PersistenceContext(unitName="CabotUnitInfo")
	EntityManager em;
	
	private static final long serialVersionUID = 1L;

	//Method to get StockDetails and return details with pictures
	public String getStockDetails(String stockSymbol) {
		System.out.println("getStockDetails: " + stockSymbol );
		
		if(!stockSymbol.isEmpty())
		{
			//Create RequestObject
			Request aRequest = new Request();
			aRequest.setRequestBody(stockSymbol);
			aRequest.setRequestType(0);
			aRequest.setRequestStatus(0);
			em.persist(aRequest);
			
			return "Thank you for your request. Your request id is" + aRequest.getJobId();
			
		}else{
			return "Syntex error with your Request";
		}
	
		
	}


	
	public String getStockDetailsRequestStatus(int jobId) {
		System.out.println("getStockRequestStatus: " + jobId );
		int job_id;
		
		if(jobId != 0)
		{
			//Create RequestObject
			Request aRequest = new Request();
			
			aRequest = em.find(Request.class, jobId);
			
			if(aRequest.getRequestStatus() ==  1)
			{
				return aRequest.getResult();

			}else{
				return "Your request is still pending";
			}
			
			
		}else{
			return "Syntex error with your Request";
		}
	
		
	}


	public String getStockAnalysis(String listOfStockSymbols, String startDate,	String endDate) {
		System.out.println("getStockAnalysis: " + listOfStockSymbols + "," + startDate + "," + endDate);

		if(! listOfStockSymbols.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty() )
		{
			//MSFT-GOOG-ATT,01/02/2010,01/02/2011
			String requestBody = listOfStockSymbols + "," + startDate + "," + endDate;
			
			Request aRequest = new Request();
			aRequest.setRequestBody(requestBody);
			aRequest.setRequestType(0);
			aRequest.setRequestStatus(0);
			em.persist(aRequest);
			
			return "Thank you for your request. Your request id is" + aRequest.getJobId();
			
		}else{
			
			return "Syntex error with your Request";
		}
		
	}



	public String getStockAnalysisRequestStatus(int jobId) {
		System.out.println("getStrockAnalysisStatus: " + jobId );
		
		int job_id;
		
		if(jobId != 0)
		{
			//Create RequestObject
			Request aRequest = new Request();
			
			aRequest = em.find(Request.class, 1);
			
			if(aRequest.getRequestStatus() ==  1)
			{
				return "Your request has been completed." ;
				//TODO: Return JSON
			}else{
				return "Your request is still pending";
			}
			
			
		}else{
			return "Syntex error with your Request";
		}
	

	}
	
	

}
