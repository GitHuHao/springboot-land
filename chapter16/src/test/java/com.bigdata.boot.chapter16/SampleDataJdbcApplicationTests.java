package com.bigdata.boot.chapter16;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for {@link SampleDataJdbcApplication}.
 *
 * @author Andy Wilkinson
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleDataJdbcApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testCustomers() throws Exception {
		// 不需要考虑 context-path
		this.mvc.perform(get("/customer/like").param("name", "merEDith")).andExpect(status().isOk())
				.andExpect(content().string(containsString("Meredith")));
	}

}