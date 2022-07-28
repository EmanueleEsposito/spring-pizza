package jana60.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import jana60.Model.Image;
import jana60.Model.ImageForm;
import jana60.Service.ImageService;

@Controller
@RequestMapping("/image")
public class ImageController {

	@Autowired
	private ImageService service;

	@GetMapping("/{pizzaId}")
	public String pizzaImages(@PathVariable("pizzaId") Integer pizzaId, Model model) {
		List<Image> images = service.getImagesByPizzaId(pizzaId);

		ImageForm imageForm = service.createImageForm(pizzaId);
		model.addAttribute("imageList", images);
		model.addAttribute("imageForm", imageForm);
		return "image/list";
	}

	@PostMapping("/save")
	public String saveImage(@ModelAttribute("immageForm") ImageForm imageForm) {
		try {
			Image savedImage = service.createImage(imageForm);
			return "redirect:/image/" + savedImage.getPizza().getId();
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to save image");
		}
	}

	@RequestMapping(value = "/{imageId}/content", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImageContent(@PathVariable("imageId") Integer imageId) {
		byte[] content = service.getImageContent(imageId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
	}
}
