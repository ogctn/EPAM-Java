package com.epam.rd.autotasks.collections.map;

import java.util.*;

public class BooksCatalog {
    private static final String EOL = "\n";
    private Map<Author, List<Book>> catalog;

    public BooksCatalog() {
        this.catalog = new TreeMap<>();
    }

    public BooksCatalog(Map<Author, List<Book>> catalog) {
        if (catalog == null)
            throw (new NullPointerException());
        this.catalog = new TreeMap<Author, List<Book>>(catalog);
    }

    /**
     * Returns a List of books of the specified author.
     *
     * @param author the author of books to search for.
     * @return a list of books or {@code null}
     * if there is no such author in the catalog.
     */
    public List<Book> findByAuthor(Author author) {
        if (author == null)
            throw (new NullPointerException());
        List<Book> books =  catalog.get(author);
        if (books == null)
            return (null);
        return (new ArrayList<Book>(books));
    }

    /**
     * @return the string representation of all authors
     * separated by the current operating system {@code lineSeparator}.
     */
    public String getAllAuthors() {
        StringBuilder sb = new StringBuilder();
        for (Author a : catalog.keySet()) {
            if (!sb.isEmpty())
                sb.append(EOL);
            sb.append(a.toString());
        }
        return (sb.toString());
    }

    /**
     * Searches for pairs of (author, book) by the book title.
     * The pair must be included in the resulting map if the
     * book title contains the specified string matched ignore case.
     * All authors of the book must be specified in the
     * book authors list.
     *
     * @param pattern the string to search for in the book title.
     * @return the map which contains all found books and their authors.
     * It must be sorted by titles of books, if the titles match,
     * by increasing cost.
     */
    public Map<Book, List<Author>> findAuthorsByBookTitle(String pattern) {
        if (pattern == null)
            throw (new NullPointerException());

        TreeMap<Book, List<Author>> res = new TreeMap<Book, List<Author>>();
        for (Map.Entry<Author, List<Book>> entry : catalog.entrySet()) {
            Author author = entry.getKey();
            List<Book> books = entry.getValue();
            for (Book book : books) {
                if (book.getTitle().toLowerCase().contains(pattern.toLowerCase())) {
                    List<Author> authors = res.get(book);
                    if (authors == null) {
                        authors = new ArrayList<Author>();
                        res.put(book, authors);
                    }
                    authors.add(author);
                }
            }
        }
        return (res);
    }

    /**
     * Searches for all books whose genre list contains the specified string.
     * The book must be included in the resulting list if at least
     * one genre of the book contains the specified pattern ignoring case.
     *
     * @param pattern the string to search for in the book genre list.
     * @return a set of books sorted using natural ordering.
     * @see Book class.
     */
    public Set<Book> findBooksByGenre(String pattern) {
        if (pattern == null)
            throw (new NullPointerException());

        TreeSet<Book> res = new TreeSet<Book>();
        for (List<Book> books : catalog.values()) {
            for (Book book : books) {
                for (String genre : book.getGenres()) {
                    if (genre.toLowerCase().contains(pattern.toLowerCase())) {
                        res.add(book);
                        break;
                    }
                }
            }
        }
        if (res.isEmpty())
            return (null);
        return (res);
    }

    /**
     * Searches for authors of the specified book.
     *
     * @param book the book.
     * @return a list of authors of the specified book.
     * @throws NullPointerException if the parameter is {@code null}
     */
    public List<Author> findAuthorsByBook(Book book) {
        if (book == null)
            throw (new NullPointerException());

        List<Author> res = new ArrayList<Author>();
        for (Map.Entry<Author, List<Book>> entry : catalog.entrySet()) {
            if (entry.getValue().contains(book))
                res.add(entry.getKey());
        }
        return (res);
    }

    @Override
    public String toString() {
        return (catalog.toString());
    }
}
