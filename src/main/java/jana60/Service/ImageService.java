package jana60.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jana60.Model.Image;
import jana60.Model.ImageForm;
import jana60.Model.Pizza;
import jana60.Repository.ImageRepository;
import jana60.Repository.PizzaRepository;

@Service
public class ImageService {
	@Autowired
	private ImageRepository repo;
	@Autowired
	private PizzaRepository pizzaRepo;

	public List<Image> getImagesByPizzaId(Integer pizzaId) {
		Pizza pizza = pizzaRepo.findById(pizzaId).get();
		return repo.findByPizza(pizza);
	}

	public ImageForm createImageForm(Integer pizzaId) {
		Pizza pizza = pizzaRepo.findById(pizzaId).get();
		ImageForm img = new ImageForm();
		img.setPizza(pizza);
		return img;
	}

	public Image createImage(ImageForm imageForm) throws IOException {
		Image imgToSave = new Image();
		imgToSave.setPizza(imageForm.getPizza());
		if (imageForm.getContentMultipart() != null) {
			byte[] contentSerialized = imageForm.getContentMultipart().getBytes();
			imgToSave.setContent(contentSerialized);
		}
		return repo.save(imgToSave);
	}

	public byte[] getImageContent(Integer id) {
		Image img = repo.findById(id).get();
		return img.getContent();
	}

}
