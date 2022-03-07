package it.catalogo.testweb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.catalogo.model.Autore;
import it.catalogo.model.Categoria;
import it.catalogo.model.Libro;

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
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testDeleteById() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/api/libro/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPost() throws Exception {
		Categoria c1 = new Categoria();
		c1.setNome("categoria1");
		Autore a1 = new Autore();
		a1.setNome("nome1");
		a1.setCognome("cognome1");
		Libro l1 = new Libro();
		l1.setTitolo("titolo1");
		l1.setPrezzo(30.5);
		l1.setAnnoPubblicazione(1986);
		l1.aggiungiCategoria(c1);
		l1.aggiungiAutore(a1);
		
		ObjectMapper objectMapper = new ObjectMapper();
	    String json = objectMapper.writeValueAsString(l1);
	    
	    MvcResult result = mockMvc.perform(
	            post("/api/libro")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(json))
	            .andExpect(status().isOk())
	            .andExpect(content().json("{'titolo':'titolo1'}"))
	            .andExpect(content().json("{'prezzo': 30.5}"))
	            .andReturn();
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPut() throws Exception {
		Categoria c1 = new Categoria();
		c1.setNome("Horror");
		Autore a1 = new Autore();
		a1.setNome("Stephen");
		a1.setCognome("King");
		Libro l1 = new Libro();
		l1.setTitolo("titolo1");
		l1.setPrezzo(30.5);
		l1.setAnnoPubblicazione(1986);
		l1.aggiungiCategoria(c1);
		l1.aggiungiAutore(a1);
		
		ObjectMapper objectMapper = new ObjectMapper();
	    String json = objectMapper.writeValueAsString(l1);
	    
	    MvcResult result = mockMvc.perform(
	            put("/api/libro/1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(json))
	            .andExpect(status().isOk())
	            .andExpect(content().json("{'titolo':'titolo1'}"))
	            .andExpect(content().json("{'prezzo': 30.5}"))
	            .andReturn();
	}

}
