package it.catalogo.testweb;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class TestAutenticazione {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithAnonymousUser
	final void getAllAutoryWhenUserAnonymous() throws Exception {
		this.mockMvc.perform(get("/api/autore")).andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithAnonymousUser
	final void testLoginPubblica() throws Exception {
		this.mockMvc.perform(get("/auth/login")).andExpect(status().isMethodNotAllowed());
	}

}
