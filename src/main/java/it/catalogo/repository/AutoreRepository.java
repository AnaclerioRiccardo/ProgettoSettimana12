package it.catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.catalogo.model.Autore;

public interface AutoreRepository extends JpaRepository<Autore, Long>{
	
	List<Autore> findByNomeAndCognome(String nome, String cognome);

}
