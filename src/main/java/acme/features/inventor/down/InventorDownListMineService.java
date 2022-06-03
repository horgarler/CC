package acme.features.inventor.down;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.downs.Down;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorDownListMineService implements AbstractListService<Inventor, Down>{
	
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
	public Collection<Down> findMany(final Request<Down> request) {
		assert request != null;
		
		Collection<Down> result;
		Principal principal;

		principal = request.getPrincipal(); 
        result = this.repository.findMineDown(principal.getUsername());
		return result;
	}

	@Override
	public void unbind(final Request<Down> request, final Down entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "subject");
		
		final String item = entity.getItem().getCode();
		model.setAttribute("item", item);
		
	}
	
}
