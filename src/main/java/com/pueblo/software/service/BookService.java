package com.pueblo.software.service;

import java.util.List;

import com.pueblo.software.model.Book;

public interface BookService {
 
	public void addBook(String name);
	
	public List<Book> getBooksByName(String name);
}
