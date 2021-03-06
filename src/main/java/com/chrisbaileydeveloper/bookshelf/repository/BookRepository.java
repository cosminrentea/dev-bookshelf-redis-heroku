package com.chrisbaileydeveloper.bookshelf.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.chrisbaileydeveloper.bookshelf.domain.Book;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Redis repository for the Book entity.
 */
@Repository
public class BookRepository {

    @Inject
    private RedisTemplate<String, Book> redisTemplate;

    public void save(Book book) {
        redisTemplate.opsForValue().set(book.getId(), book);
    }

    public Book findById(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        Set<String> keys = redisTemplate.keys("*");

        for (String key : keys) {
            books.add(findById(key));
        }

        return books;
    }

    public void delete(Book b) {
        String key = b.getId();
        redisTemplate.opsForValue().getOperations().delete(key);
    }


    public void deleteAll() {
        Set<String> keys = redisTemplate.keys("*");

        for (String key : keys) {
            Book b = new Book(key);
            delete(b);
        }
    }
}
