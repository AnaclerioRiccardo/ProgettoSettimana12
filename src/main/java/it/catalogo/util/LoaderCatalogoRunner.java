package it.catalogo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.catalogo.model.Autore;
import it.catalogo.model.Categoria;
import it.catalogo.model.Libro;
import it.catalogo.repository.LibroRepository;

@Component
public class LoaderCatalogoRunner implements CommandLineRunner {
	
	@Autowired
	private LibroRepository libroRepository;

	@Override
	public void run(String... args) throws Exception {
		//Creo le categorie
		Categoria c1 = new Categoria();
		c1.setNome("Horror");
		Categoria c2 = new Categoria();
		c2.setNome("Fantasy");
		Categoria c3 = new Categoria();
		c3.setNome("Giallo");
		
		//Creo gli autori
		Autore a1 = new Autore();
		a1.setNome("Stephen");
		a1.setCognome("King");
		Autore a2 = new Autore();
		a2.setNome("J.R.R.");
		a2.setCognome("Tolkien");
		Autore a3 = new Autore();
		a3.setNome("Agatha");
		a3.setCognome("Christie");
		
		//Creo i libri
		Libro l1 = new Libro();
		l1.setTitolo("IT");
		l1.setPrezzo(30.5);
		l1.setAnnoPubblicazione(1986);
		l1.aggiungiCategoria(c1);
		l1.aggiungiAutore(a1);
		libroRepository.save(l1);
		
		Libro l2 = new Libro();
		l2.setTitolo("Il signore degli anelli");
		l2.setPrezzo(45.0);
		l2.setAnnoPubblicazione(1955);
		l2.aggiungiCategoria(c2);
		l2.aggiungiAutore(a2);
		libroRepository.save(l2);
		
		Libro l3 = new Libro();
		l3.setTitolo("Assassionio sull'Orient Express");
		l3.setPrezzo(25.99);
		l3.setAnnoPubblicazione(1934);
		l3.aggiungiCategoria(c3);
		l3.aggiungiAutore(a3);
		libroRepository.save(l3);
	}

}
