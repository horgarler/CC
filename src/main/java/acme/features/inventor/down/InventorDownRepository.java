package acme.features.inventor.down;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Configuration;
import acme.entities.downs.Down;
import acme.entities.items.Item;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorDownRepository extends AbstractRepository {
	
	@Query("select x from Down x where x.item.inventor.userAccount.username = :username")
	Collection<Down> findMineDown(String username);
	
	@Query("select x from Down x where x.id = :id")
	Down findOneDown(int id);
	
	@Query("select x from Down x where x.code = :code")
	Down findDownByCode(String code);
	
	@Query("select c from Configuration c")
	Configuration findConfiguration();

	@Query("select i from Item i where i.code = :code")
	Item findOneItemByCode(String code);
	
	@Query("select i.code from Item i where i.published = 1 and i.itemType = 1 and i.inventor.userAccount.username = :username")
	List<String> findAllPossibleItemCodes(String username);
	
	@Query("select x.item.code from Down x where x.item.inventor.userAccount.username = :username")
	List<String> findAllTakenItemCodes(String username);
  
}
