package cm.gasmyr.it.app.aam.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import cm.gasmyr.it.app.music.MegaMusicApplication;
import cm.gasmyr.it.app.music.core.Category;
import cm.gasmyr.it.app.music.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MegaMusicApplication.class })
@SpringBootTest
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository objectToTest;

	@Test
	public void addTest() {
		int count = ((List<Category>) objectToTest.findAll()).size();

		Category category = Category.builder().withDescription("descriptiion").named("Category for test").build();
		objectToTest.save(category);

		List<Category> categories = (List<Category>) objectToTest.findAll();
		Assert.assertEquals(count, categories.size() - 1);
	}

	@Test
	public void listTest() {

		List<Category> categories = (List<Category>) objectToTest.findAll();

		Assert.assertNotNull(categories);
		Assert.assertTrue(categories.size() > 4);
	}

	@Test
	public void deleteTest() {
		Category categoryToBeDeleted = Category.builder().withDescription("descriptiion for deletion")
				.named("Category for deletion").build();
		categoryToBeDeleted=objectToTest.save(categoryToBeDeleted);

		objectToTest.delete(categoryToBeDeleted);

		
		List<Category> categories = (List<Category>) objectToTest.findAll();
		Assert.assertTrue(!categories.contains(categoryToBeDeleted));
	}
	
	@Test
	public void updateTest() {
		String newDescription="i'm new here";
		Category categoryToBeUpdated = Category.builder().withDescription("description for update")
				.named("Category for update").build();
		categoryToBeUpdated=objectToTest.save(categoryToBeUpdated);
		Category result=objectToTest.findOne(categoryToBeUpdated.getId());
		Assert.assertNotNull(result);
		result=Category.builder(result).withDescription(newDescription).build();
		objectToTest.save(result);

		Category updatedCategory=objectToTest.findOne(categoryToBeUpdated.getId());
		
		Assert.assertEquals(updatedCategory.getId(), categoryToBeUpdated.getId());
		Assert.assertEquals(updatedCategory.getDescription(), newDescription);
	}

}
