package edu.sjsu.carbonated.server;

import java.io.Serializable;

public interface CarbonatedTradeFairInterface extends Serializable {

	//This method will be used for initial request from Consultants
	//Input: Stock Symbol (for example MSFT, AAPL )
	//Return Value: JSON Object String which contains Job Id, date time, other message and params 
	String getStockDetails(String stockSymbol);
	

	//This method will be used by Consultant to check on status of their request
	//Input: Job id Number
	//Return Value: JSON Object String which contains either
	//	1) If Job is done, JSON object with all the stock information
	//	2) If job is not completed, JSON object with Job Status (Pending)
	String getStockDetailsRequestStatus(int jobId);
	
	
	//This method will be used by Analytical team to get lots of data (CSV)
	//Input: Stock Symbols separated by ,. For example: ("MSFT,GOOG,AAPL", 01/02/1980, 03/11/2011)
	//Return value: JSON Object string which contains Job Id, date Time and other message and params
	String getStockAnalysis(String listOfStockSymbols, String startDate, String endDate);
	
	
	//This method will be used by Analytical team to check on status of their request
	//Input: Job id Number
	//Return Value: JSON Object String which contains either
	//	1) If Job is done, JSON object with Link to file and other information
	//	2) If job is not completed, JSON object with Job Status (Pending)
	String getStockAnalysisRequestStatus(int jobId);
	
	
}
