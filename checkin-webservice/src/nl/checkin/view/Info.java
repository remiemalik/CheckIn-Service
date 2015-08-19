package nl.checkin.view;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.checkin.control.dao.InMemoryResponseDao;
import nl.checkin.control.dao.Response;
import nl.checkin.util.Attribute;

@Path(Attribute.PUBLIC_URL)
public class Info {
	
	private InMemoryResponseDao responseDao;
	public Info() {
		
	}
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(Attribute.RESPONSES)
	public ArrayList<Response> displayResponses() {
	
		responseDao = InMemoryResponseDao.getInstance();
		ArrayList<Response> list = new ArrayList<>();
		
		if(responseDao.getStoreSize() > 0){
			for (Response response : responseDao.findAllResponses()) {
				list.add(response);
			}
		
		}
		return list;
		
	}
}
