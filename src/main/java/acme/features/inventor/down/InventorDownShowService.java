package acme.features.inventor.down;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.downs.Down;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorDownShowService implements AbstractShowService<Inventor, Down>{
	
	@Autowired
	protected InventorDownRepository repository;
	 
	@Override
	public boolean authorise(final Request<Down> request) {
		assert request != null;

		boolean result;
		result = request.getPrincipal().hasRole(Inventor.class);
		
		return result;
	}

	@Override
	public Down findOne(final Request<Down> request) {
		assert request != null;
		
		Down result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneDown(id);
		
		return result;
	}

	@Override
	public void unbind(final Request<Down> request, final Down entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "creationMoment", "subject", "explanation", "startPeriod", "endPeriod", "quantity", "additionalInfo");
		
		final String item = entity.getItem().getCode();
		model.setAttribute("item", item);
		
	}
	
}
