package edu.sjsu.carbonated.server;

/**
 * Original Implementation:
 * http://javahowto.blogspot.com/2006/07/helloworld-with-jpa-hibernate-and.html
 * @author Michael Hari 
 * Modified: 10/16/11
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class RequestManager {
	private EntityManagerFactory emf;
	private EntityManager em;
	private String PERSISTENCE_UNIT_NAME = "jobstorage";


	private void initEntityManager() {
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = emf.createEntityManager();
	}

	private void closeEntityManager() {
		em.close();
		emf.close();
	}
	
	public void readRequestType(String requestType){
		
		initEntityManager();
		
		Query q =  em.createQuery(
		"select * from JobRequests where requestType=" + requestType);
		
		closeEntityManager();
	}
	
	/**
	 * @param requestType QPB or HPB
	 * @param requestBody AA|Alcoa Inc. Common Stock|N|AA|N|100|N|AA
	 * @return void
	 */
	public void addRequest(String requestType, String requestBody){
		
		initEntityManager();
		
		em.getTransaction().begin();

		Request req = new Request(requestType, requestBody, "P");
        
		em.persist(req);
		em.getTransaction().commit();
		
		closeEntityManager();
	}

	private void read() {
		Request g = (Request) em.createQuery(
				"select g from Greeting g where g.language = :language")
				.setParameter("language", "en");
		System.out.println("Query returned: " + g);
	}
}