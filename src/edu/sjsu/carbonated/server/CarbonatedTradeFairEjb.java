package edu.sjsu.carbonated.server;


import javax.ejb.*;

@Stateless
@Remote(CarbonatedTradeFairInterface.class)
public class CarbonatedTradeFairEjb implements CarbonatedTradeFairInterface {

	private static final long serialVersionUID = 1L;

	//Method to get StockDetails and return details with pictures
	public String getStockDetails(String stockSymbol) {
		System.out.println("getStockDetails: " + stockSymbol );
		//TODO: validate Input
		//TODO: Store Request in DB using JPA
		return "Thank you for your request";
	}


	public String getStockDetailsRequestStatus(String jobId) {
		System.out.println("getStockRequestStatus: " + jobId );
		//TODO: validate Input
		//TODO: Store Request in DB using JPA
		return "Your Request # " + jobId + "is Pending";
	}


	public String getStockAnalysis(String listOfStockSymbols, String startDate,	String endDate) {
		System.out.println("getStockAnalysis: " + listOfStockSymbols + "," + startDate + "," + endDate);
		//TODO: validate Input
		//TODO: Store Request in DB using JPA
		return "Thank you for your request";
	}



	public String getStockAnalysisRequestStatus(String jobId) {
		System.out.println("getStrockAnalysisStatus: " + jobId );
		//TODO: validate Input
		//TODO: Store Request in DB using JPA
		return "Your Request # " + jobId + "is Pending";
	}
	
	

}
