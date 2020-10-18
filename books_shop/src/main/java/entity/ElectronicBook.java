package entity;

public class E_book extends Book{
    private String downloadFormat;

    public E_book(int id, String name, double price, String genre, String language, String author, int bookbinding, int ISBN, String publisher, String downloadFormat) {
        super(id, name, price, genre, language, author, bookbinding, ISBN, publisher);
        this.downloadFormat = downloadFormat;
    }

    public String getDownloadFormat() {
        return downloadFormat;
    }

    public void setDownloadFormat(String downloadFormat) {
        this.downloadFormat = downloadFormat;
    }

    @Override
    public String toString() {
        return "E_book{" +
                "downloadFormat='" + downloadFormat + '\'' +
                '}';
    }
}
