package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Item;

import repositories.ItemRepository;

@Transactional
@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	public Collection<Item> itemsPerProvider(int id){
		return this.itemRepository.itemsPerProvider(id);
	}
	public void deleteItemsPerProvider(Collection<Item>col){
		
		this.itemRepository.deleteInBatch(col);
	}
}
