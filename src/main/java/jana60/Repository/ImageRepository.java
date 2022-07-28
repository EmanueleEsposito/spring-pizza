package jana60.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import jana60.Model.Image;
import jana60.Model.Pizza;

public interface ImageRepository extends CrudRepository<Image, Integer> {

	public List<Image> findByPizza(Pizza pizza);
}
