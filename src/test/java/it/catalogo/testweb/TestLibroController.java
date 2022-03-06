package it.catalogo.testweb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TestLibroController {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser
	final void testGetAll() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/libro")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testGetById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/libro/1")).andDo(print()).andExpect(status().isOk());	
	}

}