package com.xiongyf.jwtdemo.system.repository;


import com.xiongyf.jwtdemo.system.pojo.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookById(Long id);

}
