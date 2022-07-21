package jana60.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jana60.Model.Pizza;
import jana60.Repository.PizzaRepository;

@Controller
@RequestMapping("/")
public class PizzaController {

	@Autowired
	private PizzaRepository repo;

	@GetMapping
	public String pizzaList(Model model) {
		model.addAttribute("pizze", repo.findAll());
		return "/Pizza/list";
	}

	@GetMapping("/add")
	public String pizzaForm(Model model) {
		model.addAttribute("pizza", new Pizza());
		return "/Pizza/edit";
	}

	@PostMapping("/add")
	public String save(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult br) {
		if (br.hasErrors()) {
			return "pizza/edit";
		} else {
			repo.save(formPizza);
			return "redirect:/";
		}
	}
}
