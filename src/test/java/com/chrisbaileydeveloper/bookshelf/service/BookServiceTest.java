package com.chrisbaileydeveloper.bookshelf.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import com.chrisbaileydeveloper.bookshelf.domain.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class for the BookService.
 *
 * @see BookService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BookServiceTest {
	private static final String EXPECTED_NAME = "Effective Java";
    private static final String EXPECTED_PUBLISHER = "Addison-Wesley";
    private static final int DEFAULT_COUNT = 2;
    private int totalBooks;
    private Book testBook = new Book("13", "Name test", "Publisher test", null, "Description test", "iVBORw0KGgoA");
	
    @Inject
    private BookService bookService;
	
    @Before
	public void setup() {
    	// Initialize test database.
    	bookService.restoreDefaultBooks();
    	
    	// Initialize 'totalBooks' counter.
		totalBooks = bookService.findAll().size();
	}
    
    @Test
	public void testFindAll() {
    	assertThat(bookService.findAll()).hasSize(totalBooks);
	}

	@Test
	public void testFindById() {
		// Book with id="1" is Effective Java by Addison-Wesley
        assertThat(bookService.findById("1").getName()).isEqualTo(EXPECTED_NAME);
        assertThat(bookService.findById("1").getPublisher()).isEqualTo(EXPECTED_PUBLISHER);
	}

	@Test
	public void testSave() {
		bookService.save(testBook);
		assertThat(bookService.findAll()).hasSize(totalBooks + 1);
	}

	@Test
	public void testDelete() {
		Book book = bookService.findById("1");
		bookService.delete(book);
		assertThat(bookService.findAll()).hasSize(totalBooks - 1);
	}

	@Test
	public void testDeleteAll() {
		bookService.deleteAll();
		assertThat(bookService.findAll()).isEmpty();
	}

	@Test
	public void testRestoreDefaultBooks() {
		// Add one book, then reset database.
		bookService.save(testBook);
		
		bookService.restoreDefaultBooks();
		assertThat(bookService.findAll()).hasSize(DEFAULT_COUNT);
	}
}
