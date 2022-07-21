package jana60.Repository;

import org.springframework.data.repository.CrudRepository;

import jana60.Model.Pizza;

public interface PizzaRepository extends CrudRepository<Pizza, Integer> {

}
