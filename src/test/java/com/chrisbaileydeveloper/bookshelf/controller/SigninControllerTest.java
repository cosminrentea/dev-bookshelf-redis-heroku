package com.chrisbaileydeveloper.bookshelf.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SigninControllerTest {
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		SigninController signinController = new SigninController();
		this.mockMvc = MockMvcBuilders.standaloneSetup(signinController).build();
	}

	@Test
	public void testSignin() throws Exception {
		mockMvc.perform(get("/signin"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("signin/signin"));
	}
}
