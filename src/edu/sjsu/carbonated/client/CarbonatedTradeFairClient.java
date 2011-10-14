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
	
	
	public void requestStockDetails(String stockSyb)
	{
		
		try {
			System.out.println("Request to the server: StockSymbol" + stockSyb);
			long st = System.currentTimeMillis();
			Context ctx = getContext();
			Object ref = ctx.lookup(ejbLocation);
			CarbonatedTradeFairInterface svr = (CarbonatedTradeFairInterface) PortableRemoteObject.narrow(ref,
					CarbonatedTradeFairInterface.class);
			long mt = System.currentTimeMillis();
			System.out.println("From the server: " + svr.getStockDetails(stockSyb));
			long et = System.currentTimeMillis();

			System.out.println("Connect: " + (mt - st));
			System.out.println("Message: " + (et - mt));
			System.out.println("Total:   " + (et - st));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkStatusStockDetails(String jobId)
	{
		
		try {
			System.out.println("Request to the server: Job Id" + jobId);
			long st = System.currentTimeMillis();
			Context ctx = getContext();
			Object ref = ctx.lookup(ejbLocation);
			CarbonatedTradeFairInterface svr = (CarbonatedTradeFairInterface) PortableRemoteObject.narrow(ref,
					CarbonatedTradeFairInterface.class);
			long mt = System.currentTimeMillis();
			System.out.println("From the server: " + svr.getStockDetailsRequestStatus(jobId));
			long et = System.currentTimeMillis();

			System.out.println("Connect: " + (mt - st));
			System.out.println("Message: " + (et - mt));
			System.out.println("Total:   " + (et - st));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void requestStockAnalysis(String stockSyb, String startTime, String endTime)
	{
		
		try {
			System.out.println("Request to the server: " + stockSyb + "," + startTime + "," + endTime);
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
	
	public void checkStatusStockAnalysis(String stockSyb)
	{
		
		try {
			System.out.println("Request to the server: StockSymbol" + stockSyb);
			
			long st = System.currentTimeMillis();
			Context ctx = getContext();
			Object ref = ctx.lookup(ejbLocation);
			CarbonatedTradeFairInterface svr = (CarbonatedTradeFairInterface) PortableRemoteObject.narrow(ref,
					CarbonatedTradeFairInterface.class);
			long mt = System.currentTimeMillis();
			System.out.println("From the server: " + svr.getStockAnalysisRequestStatus(stockSyb));
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
		
		clt.requestStockDetails("MSFT");
		clt.checkStatusStockDetails("1234");
		
		clt.requestStockAnalysis("MSFT,GOOG", "01/02/2000", "01/02/2011");
		clt.checkStatusStockAnalysis("5678");

	}

}
