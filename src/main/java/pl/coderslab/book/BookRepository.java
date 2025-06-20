package pl.coderslab.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

    List<Book> findAllByRating(int rating);

    List<Book> findAllByTitle(String title);
    List<Book> findByCategory(Category category);
    List<Book> findByCategoryId(Long categoryId);
}
