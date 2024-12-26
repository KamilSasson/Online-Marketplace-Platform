package AmitGarfel_KamilSason_DanSwirsky;

public class Seller extends User {
    private Product[] products;
    private int productCount;

    public Seller(String username, String password) {
        this.username = username;
        this.password = password;
        this.products = new Product[2];
        this.productCount = 0;
    }

    public Product[] getProducts() {
        Product[] result = new Product[productCount];
        System.arraycopy(products, 0, result, 0, productCount);
        return result;
    }

    public void addProductToSeller(Product product) {
        if (productCount == products.length) {
            resizeProducts();
        }
        products[productCount++] = product;
    }

    private void resizeProducts() {
        Product[] newProducts = new Product[products.length * 2];
        System.arraycopy(products, 0, newProducts, 0, products.length);
        products = newProducts;
    }

    public int getProductCount() {
        return productCount;
    }

    @Override
    public String toString() {
        String result = "Username: " + username + "\nPassword: " + password + "\nProducts: ";
        if (productCount == 0) {
            result += "No products yet";
        } else {
            for (int i = 0; i < productCount; i++) {
                result += "\n" + products[i].toString();
            }
        }
        return result;
    }
}
