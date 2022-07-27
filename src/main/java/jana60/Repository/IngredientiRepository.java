package jana60.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import jana60.Model.Ingredienti;

public interface IngredientiRepository extends CrudRepository<Ingredienti, Integer> {

	public List<Ingredienti> findAllByOrderByNome();
}
