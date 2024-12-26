package AmitGarfel_KamilSason_DanSwirsky;

import java.time.LocalDate;

public class Order {
    private Product[] products;
    private int productCounter;
    private double totalPrice;
    private final LocalDate buyDate;

    public Order(Product[] products, LocalDate buyDate) {
        this.products = new Product[products.length];
        this.productCounter = 0;
        this.totalPrice = 0;
        this.buyDate = buyDate;
        for (Product product : products) {
            addProduct(product);
        }
    }

    public void addProduct(Product product) {
        if (productCounter == products.length) {
            resizeProducts();
        }
        products[productCounter++] = product;
        totalPrice += product.getProductPrice();
    }

    private void resizeProducts() {
        Product[] newProducts = new Product[products.length * 2];
        System.arraycopy(products, 0, newProducts, 0, products.length);
        products = newProducts;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getBuyDate() {
        return buyDate;
    }

    @Override
    public String toString() {
        String result = "Order date: " + buyDate + "\nTotal price: " + String.format("%.2f", totalPrice) + "\nProducts:";
        for (int i = 0; i < productCounter; i++) {
            result += "\n\t- " + products[i].toString();
        }
        return result;
    }

    public Product[] getProducts() {
        Product[] result = new Product[productCounter];
        System.arraycopy(products, 0, result, 0, productCounter);
        return result;
    }
}
