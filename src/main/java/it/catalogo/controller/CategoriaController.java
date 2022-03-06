package it.catalogo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.catalogo.exception.SegreteriaException;
import it.catalogo.model.Categoria;
import it.catalogo.service.CategoriaService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@PostMapping("/categoria")
	@Operation(summary = "Inserimento di una nuova Categoria")
	@ApiResponse(responseCode = "200", description = "Categoria inserita")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Categoria> save(@RequestBody Categoria categoria){
		if(categoriaService.findByNome(categoria.getNome()).isPresent()) {
			throw new SegreteriaException("Nome Categoria gia' presente");
		} else {
			Categoria save =categoriaService.save(categoria);
			return new ResponseEntity<>(save, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/categoria/{id}")
	@Operation(summary = "Cancella una categoria tramite l'id")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id){
		if(!categoriaService.findById(id).isPresent()) {
			throw new SegreteriaException("id Categoria non presente");
		}
		categoriaService.delete(id);
		return new ResponseEntity<>("Categoria eliminata correttamente", HttpStatus.OK);
	}
	
	@PutMapping("/categoria/{id}")
	@Operation(summary = "Modifica una Categoria tramite l'id")
	@ApiResponse(responseCode = "200", description = "Categoria aggiornata")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoria){
		if(categoriaService.findByNome(categoria.getNome()).isPresent()) {
			throw new SegreteriaException("Nome Categoria gia' presente");
		} else {
			Categoria update =categoriaService.update(categoria, id);
			return new ResponseEntity<>(update, HttpStatus.OK);
		}
	}
	
	@GetMapping("/categoria")
	@Operation(summary = "Restituisce la lista di tutte le categorie")
	@ApiResponse(responseCode = "200", description = "Lista di tutte le categorie")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<List<Categoria>> findAll (){
		List<Categoria> categorie = categoriaService.findAll();
		System.out.println(categorie);
		if (!categorie.isEmpty()) {
			return new ResponseEntity<>(categorie, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/categoria/{id}")
	@Operation(summary = "Cerca una Categoria tramite l'id")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Categoria> findById(@PathVariable Long id){
		Optional<Categoria> categoria = categoriaService.findById(id);
		if (categoria.isPresent()) {
			return new ResponseEntity<>(categoria.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	
}
