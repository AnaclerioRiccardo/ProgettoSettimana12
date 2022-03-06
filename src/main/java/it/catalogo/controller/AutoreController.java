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
import it.catalogo.model.Autore;
import it.catalogo.service.AutoreService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class AutoreController {
	
	@Autowired
	private AutoreService autoreService;
	
	/*
	crea nuovo autore, nuovo libro e nuova categoria
	levare autore
	levare tutti gli id
	se il titolo del libro e' gia' presente prende il libro salvato nel database altrimenti lo crea nuovo,
	nel caso in cui il libro viene creato, faccio la stessa cosa per la categoria ovvero
	se il nome della categoria esiste gia' prendo quella altrimenti ne crea una nuova.
	*/
	@PostMapping("/autore")
	@Operation(summary = "Aggiunge un Autore", description = "cancellare dal JSON la lista di autori, cancellare tutti gli id, "
			+ "per i libri se il titolo e' gia' presente prende il libro salvato nel database altrimenti lo crea nuovo, "
			+ "nel caso in cui il libro lo crea fa la stessa cosa per la categoria")
	@ApiResponse(responseCode = "200", description = "Autore inserito")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Autore> save(@RequestBody Autore autore){
		if(!autoreService.findByNomeAndCognome(autore.getNome(), autore.getCognome()).isEmpty()) {
			throw new SegreteriaException("Autore gia' presente con quella coppia di nome e cognome");
		}
		Autore save = autoreService.save(autore);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}
	
	@DeleteMapping("/autore/{id}")
	@Operation(summary = "Cancella un Autore tramite l'id")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id){
		if(!autoreService.findById(id).isPresent()) {
			throw new SegreteriaException("id Autore non presente");
		}
		autoreService.delete(id);
		return new ResponseEntity<>("Autore eliminato correttamente", HttpStatus.OK);
	}
	
	@PutMapping("/autore/{id}")
	@Operation(summary = "Modifica un Autore tramite l'id", description = "Ho assunto che qui si possa modificare solamente le informazioni relative all'autore, "
			+ "quindi nel JSON cancellare tutta la parte relativa ai libri e alla categoria")
	@ApiResponse(responseCode = "200", description = "Autore aggiornato")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Autore> update(@PathVariable Long id, @RequestBody Autore autore){
		if(!autoreService.findById(id).isPresent()) {
			throw new SegreteriaException("id Autore non presente");
		}
		Autore update =autoreService.update(autore, id);
		return new ResponseEntity<>(update, HttpStatus.OK);
	}
	
	@GetMapping("/autore")
	@Operation(summary = "Restituisce la lista di tutti gli autori")
	@ApiResponse(responseCode = "200", description = "Lista di tutti gli autori")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<List<Autore>> findAll (){
		List<Autore> autori = autoreService.findAll();
		if (!autori.isEmpty()) {
			return new ResponseEntity<>(autori, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/autore/{id}")
	@Operation(summary = "Cerca un Autore tramite l'id")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Autore> findById(@PathVariable Long id){
		Optional<Autore> autore = autoreService.findById(id);
		if (autore.isPresent()) {
			return new ResponseEntity<>(autore.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
}
