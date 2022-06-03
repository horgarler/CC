package acme.features.inventor.down;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Configuration;
import acme.entities.downs.Down;
import acme.entities.items.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import main.spamDetector;

@Service
public class InventorDownCreateService implements AbstractCreateService<Inventor, Down>{
	
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
        
        request.bind(entity, errors, "code", "subject", "explanation", "startPeriod", "endPeriod", "quantity", "additionalInfo");
        
        final String itemCode = request.getModel().getString("items");
        
        final Item item = this.repository.findOneItemByCode(itemCode);
        entity.setItem(item);

	}
	
	@Override
	public void validate(final Request<Down> request, final Down entity, final Errors errors) {
		assert request != null;
        assert entity != null;
        assert errors != null;
        
        //XXP
        if(!errors.hasErrors("code")) {
            final Date d = entity.getCreationMoment();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            
            final String[] fecha = entity.getCode().split(":");
            final Integer dia = Integer.parseInt(fecha[4]);
            final Integer mes = Integer.parseInt(fecha[2]);
            final Integer anyo = Integer.parseInt(fecha[1]);
            
            final String year = String.valueOf(calendar.get(Calendar.YEAR));
            final char[] digitsYear = year.toCharArray();
            final String ten = digitsYear[2] + "0";
            final String one = digitsYear[0] + "";
            final Integer yearTwoDigits = Integer.parseInt(ten) + Integer.parseInt(one);
            
            final Integer month = calendar.get(Calendar.MONTH) + 1;
            final Integer day = calendar.get(Calendar.DAY_OF_MONTH);

            final Boolean result = (dia.equals(day)) && (mes.equals(month)) && (anyo.equals(yearTwoDigits));
            
            errors.state(request, result, "code", "inventor.down.form.error.code-date");
        }
        
        if(!errors.hasErrors("code")) {
        	errors.state(request, this.repository.findDownByCode(entity.getCode()) == null, "code", "inventor.down.form.error.not-unique");
        }
		
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
        
        if(!errors.hasErrors("items")) {
            final String itemCode = request.getModel().getString("items");
            final Item item = this.repository.findOneItemByCode(itemCode);
        	if (itemCode.equals("No components available")) {
            	errors.state(request, item != null, "items", "inventor.down.form.error.item-not-available");
            }
        }
        
        if(!errors.hasErrors("items")) {
            final String itemCode = request.getModel().getString("items");
            final Item item = this.repository.findOneItemByCode(itemCode);
            errors.state(request, !itemCode.equals(""), "items", "inventor.down.form.error.item-null");
            if (!itemCode.equals("")) {
                errors.state(request, item != null, "items", "inventor.down.form.error.item-does-not-exist");
            }
        }
        
	}

	@Override
	public void unbind(final Request<Down> request, final Down entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		String username = request.getPrincipal().getUsername();
		
		List<String> possible = this.repository.findAllPossibleItemCodes(username);
		List<String> taken = this.repository.findAllTakenItemCodes(username);
		List<String> itemList = new ArrayList<>();

		for(String code : possible) {
			if(!taken.contains(code))
				itemList.add(code);
		}

		if(itemList.isEmpty()) {
			itemList.add("No components available");
		}

		model.setAttribute("items", itemList);
		
		request.unbind(entity, model, "code", "subject", "explanation", "startPeriod", "endPeriod", "quantity", "additionalInfo");
	}
	
	@Override
	public Down instantiate(final Request<Down> request) {
		assert request != null;
		
		Down result;
		result = new Down();
		
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		result.setCreationMoment(moment);	
        
		return result;
	}
	
	@Override
	public void create(final Request<Down> request, final Down entity) {
		assert request != null;
        assert entity != null;
        
        this.repository.save(entity);
	}
	
}
