package pl.coderslab.book;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> findAll() {
        return entityManager
                .createQuery("select baaa from Book baaa", Book.class)
                .getResultList();
    }

    public List<Book> findAllByRating(int rating) {
        return entityManager
                .createQuery("select b from Book b where b.rating = :myRating", Book.class)
                .setParameter("myRating", rating)
                .getResultList();
    }

    public void saveBook(Book book) {
        entityManager.persist(book);
    }

    public Book findById(long id) {
        return entityManager.find(Book.class, id);
    }

    public void update(Book book) {
        entityManager.merge(book);
    }

    public void delete(long id) {
        entityManager.remove(entityManager.find(Book.class, id));
    }


    public Book findByTitle(String title) {
        return entityManager.createQuery("SELECT b FROM Book b WHERE b.title = :t", Book.class)
                .setParameter("t", title)
                .getSingleResult();
    }
}