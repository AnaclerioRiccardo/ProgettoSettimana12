package it.catalogo.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import it.catalogo.model.Libro;
import it.catalogo.service.LibroService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class LibroController {

	@Autowired
	private LibroService libroService;
	
	@PostMapping("/libro")
	@Operation(summary = "Inserire un nuovo Libro", description = "cancellare dal JSON la lista di libri, cancellare tutti gli id, "
			+ "per gli autori, se la coppia di valori nome e cognome e' gia' presente prende l'autore salvato nel database, altrimenti lo crea nuovo; "
			+ "per le categorie, se il nome e' gia' presente prende la categoria salvata nel db, altrimenti la crea nuova.")
	@ApiResponse(responseCode = "200", description = "Libro inserito")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Libro> save(@RequestBody Libro libro){
		if(libroService.findByTitolo(libro.getTitolo()).isPresent()) {
			throw new SegreteriaException("Il titolo del libro e' gia' presente");
		}
		Libro save = libroService.save(libro);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}
	
	@DeleteMapping("/libro/{id}")
	@Operation(summary = "Cancella un Libro tramite l'id")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id){
		if(!libroService.findById(id).isPresent()) {
			throw new SegreteriaException("id Libro non presente");
		}
		libroService.delete(id);
		return new ResponseEntity<>("Libro eliminato correttamente", HttpStatus.OK);
	}
	
	@PutMapping("/libro/{id}")
	@Operation(summary = "Modifica un Libro tramite l'id", description = "cancellare dal JSON la lista di libri, cancellare tutti gli id, "
			+ "la coppia nome e cognome dell'Autore e il nome della Categoria devono esistere")
	@ApiResponse(responseCode = "200", description = "Libro aggiornato")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Libro> update(@PathVariable Long id, @RequestBody Libro libro){
		if(!libroService.findById(id).isPresent()) {
			throw new SegreteriaException("id Autore non presente");
		}
		Libro update =libroService.update(libro, id);
		return new ResponseEntity<>(update, HttpStatus.OK);
	}
	
	@GetMapping("/libro")
	@Operation(summary = "Restituisce la lista di tutti i Libri")
	@ApiResponse(responseCode = "200", description = "Lista di tutti i Libri")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<List<Libro>> findAll (){
		List<Libro> libri = libroService.findAll();
		if (!libri.isEmpty()) {
			return new ResponseEntity<>(libri, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/libro/{id}")
	@Operation(summary = "Cerca un Libro tramite l'id")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Libro> findById(@PathVariable Long id){
		Optional<Libro> libro = libroService.findById(id);
		if (libro.isPresent()) {
			return new ResponseEntity<>(libro.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/libro/listaByAutori/{autori}")
	@Operation(summary = "Restituisce la lista di tutti i Libri scritti dagli autori passati in input")
	@ApiResponse(responseCode = "200", description = "Lista di tutti i Libri scritta dagli autori passati in input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<List<Libro>> findAllByAutori (@PathVariable Set<Long> autori){
		List<Libro> libri = libroService.findAllByAutori(autori);
		if (!libri.isEmpty()) {
			return new ResponseEntity<>(libri, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/libro/listaByCategoria/{categorie}")
	@Operation(summary = "Restituisce la lista di tutti i Libri delle categorie passate come input")
	@ApiResponse(responseCode = "200", description = "Lista di tutti i Libri delle categorie passate come input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<List<Libro>> findAllByCategorie (@PathVariable Set<Long> categorie){
		List<Libro> libri = libroService.findAllByCategorie(categorie);
		if (!libri.isEmpty()) {
			return new ResponseEntity<>(libri, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	

}
