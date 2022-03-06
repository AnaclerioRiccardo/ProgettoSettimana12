package it.catalogo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.catalogo.model.Autore;
import it.catalogo.model.Categoria;
import it.catalogo.model.Libro;
import it.catalogo.repository.AutoreRepository;
import it.catalogo.repository.CategoriaRepository;
import it.catalogo.repository.LibroRepository;

@Service
public class AutoreService {

	@Autowired
	private AutoreRepository autoreRepository;
	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;

	
	public List<Autore> findByNomeAndCognome(String nome, String cognome){
		return autoreRepository.findByNomeAndCognome(nome, cognome);
	}
	
	public Autore save(Autore autore) {
		List<Libro> libri = new ArrayList<>();
		//se il libro esiste gia' prendo quello, altrimenti lo creo (cascade)
		for(Libro l: autore.getLibri()) {
			if(libroRepository.findByTitolo(l.getTitolo()).isPresent()) {
				libri.add(libroRepository.findByTitolo(l.getTitolo()).get());
			} else {
				//sono nel caso in cui il libro non esiste gia'
				//faccio la stessa cosa per la categoria
				//controlle se esiste gia' ed in quel caso prendo quella altrimenti la creo
				List<Categoria> categorie = new ArrayList<>();
				for(Categoria c: l.getCategorie()) {
					if(categoriaRepository.findByNome(c.getNome()).isPresent()) {
						categorie.add(categoriaRepository.findByNome(c.getNome()).get());
					} else {
						categorie.add(c);
					}
				}
				libri.add(l);
			}
		}
		autore.setLibri(libri);
		return autoreRepository.save(autore);
	}
	
	public Autore update(Autore autore, Long id) {
		Autore a = autoreRepository.findById(id).get();
		a.setNome(autore.getNome());
		a.setCognome(autore.getCognome());
		return autoreRepository.save(a);
		
	}
	
	public Optional<Autore> findById(Long id) {
		return autoreRepository.findById(id);
	}
	
	public List<Autore> findAll(){
		return autoreRepository.findAll();
	}
	
	public void delete(Long id) {
		autoreRepository.deleteById(id);
	}
	
}
