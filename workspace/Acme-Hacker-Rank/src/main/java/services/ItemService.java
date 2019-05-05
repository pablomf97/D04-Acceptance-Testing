package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import domain.Actor;
import domain.Item;
import domain.Provider;

@Transactional
@Service
public class ItemService {
	
	// Managed repository ------------------------------------
	
	@Autowired
	private ItemRepository itemRepository;
	
	// Supporting services -----------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private Validator validator;
	
	public Item create() {
		Actor principal;
		Item result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "PROVIDER"),
				"not.allowed");

		result = new Item();
		result.setProvider((Provider) principal);

		return result;
	}

	public Collection<Item> findAll() {
		Collection<Item> result;
		result = this.itemRepository.findAll();

		return result;
	}

	public Item findOne(final int itemId) {
		Item result;
		result = this.itemRepository.findOne(itemId);

		return result;
	}
	
	public Item save(final Item item) {
		Actor principal;
		Item result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "PROVIDER"), "not.allowed");
		
		Assert.notNull(item.getName());
		Assert.notNull(item.getDescription());
		Assert.notNull(item.getLinks());
		Assert.notNull(item.getProvider());
		
		Assert.isTrue((item.getProvider()).equals(principal), "not.allowed");

		result = this.itemRepository.save(item);

		return result;
	}
	
	public void delete(final Item item) {
		Actor principal;

		Assert.notNull(item);
		Assert.isTrue(item.getId() != 0, "wrong.id");

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "PROVIDER"),
				"not.allowed");

		Assert.isTrue(item.getProvider().equals(principal), "not.allowed");

		this.itemRepository.delete(item.getId());
	}
	
	// Other business methods -------------------------------
	
	public void flush() {
		this.itemRepository.flush();
	}
	
	public Collection<Item> itemsPerProvider(int id){
		return this.itemRepository.itemsPerProvider(id);
	}
	
	public void deleteItemsPerProvider(Collection<Item>col){
		
		this.itemRepository.deleteInBatch(col);
	}
	
	public Collection<String> checkSplitAttachments(final String attachments, final BindingResult binding) {
		final Collection<String> res = new ArrayList<>();
		if (attachments != null && !attachments.isEmpty()) {
			final String[] slice = attachments.split(",");
			for (final String p : slice)
				if (p.trim() != "") {
					try {
						Assert.isTrue(ResourceUtils.isUrl(p), "error.url");
					} catch (final Throwable oops) {
						binding.rejectValue("attachments", "error.url");
					}
					res.add(p);
				}
		}
		return res;
	}
	
	public Item reconstruct(final Item item,
			 BindingResult binding) {
		Item result;
		Actor principal = this.actorService.findByPrincipal();
		
		if (item.getId() == 0) {
			result = this.create();
			
		} else {
			result = this.findOne(item.getId());
			Assert.isTrue(result.getProvider().equals((Provider) principal));
		}
		result.setName(item.getName());
		result.setDescription(item.getDescription());
		result.setLinks(item.getLinks());
		result.setPictures(item.getPictures());

		this.validator.validate(result, binding);
		
		return result;
	}
}
