package acme.features.inventor.down;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Configuration;
import acme.entities.downs.Down;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import main.spamDetector;

@Service
public class InventorDownUpdateService implements AbstractUpdateService<Inventor, Down> {
	
	// Internal State 
	
	@Autowired
	protected InventorDownRepository repository;
		
	//AbstractUpdateService<Inventor, Down> interface
	
	@Override
	public boolean authorise(final Request<Down> request) {
		assert request != null;
		final java.util.Collection<Down> mines = this.repository.findMineDown(request.getPrincipal().getUsername());
		final Down down = this.repository.findOneDown(request.getModel().getInteger("id"));
		
		final boolean result;
		result = request.getPrincipal().hasRole(Inventor.class)&&mines.contains(down);
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
	public void unbind(final Request<Down> request, final Down entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model,"code",  "creationMoment", "subject", "explanation", "startPeriod", "endPeriod", "endPeriod", "quantity", "additionalInfo");
		final String item = entity.getItem().getCode();
		model.setAttribute("item", item);
		
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
	public void validate(final Request<Down> request, final Down entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if(!errors.hasErrors("subject")) {
        	final Configuration configuration = this.repository.findConfiguration();
        	final String[] sp = configuration.getWeakSpamTerms().split(",");
        	final List<String> softSpam = new ArrayList<String>(Arrays.asList(sp));
        	final Double softThreshold = configuration.getWeakSpamThreshold();
        	final String[] hp = configuration.getStrongSpamTerms().split(",");
        	final List<String> hardSpam = new ArrayList<String>(Arrays.asList(hp));
        	final Double hardThreshold = configuration.getStrongSpamThreshold();
        	errors.state(request, !spamDetector.isSpam(entity.getSubject(), softSpam, softThreshold, hardSpam, hardThreshold), "subject", "inventor.down.form.error.spam");
        }
		
		if(!errors.hasErrors("explanation")) {
        	final Configuration configuration = this.repository.findConfiguration();
        	final String[] sp = configuration.getWeakSpamTerms().split(",");
        	final List<String> softSpam = new ArrayList<String>(Arrays.asList(sp));
        	final Double softThreshold = configuration.getWeakSpamThreshold();
        	final String[] hp = configuration.getStrongSpamTerms().split(",");
        	final List<String> hardSpam = new ArrayList<String>(Arrays.asList(hp));
        	final Double hardThreshold = configuration.getStrongSpamThreshold();
        	errors.state(request, !spamDetector.isSpam(entity.getExplanation(), softSpam, softThreshold, hardSpam, hardThreshold), "explanation", "inventor.down.form.error.spam");
        }
		
		
		if(!errors.hasErrors("startPeriod")) {
        	final Date startPeriod = entity.getStartPeriod();
        	final Calendar calendar = Calendar.getInstance();
        	calendar.setTime(entity.getCreationMoment()); // Aquí no tendremos en cuenta la fecha de actualización, sino de creación
        	calendar.add(Calendar.MONTH, 1);
        	calendar.add(Calendar.SECOND, -1); // Un mes menos un segundo
        	errors.state(request, startPeriod.after(calendar.getTime()), "startPeriod", "inventor.down.form.error.start-period-not-enough");
        }
		
		if(!errors.hasErrors("endPeriod") && entity.getStartPeriod()!=null) {
        	final Date startPeriod = entity.getStartPeriod();
        	final Date endPeriod = entity.getEndPeriod();
        	final Date moment = new Date(startPeriod.getTime() + 604799999); 
        	errors.state(request, endPeriod.after(moment), "endPeriod", "inventor.down.form.error.end-period-one-week-before-start-period");
        }
		
		if(!errors.hasErrors("quantity")) {
            final String acceptedCurrencies = this.repository.findConfiguration().getAcceptedCurrencies();
            final String[] currencies = acceptedCurrencies.split(",");
            boolean isCorrect = false;
            final String c = entity.getQuantity().getCurrency();
            for (final String currency : currencies) {
                if (c.equals(currency)) {
                    isCorrect = true;
                }
            }
            errors.state(request, isCorrect, "quantity", "inventor.down.form.error.incorrect-currency");
        }
        
        if(!errors.hasErrors("quantity")) {
            errors.state(request, entity.getQuantity().getAmount() >= 0.0, "quantity", "inventor.down.form.error.negative-budget");
        }
		
	}
		
	@Override
	public void update(final Request<Down> request, final Down entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
