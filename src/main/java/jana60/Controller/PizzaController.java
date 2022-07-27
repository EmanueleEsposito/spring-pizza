package jana60.Controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jana60.Model.Pizza;
import jana60.Repository.IngredientiRepository;
import jana60.Repository.PizzaRepository;

@Controller
@RequestMapping("/")
public class PizzaController {

	@Autowired
	private PizzaRepository repo;

	@Autowired
	private IngredientiRepository ingredientiRepo;

	@GetMapping
	public String pizzaList(Model model) {
		model.addAttribute("pizze", repo.findAll());
		return "/Pizza/list";
	}

	@GetMapping("/advanced_search")
	public String advancedSearch() {
		return "/pizza/search";
	}

	@GetMapping("/search")
	public String search(@RequestParam(name = "queryNome") String queryNome, Model model) {
		if (queryNome != null && queryNome.isEmpty()) {
			queryNome = null;
		}
		List<Pizza> pizze = repo.findByNomeContainingIgnoreCase(queryNome);
		model.addAttribute("pizze", pizze);
		return "/pizza/list";
	}

	@GetMapping("/salva")
	public String pizzaForm(Model model) {
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("ingredientiList", ingredientiRepo.findAllByOrderByNome());
		return "/pizza/edit";
	}

	@PostMapping("/salva")
	public String save(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult br, Model model) {
		boolean hasErrors = br.hasErrors();
		if (hasErrors) {

			model.addAttribute("ingredientiList", ingredientiRepo.findAllByOrderByNome());
			return "/pizza/edit";
		} else {

			try {
				repo.save(formPizza);
			} catch (Exception e) {
				model.addAttribute("errorMessage", "Unable to save the Pizza");
				model.addAttribute("categoryList", ingredientiRepo.findAllByOrderByNome());
				return "/pizza/edit";
			}
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
			model.addAttribute("ingredientiList", ingredientiRepo.findAllByOrderByNome());
			return "/pizza/edit";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id" + pizzaId + "non è presente");
		}
	}

}
