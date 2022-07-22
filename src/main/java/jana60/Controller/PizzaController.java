package jana60.Controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@GetMapping("/salva")
	public String pizzaForm(Model model) {
		model.addAttribute("pizza", new Pizza());
		return "/pizza/edit";
	}

	@PostMapping("/salva")
	public String save(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult br, Model model) {
		if (br.hasErrors()) {
			return "pizza/edit";
		} else {
			repo.save(formPizza);
			return "redirect:/";
		}

	}

	@GetMapping("/elimina/{id}")
	public String elimina(@PathVariable("id") Integer pizzaId, RedirectAttributes ra) {
		Optional<Pizza> result = repo.findById(pizzaId);
		if (result.isPresent()) {
			repo.delete(result.get());
			ra.addFlashAttribute("successMessage",
					" La Pizza" + "" + result.get().getNome() + "" + "" + " è stata correttamente eliminata!");
			return "redirect:/";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + pizzaId + " non è presente");
		}
	}

	@GetMapping("/modifica/{id}")
	public String modifica(@PathVariable("id") Integer pizzaId, Model model) {
		Optional<Pizza> result = repo.findById(pizzaId);
		if (result.isPresent()) {
			model.addAttribute("pizza", result.get());
			return "/pizza/edit";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id" + pizzaId + "non è presente");
		}
	}

}
