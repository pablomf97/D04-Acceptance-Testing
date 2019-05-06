
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ItemService;
import domain.Actor;
import domain.Item;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ItemService		itemService;


	public ItemController() {
		super();
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int itemId) {
		ModelAndView result;
		Item item;
		boolean isPrincipal = false;
		Actor principal;

		try {
			item = this.itemService.findOne(itemId);
			try {
				principal = this.actorService.findByPrincipal();
				if (item.getProvider().equals(principal))
					isPrincipal = true;
			} catch (final Throwable oops) {
			}

			final BindingResult binding = null;
			final Collection<String> links = this.itemService.checkSplitAttachments(item.getLinks(), binding);
			final Collection<String> pictures = this.itemService.checkSplitAttachments(item.getPictures(), binding);

			result = new ModelAndView("item/display");
			result.addObject("item", item);
			result.addObject("links", links);
			result.addObject("pictures", pictures);
			result.addObject("isPrincipal", isPrincipal);
			result.addObject("requestURI", "item/display.do?itemId=" + itemId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "position.commit.error");
			result.addObject("permission", false);
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result = new ModelAndView("item/list");
		Collection<Item> items;
		Actor principal;

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal, "PROVIDER"));

			items = this.itemService.itemsPerProvider(principal.getId());

			result.addObject("items", items);
			result.addObject("permission", true);

		} catch (final Throwable oops) {
			result.addObject("errMsg", oops);
			result.addObject("permission", false);
		}
		return result;
	}

	/* List of items */
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll(@RequestParam(required = false) final Integer providerId) {
		ModelAndView result;
		try {
			Collection<Item> items = new ArrayList<>();
			result = new ModelAndView("item/listAll");
			if (providerId != null)
				items = this.itemService.itemsPerProvider(providerId);
			else
				items = this.itemService.findAll();
			result.addObject("requestURI", "/item/listAll.do");
			result.addObject("items", items);
			result.addObject("permission", true);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "position.commit.error");
			result.addObject("permission", false);
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result = null;
		try {
			final Item item = this.itemService.create();

			result = this.createEditModelAndView(item);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
		}
		return result;
	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int itemId) {
		ModelAndView result;
		Item item;
		try {
			item = this.itemService.findOne(itemId);
			Assert.notNull(item);

			result = this.createEditModelAndView(item);
			result.addObject("itemId", itemId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Item item, final BindingResult binding) {
		ModelAndView result;
		Item aux;
		try {
			aux = this.itemService.reconstruct(item, binding);
			if (binding.hasErrors()) {

				result = new ModelAndView("item/edit");
				result.addObject("item", item);
				result.addObject("binding", binding);
				result.addObject("isPrincipal", true);
			} else
				try {
					this.itemService.save(aux);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					result = new ModelAndView("item/edit");
					result.addObject("item", aux);
					result.addObject("messageCode", oops.getMessage());
				}
		} catch (final Throwable oops) {
			if (binding.hasErrors())
				result = this.createEditModelAndView(item, "jpa.error");
			else
				result = this.createEditModelAndView(item, "commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int itemId) {
		ModelAndView result;
		try {
			final Item item = this.itemService.findOne(itemId);
			this.itemService.delete(item);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", oops.getMessage());
		}
		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Item item) {
		ModelAndView result;

		result = this.createEditModelAndView(item, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Item item, final String messageCode) {
		final ModelAndView result;
		Actor principal;
		boolean isPrincipal = true;

		if (messageCode == null) {
			principal = this.actorService.findByPrincipal();

			if (principal.getId() != item.getProvider().getId())
				isPrincipal = false;
		}

		result = new ModelAndView("item/edit");
		result.addObject("item", item);
		result.addObject("isPrincipal", isPrincipal);
		result.addObject("message", messageCode);

		return result;
	}
}
