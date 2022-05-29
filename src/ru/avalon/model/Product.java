package ru.avalon.model;

import java.util.Objects;

public class Product {

    private String artNumber;
    private String productName;
    private String color;
    private int price;
    private int count;

    public Product(String artNumber, String productName, String color, int price, int count) {
        this.artNumber = artNumber;
        this.productName = productName;
        this.color = color;
        this.price = price;
        this.count = count;
    }

    public String getArtNumber() {
        return artNumber;
    }

    public void setArtNumber(String artNumber) {
        this.artNumber = artNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;

        if (price != product.price) return false;
        if (count != product.count) return false;
        if (!artNumber.equals(product.artNumber)) return false;
        if (!productName.equals(product.productName)) return false;
        return Objects.equals(color, product.color);
    }

    @Override
    public int hashCode() {
        int result = artNumber.hashCode();
        result = 31 * result + productName.hashCode();
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + count;
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "artNumber='" + artNumber + '\'' +
                ", productName='" + productName + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
