package edu.sjsu.carbonated.client;


import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import edu.sjsu.carbonated.server.CarbonatedTradeFairInterface;

public class CarbonatedTradeFairClient {

	String endpoint;
	Context ctx;
	String ejbLocation = "CarbonatedTradeFairEjb/remote";
	
	public CarbonatedTradeFairClient(String endpoint) {
		this.endpoint = endpoint;
	}

	
	/**
	 * NOTE: This method is needed when connecting to an EJB from _outside_ of
	 * the application server (e.g., jboss). If we are running from within
	 * jboss; jboss has a context that you do not need to do this (use @EJB
	 * instead)
	 * 
	 * @return
	 * @throws Exception
	 */
	private Context getContext() throws Exception {
		if (ctx != null)
			return ctx;

		Properties props = new Properties();
		props.put(Context.PROVIDER_URL, "jnp://" + endpoint);
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		props.put(Context.URL_PKG_PREFIXES,
				"org.jboss.naming:org.jnp.interfaces");
		ctx = new InitialContext(props);

		return ctx;
	}
	
	
	public String requestStockDetails(String stockSyb)
	{
		String result = "";
		try {
			System.out.println("requestStockDetails = Request to the server: StockSymbol" + stockSyb);
			long st = System.currentTimeMillis();
			Context ctx = getContext();
			Object ref = ctx.lookup(ejbLocation);
			CarbonatedTradeFairInterface svr = (CarbonatedTradeFairInterface) PortableRemoteObject.narrow(ref,
					CarbonatedTradeFairInterface.class);
			long mt = System.currentTimeMillis();
			result =  svr.getStockDetails(stockSyb);
			System.out.println("From the server: " + result);
			long et = System.currentTimeMillis();

			System.out.println("Connect: " + (mt - st));
			System.out.println("Message: " + (et - mt));
			System.out.println("Total:   " + (et - st));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}

	public String checkStatusStockDetails(int jobId)
	{
		String result = "";
		try {
			System.out.println("checkStatusStockDetails = Request to the server: Job Id" + jobId);
			long st = System.currentTimeMillis();
			Context ctx = getContext();
			Object ref = ctx.lookup(ejbLocation);
			CarbonatedTradeFairInterface svr = (CarbonatedTradeFairInterface) PortableRemoteObject.narrow(ref,
					CarbonatedTradeFairInterface.class);
			long mt = System.currentTimeMillis();
			System.out.println("From the server: ");
			result = svr.getStockDetailsRequestStatus(jobId);
			System.out.println(result);
			long et = System.currentTimeMillis();

			System.out.println("Connect: " + (mt - st));
			System.out.println("Message: " + (et - mt));
			System.out.println("Total:   " + (et - st));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void requestStockAnalysis(String stockSyb, String startTime, String endTime)
	{
		
		try {
			System.out.println("requestStockAnalysis = Request to the server: " + stockSyb + "," + startTime + "," + endTime);
			long st = System.currentTimeMillis();
			Context ctx = getContext();
			Object ref = ctx.lookup(ejbLocation);
			CarbonatedTradeFairInterface svr = (CarbonatedTradeFairInterface) PortableRemoteObject.narrow(ref,
					CarbonatedTradeFairInterface.class);
			long mt = System.currentTimeMillis();
			System.out.println("From the server: " + svr.getStockAnalysis(stockSyb,startTime,endTime));
			long et = System.currentTimeMillis();

			System.out.println("Connect: " + (mt - st));
			System.out.println("Message: " + (et - mt));
			System.out.println("Total:   " + (et - st));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkStatusStockAnalysis(int jobID)
	{
		
		try {
			System.out.println("checkStatusStockAnalysis = Request to the server: StockSymbol" + jobID);
			
			long st = System.currentTimeMillis();
			Context ctx = getContext();
			Object ref = ctx.lookup(ejbLocation);
			CarbonatedTradeFairInterface svr = (CarbonatedTradeFairInterface) PortableRemoteObject.narrow(ref,
					CarbonatedTradeFairInterface.class);
			long mt = System.currentTimeMillis();
			System.out.println("From the server: " + svr.getStockAnalysisRequestStatus(jobID));
			long et = System.currentTimeMillis();

			System.out.println("Connect: " + (mt - st));
			System.out.println("Message: " + (et - mt));
			System.out.println("Total:   " + (et - st));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CarbonatedTradeFairClient clt = new CarbonatedTradeFairClient("localhost:1099");
		
		String numS = clt.requestStockDetails("AA|Alcoa Inc. Common Stock|N|AA|N|100|N|AA");
		int jobid = 0;
		try{
			jobid = Integer.parseInt(numS);
		}catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		boolean processComplete = false;
		String result = "";
		
		while(!processComplete)
		{
			result = clt.checkStatusStockDetails(jobid);
			
			if(!result.equals("PENDING"))
			{
				processComplete = true;
			}
		}
		
		
		/*clt.requestStockAnalysis("MSFT,GOOG", "01/02/2000", "01/02/2011");
		clt.checkStatusStockAnalysis(5);*/

	}

}
