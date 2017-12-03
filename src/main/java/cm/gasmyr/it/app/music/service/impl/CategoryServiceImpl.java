package cm.gasmyr.it.app.music.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cm.gasmyr.it.app.music.core.Category;
import cm.gasmyr.it.app.music.repository.CategoryRepository;
import cm.gasmyr.it.app.music.service.CategoryService;

@Service
@Component
public class CategoryServiceImpl implements CategoryService {
	final CategoryRepository categoryRepository;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> listAll() {
		return (List<Category>) categoryRepository.findAll();
	}

	@Override
	public Category getById(Long id) {
		return categoryRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		categoryRepository.delete(id);
	}

	@Override
	public Category saveOrUpdate(Category categoryToSave) {
		Category category = categoryRepository.findOne(categoryToSave.getId());
		if (category != null) {
			Category result = category.updateInternal(categoryToSave);
			return categoryRepository.save(result);
		} else {
			return categoryRepository.save(categoryToSave);
		}
	}
}
