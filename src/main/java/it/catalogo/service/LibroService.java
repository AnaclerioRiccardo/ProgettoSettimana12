package it.catalogo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.catalogo.exception.SegreteriaException;
import it.catalogo.model.Autore;
import it.catalogo.model.Categoria;
import it.catalogo.model.Libro;
import it.catalogo.repository.AutoreRepository;
import it.catalogo.repository.CategoriaRepository;
import it.catalogo.repository.LibroRepository;

@Service
public class LibroService {
	
	@Autowired
	private AutoreRepository autoreRepository;
	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Optional<Libro> findByTitolo(String titolo){
		return libroRepository.findByTitolo(titolo);
	}
	
	public Libro save(Libro libro) {
		Libro l = new Libro();
		l.setAnnoPubblicazione(libro.getAnnoPubblicazione());
		l.setPrezzo(libro.getPrezzo());
		l.setTitolo(libro.getTitolo());
		List<Categoria> categorie = new ArrayList<>();
		for(Categoria c: libro.getCategorie()) {
			if(categoriaRepository.findByNome(c.getNome()).isPresent()) {
				categorie.add(categoriaRepository.findByNome(c.getNome()).get());
			} else {
				categorie.add(c);
			}
		}
		l.setCategorie(categorie);
		List<Autore> autori = new ArrayList<>();
		for(Autore a: libro.getAutori()) {
			if(autoreRepository.findByNomeAndCognome(a.getNome(), a.getCognome()).isEmpty()) {
				autori.add(a);
			} else {
				autori.add(autoreRepository.findByNomeAndCognome(a.getNome(), a.getCognome()).get(0));
			}
		}
		l.setAutori(autori);
		return libroRepository.save(l);
	}

	public Optional<Libro> findById(Long id) {
		return libroRepository.findById(id);
	}

	public void delete(Long id) {
		libroRepository.deleteById(id);	
	}

	public List<Libro> findAll() {
		return libroRepository.findAll();
	}

	public Libro update(Libro libro, Long id) {
		Libro l = libroRepository.findById(id).get();
		l.setTitolo(libro.getTitolo());
		l.setAnnoPubblicazione(libro.getAnnoPubblicazione());
		l.setPrezzo(libro.getPrezzo());
		List<Categoria> categorie = new ArrayList<>();
		for(Categoria c: libro.getCategorie()) {
			if(categoriaRepository.findByNome(c.getNome()).isPresent()) {
				categorie.add(categoriaRepository.findByNome(c.getNome()).get());
			} else {
				throw new SegreteriaException("La categoria "+c.getNome()+" non esiste");
			}
		}
		l.setCategorie(categorie);
		List<Autore> autori = new ArrayList<>();
		for(Autore a: libro.getAutori()) {
			if(autoreRepository.findByNomeAndCognome(a.getNome(), a.getCognome()).isEmpty()) {
				throw new SegreteriaException("L'Autore "+a.getNome()+" "+a.getCognome()+" non esiste");
			} else {
				autori.add(autoreRepository.findByNomeAndCognome(a.getNome(), a.getCognome()).get(0));
			}
		}
		l.setAutori(autori);
		return l;
	}

	public List<Libro> findAllByAutori(Set<Long> autori) {
		List<Libro> libriByAutori = new ArrayList<>();
		List<Autore> aut = autoreRepository.findAllById(autori);
		if(aut.isEmpty())	//se e' vuota significa che quegli autori non hanno scritto nemmeno un libro
			return libriByAutori;
		for(Libro l: findAll()) {
			for(Autore a:l.getAutori()) {
				if(aut.contains(a)) {
					libriByAutori.add(l);
				}
			}
		}
		return libriByAutori;
	}

	public List<Libro> findAllByCategorie(Set<Long> categorie) {
		List<Libro> libriByCategorie = new ArrayList<>();
		List<Categoria> cat = categoriaRepository.findAllById(categorie);
		if(cat.isEmpty())	//se e' vuota significa che non esistono libri di quelle categorie
			return libriByCategorie;
		for(Libro l: findAll()) {
			for(Categoria c:l.getCategorie()) {
				if(cat.contains(c)) {
					libriByCategorie.add(l);
				}
			}
		}
		return libriByCategorie;
	}
	

}
