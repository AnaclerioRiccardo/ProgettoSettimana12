package it.catalogo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.catalogo.model.Categoria;
import it.catalogo.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

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
