package pl.coderslab.book;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;


@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;
    private final PublisherDao publisherDao;
    private final BookRepository bookRepository;

    public BookController(BookDao bookDao, PublisherDao publisherDao, BookRepository bookRepository) {
        this.bookDao = bookDao;
        this.publisherDao = publisherDao;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/list-repo")
    @ResponseBody
    public String getListRepo() {

        return bookRepository.findAll()
                .stream()
                .map(b -> b.getTitle().concat(" - ").concat(b.getId().toString()))
                .collect(Collectors.joining(", "));
    }

    @GetMapping("/list")
    @ResponseBody
    public String getList() {
        return bookDao.findAll()
                .stream()
                .map(b -> b.getTitle().concat(" - ").concat(b.getId().toString()))
                .collect(Collectors.joining(", "));
    }

    @GetMapping("/list-rating/{rating}")
    @ResponseBody
    public String getList(@PathVariable int rating) {
        return bookDao.findAllByRating(rating)
                .stream()
                .map(b -> b.getTitle().concat(" - ")
                        .concat(b.getId().toString())
                        .concat(" - ").concat(b.getRating() + " ")
                )
                .collect(Collectors.joining(", "));
    }

    @RequestMapping("/add")
    @ResponseBody
    public String hello() {
        Publisher publisher = new Publisher();
        publisher.setName("Helion 2");
        publisherDao.savePublisher(publisher);
        Book book = new Book("Thinking in Java 2", 6, "Book from controller 2");
        book.setPublisher(publisher);
        bookDao.saveBook(book);
        return "Id dodanej książki to:" + book.getId();
    }

    @RequestMapping("/get/{id}")
    @ResponseBody
    public String getBook(@PathVariable long id) {
        Book book = bookDao.findById(id);
        return book.toString();
    }

    @RequestMapping("/update/{id}/{title}")
    @ResponseBody
    public String updateBook(@PathVariable long id, @PathVariable String title) {
        Book book = bookDao.findById(id);
        book.setTitle(title);
        bookDao.update(book);
        return book.toString();
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public String deleteBook(@PathVariable long id) {
        bookDao.delete(id);
        return "deleted";
    }
}