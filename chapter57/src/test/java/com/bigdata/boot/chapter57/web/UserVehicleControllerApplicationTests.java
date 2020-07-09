package com.bigdata.boot.chapter57.web;

import com.bigdata.boot.chapter57.domain.VehicleDetails;
import com.bigdata.boot.chapter57.runner.WelcomeCommandLineRunner;
import com.bigdata.boot.chapter57.service.UserVehicleService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@code @SpringBootTest} based tests for {@link UserVehicleController}.
 *
 * @author Phillip Webb
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserVehicleControllerApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ApplicationContext applicationContext;

	@MockBean
	private UserVehicleService userVehicleService;

	@Test
	public void getVehicleWhenRequestingTextShouldReturnMakeAndModel() throws Exception {
		given(this.userVehicleService.getVehicleDetails("sboot"))
				.willReturn(new VehicleDetails("Honda", "Civic"));
		this.mvc.perform(get("/sboot/vehicle").accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andExpect(content().string("Honda Civic"));
	}

	@Test
	public void welcomeCommandLineRunnerShouldBeAvailable() {
		// Since we're a @SpringBootTest all beans should be available.
		assertThat(this.applicationContext.getBean(WelcomeCommandLineRunner.class))
				.isNotNull();
	}

}