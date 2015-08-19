package nl.checkin.control.dao;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlRootElement;

import nl.checkin.control.factory.Factory;
import nl.checkin.control.factory.FactoryImpl;

@XmlRootElement
public class InMemoryResponseDao {

	private TreeMap<String, Response> responseStore = new TreeMap<>();
	private static InMemoryResponseDao instance;
	private FactoryImpl factory;
	
	enum SortBy{ MESSAGE}

    
	public  static InMemoryResponseDao getInstance() {
		if (instance == null) {
			instance = new InMemoryResponseDao();
		}

		return instance;
	}

	private InMemoryResponseDao() {
		factory = new FactoryImpl();

		create(factory.createResponse(Factory.Type.ERROR, "302","missing parameter"));
		create(factory.createResponse(Factory.Type.ERROR, "303","invalid token"));
		create(factory.createResponse(Factory.Type.ERROR, "304","wrong username or password"));
		create(factory.createResponse(Factory.Type.ERROR, "305","some sql went wrong"));
		create(factory.createResponse(Factory.Type.ERROR, "306","could not perform action"));
		create(factory.createResponse(Factory.Type.SUCCES));

	}

	public Collection<Response> findAllResponses() {
		return responseStore.values();
	}
	
	public Response findResponseByCode(String code) {
		return responseStore.get(code);
	}

	public void create(Response response) {
		responseStore.put(response.getCode(), response);
	}

	public void delete(Response response) {
		responseStore.remove(response);
	}
	
	public int getStoreSize(){
		return responseStore.size();
	}
	
	public void sort(SortBy sort){
		
		Helper helper = new Helper();
		switch(sort){
		
		case MESSAGE:
			break;
		default :
			break;
		}
	}
	
	class Helper{
		private Comparator<Response> sortByMessage = new Comparator<Response>() {
			
			@Override
			public int compare(Response first, Response second) {
				// TODO Auto-generated method stub
				return first.getCode().compareTo(second.getCode());
			}
		};

		
		
		

	}

}
