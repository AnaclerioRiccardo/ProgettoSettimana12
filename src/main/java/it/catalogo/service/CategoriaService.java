package it.catalogo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.catalogo.model.Categoria;
import it.catalogo.model.Libro;
import it.catalogo.repository.CategoriaRepository;
import it.catalogo.repository.LibroRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private LibroRepository libroRepository;

	public Optional<Categoria> findById(Long id) {
		return categoriaRepository.findById(id);
	}
	
	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public Optional<Categoria> findByNome(String nome) {
		return categoriaRepository.findByNome(nome);
	}
	
	public void delete(Long id) {
		List<Libro> libri = libroRepository.findAll();
		Categoria categoria = findById(id).get();
		for(Libro l: libri) {
			List<Categoria> categorie = new ArrayList<>();
			for(Categoria c: l.getCategorie()) {
				if(!c.getNome().equals(categoria.getNome())) {
					categorie.add(c);
				}
			}
			l.setCategorie(categorie);
		}
		categoriaRepository.deleteById(id);
	}
	
	public Categoria update(Categoria categoria, Long id) {
		Categoria categoriaUp = findById(id).get();
		categoriaUp.setNome(categoria.getNome());
		categoriaRepository.save(categoriaUp);
		return categoriaUp;
	}
	
	public List<Categoria> findAll(){
		return categoriaRepository.findAll();
	}
}
