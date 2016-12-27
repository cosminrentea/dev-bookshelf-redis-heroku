package com.chrisbaileydeveloper.bookshelf.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import com.chrisbaileydeveloper.bookshelf.repository.BookRepository;
import com.chrisbaileydeveloper.bookshelf.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BookControllerTest {
	private MockMvc mockMvc;

	@Inject
	private BookRepository bookRepository;

	@Before
	public void setup() {
		BookService bookService = new BookService();
		ReflectionTestUtils.setField(bookService, "bookRepository",	bookRepository);

		BookController bookController = new BookController();
		ReflectionTestUtils.setField(bookController, "bookService", bookService);

		this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}

	@Test
	public void testList() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("books/list"))
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("books"))
			.andExpect(model().attribute("books", hasItem(
                        allOf(
                        	hasProperty("id", is("1")),
                            hasProperty("name", is("Effective Java")),
                            hasProperty("publisher", is("Addison-Wesley"))
                        )
                )))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                            hasProperty("id", is("2")),
                            hasProperty("name", is("Design Patterns:  Elements of Reusable Object-Oriented Software")),
                            hasProperty("publisher", is("Addison-Wesley Professional"))
                        )
                )));
	}

	@Test
	public void testShow() throws Exception {
		mockMvc.perform(get("/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("books/show"))
			.andExpect(model().size(1))
			.andExpect(model().attribute("book", 
					allOf(
						hasProperty("id", is("1")),
						hasProperty("name", is("Effective Java")),
                        hasProperty("publisher", is("Addison-Wesley"))
					)
			));
	}

	@Test
	public void testUpdateForm() throws Exception {
		mockMvc.perform(get("/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("book"))
			.andExpect(model().attribute("book", 
					allOf(
						hasProperty("id", is("1")),
						hasProperty("name", is("Effective Java")),
                        hasProperty("publisher", is("Addison-Wesley"))
					)
			));
	}
	
	@Test
	public void testCreateForm() throws Exception {
		mockMvc.perform(get("/create"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("books/create"))
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("book"))
			.andExpect(model().attribute("book", 
					allOf(
						hasProperty("name", equalTo(null)),
                        hasProperty("publisher", equalTo(null))
					)
			));;
	}
	
	@Test
	public void testDownloadPhoto() throws Exception {
		mockMvc.perform(get("/photo/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE))
				.andReturn();
	}
}
