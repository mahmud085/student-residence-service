package org.daaaccess;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.*;
import javax.persistence.metamodel.Type.PersistenceType;
import javax.transaction.TransactionRequiredException;


/**
 * {@link Storage} class manages all the DB transactions. 
 * 
 * @author Kowshik Dipta Das Joy
 *
 */
public final class Storage {

	private final Logger log =Logger.getLogger(this.getClass().getName());
	//This we do not need
	//@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private  EntityManager emanager;

	private static Storage INSTANCE;

	private Storage() {

	}

	/**
	 * If an instance of Storage class does not exists , creates the instance.
	 * @return instance of the storage class
	 */

	public static  Storage instance() {
		if(Storage.INSTANCE==null)
			Storage.INSTANCE=new Storage();
		return Storage.INSTANCE;
	}

	/**
	 * 
	 * @param Perperties to run JPA instance.
	 */
	public Storage build(Properties properties) {


		emanager= Persistence
				.createEntityManagerFactory(properties.getProperty("persistent.unitname") , getDBProperties(properties))
				.createEntityManager();


		return Storage.INSTANCE;
	}



	/**
	 * Executes a named query. Such as update, Delete etc.
	 * @param namedQuery , className
	 * @return Fetched Result of type {@link IBeverageEntity}
	 */
	public List<?> execute(String namedQuery , Class<?> className) {
		EntityTransaction transaction = emanager.getTransaction();
		transaction.begin();
		TypedQuery<?> dataObjectList = emanager.createNamedQuery(namedQuery , className);
		transaction.commit();
		return dataObjectList.getResultList();
	}


	/**
	 * Initially this method will try to insert data into DB. 
	 * Incase of {@link EntityExistsException} this will try to update data in database
	 * @param Object of the Entity Type .
	 * @return If Execution was successful or not.
	 */
	public void execute(Object obj) {
		System.out.println("Google");
		EntityTransaction transaction = emanager.getTransaction();
		System.out.println(transaction);
		transaction.begin();
		System.out.println("Began Transaction");
		try{

			emanager.persist(obj);
		}
		catch(Exception e) {

			System.out.println(e.getMessage());
		}
		System.out.println("Insertion error");
		transaction.commit();
	}



	/**
	 * 
	 * @param Object 
	 * @return number of rows effected
	 */
	public int update(String namedQuery , Map<String, String> parameterList) {
		Query q =emanager.createNamedQuery(namedQuery);


		Set<Entry<String, String>> propEntries =parameterList.entrySet();
		for (Entry<String, String> obj : propEntries) {
			q.setParameter(obj.getKey().toString(), obj.getValue().toString());

		}	
		return q.executeUpdate();
	}


	/**
	 * 
	 * @param Object 
	 */
	public void insert(Object object) {
		this.execute(object);

	}

	public List<?> execute(String namedQuery , Map<String, String> parameterList){

		Query q =emanager.createNamedQuery(namedQuery);


		Set<Entry<String, String>> propEntries =parameterList.entrySet();
		for (Entry<String, String> obj : propEntries) {
			q.setParameter(obj.getKey().toString(), obj.getValue());

		}
		return q.getResultList();
	}

	/**
	 * Get all mentioned Properties needed for javax.JDBC.
	 * @param properties
	 * @return List map for the properties.
	 */
	private Map<String, String> getDBProperties(Properties properties){

		StringBuilder b = new StringBuilder("javax.persistence.");
		Map<String, String> prop=new HashMap<String, String>();

		Set<Entry<Object, Object>> propEntries = properties.entrySet();
		for (Entry<Object, Object> obj : propEntries) {
			if(obj.getKey().toString().contains("jdbc"))
				prop.put(b+obj.getKey().toString(), obj.getValue().toString());
		}
		return prop;
	}

	public <T> T executeForSingle(String namedQuery, Map<String, Object> paramList) {
		Query query = buildNamedQuery(namedQuery, paramList);

		return (T) query.getSingleResult();
	}

	public <T> List<T> executeForMultiple(String namedQuery, Map<String, Object> paramList) {
		Query query = buildNamedQuery(namedQuery, paramList);

		return query.getResultList();
	}

	public <T> List<T> executeForMultiple(String namedQuery, Map<String, Object> paramList, int pageNum, int pageSize) {
		Query query = buildNamedQuery(namedQuery, paramList);
		query.setFirstResult((pageNum - 1) * pageSize);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

	public int executeUpdate(String namedQuery , Map<String, Object> paramList) {
		Query query = buildNamedQuery(namedQuery, paramList);

		emanager.getTransaction().begin();

		int updateResult = query.executeUpdate();

		emanager.getTransaction().commit();

		return updateResult;
	}

	private Query buildNamedQuery(String namedQuery, Map<String, Object> paramList) {
		Query query = emanager.createNamedQuery(namedQuery);

		if (paramList == null) {
			return query;
		}

		for (Entry<String, Object> entry : paramList.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query;
	}
}
