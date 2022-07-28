package jana60.Model;

import org.springframework.web.multipart.MultipartFile;

public class ImageForm {

	private Integer id;
	private Pizza pizza;
	private MultipartFile contentMultipart;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	public MultipartFile getContentMultipart() {
		return contentMultipart;
	}

	public void setContentMultipart(MultipartFile contentMultipart) {
		this.contentMultipart = contentMultipart;
	}

}
