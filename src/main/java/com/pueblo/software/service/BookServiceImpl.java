package com.pueblo.software.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pueblo.software.model.Book;
import com.pueblo.software.repository.BookRepository;

@Service("bookService")
public class BookServiceImpl implements BookService{

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	BookRepository rep;
	
	
	@Override
	public void addBook(String name) {
		Book book = new Book();
		book.setName(name);
		rep.save(book);
		System.out.println("Book saved !");
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBooksByName(String name) {
		Query q = entityManager.createQuery("SELECT B FROM " +Book.class.getSimpleName()+ " B WHERE B." +Book.NAME + "=:name");
		q.setParameter("name", name);
		return q.getResultList();
	}

}
