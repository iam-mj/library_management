package items;

import java.util.Objects;

public class AudioBook extends Book{
    private Format format;
    private int duration;

    public AudioBook()
    {
        super();
        format = Format.MP3;
        duration = 0;
    }
    public AudioBook(String name, String gender, int total, Author author, Format format,
              int duration)
    {
        super(name, gender, total, author);
        this.format = format;
        this.duration = duration;
    }

    // setters
    public void setFormat(Format format)
    {
        this.format = format;
    }
    public void setDuration(int duration)
    {
        this.duration = duration;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AudioBook audioBook = (AudioBook) o;
        return duration == audioBook.duration && format == audioBook.format;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), format, duration);
    }

    @Override
    public String toString() {
        return "Audio Book\n" +
                "Title = " + name + '\n' +
                "Author = " + author + '\n' +
                "Format = " + format + '\n' +
                "Duration = " + duration + '\n' +
                "Genre = " + genre + '\n';
    }
}
