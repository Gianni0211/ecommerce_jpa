package it.objectmethod.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
	
   @Query(value = "select i from Item i where('' = ?1 or i.code = ?1) and ('' = ?2 or i.name = ?2)")
    public List<Item> findAllItemsByCodeOrName(String code, String name);
}
