package items;

import java.util.Objects;

public class Book extends LendableItem{
    protected Author author;

    public Book()
    {
        super();
        author = null;
    }
    public Book(String name, String genre, int total, Author author)
    {
        super(name, genre, total);
        this.author = author;
    }
    // constructor for data from db
    public Book(int id, String name, String genre, int total, int available, Author author)
    {
        super(id, name, genre, total, available);
        this.author = author;
    }

    // getters
    public Author getAuthor() { return this.author; }

    // setters
    public void setAuthor(Author author) { this.author = author; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author);
    }

    @Override
    public String toString() {
        return "Book\n" +
                "Title = " + name + '\n' +
                "Author = " + author + '\n' +
                "Genre = " + genre + '\n';
    }
}
