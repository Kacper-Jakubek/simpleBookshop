package pl.sdacademy.bookstore.DTO;

public class ShoppingCart {
    private String bookName;
    private int howMany;
    private double bookPrice;

    public ShoppingCart() {
    }

    public ShoppingCart(String bookName, int howMany, double bookPrice){
        this.bookName = bookName;
        this.howMany = howMany;
        this.bookPrice = bookPrice;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public int getHowMany() {
        return howMany;
    }
    public void setHowMany(int howMany) {
        this.howMany = howMany;
    }
    public double getBookPrice() {
        return bookPrice;
    }
    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }
}
