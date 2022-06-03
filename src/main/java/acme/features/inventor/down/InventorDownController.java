package acme.features.inventor.down;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.downs.Down;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorDownController extends AbstractController<Inventor, Down>{
	
	  @Autowired
	  protected InventorDownListMineService    listMineService;
	  
	  @Autowired
	  protected InventorDownShowService    showService;
	  
	  @Autowired
	  protected InventorDownCreateService createService;
	  
	  @Autowired
	  protected InventorDownUpdateService updateService;
	  
	  @Autowired
	  protected InventorDownDeleteService deleteService;
	  
	  @PostConstruct
	  protected void initialise() {
	        super.addCommand("list-mine", "list", this.listMineService);
	        super.addCommand("show", this.showService);
	        super.addCommand("create", this.createService);
	        super.addCommand("update", this.updateService);
	        super.addCommand("delete", this.deleteService);
	    }

}
