package AmitGarfel_KamilSason_DanSwirsky;

public class Product {
    private final String productName;
    private double productPrice;
    private final int productSerialNumber;
    private final ProductCategory productCategory;
    private double specialPackingPrice;
    private static int counter = 0;

    public Product(String productName, double productPrice, ProductCategory productCategory) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productSerialNumber = ++counter;
        this.specialPackingPrice = 0;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductSerialNumber() {
        return productSerialNumber;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setSpecialPackingPrice(double specialPackingPrice) {
        this.specialPackingPrice = specialPackingPrice;
        this.productPrice += specialPackingPrice;
    }

    @Override
    public String toString() {
        return productName + " - " + productPrice + " (Serial: " + productSerialNumber + ")";
    }
}
