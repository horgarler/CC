package acme.features.inventor.down;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.downs.Down;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorDownDeleteService implements AbstractDeleteService<Inventor, Down>{
	
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
	public void bind(final Request<Down> request, final Down entity, final Errors errors) {
		assert request != null;
        assert entity != null;
        assert errors != null;
        
        request.bind(entity, errors, "creationMoment", "subject", "explanation", "startPeriod", "endPeriod", "endPeriod", "quantity", "additionalInfo");
		
	}
	
	@Override
	public void validate(final Request<Down> request, final Down entity, final Errors errors) {
		assert request != null;
        assert entity != null;
        assert errors != null;
        
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
	public void delete(final Request<Down> request, final Down entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
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
