package AmitGarfel_KamilSason_DanSwirsky;

import java.time.LocalDate;

public class Buyer extends User {
    private final Address address;
    private Product[] cart;
    private Order[] orders;
    private int cartCount;
    private int orderCount;

    public Buyer(String username, String password, Address address) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.cart = new Product[10];
        this.orders = new Order[10];
        this.cartCount = 0;
        this.orderCount = 0;
    }

    public Address getAddress() {
        return address;
    }

    public Product[] getCart() {
        Product[] result = new Product[cartCount];
        System.arraycopy(cart, 0, result, 0, cartCount);
        return result;
    }

    public void addToCart(Product product) {
        if (cartCount == cart.length) {
            resizeCart();
        }
        cart[cartCount++] = product;
    }

    public void clearCart() {
        cart = new Product[10];
        cartCount = 0;
    }

    public void addToCart(Product[] products) {
        for (int i = 0; i < products.length; i++) {
            addToCart(products[i]);
        }
    }
    private void resizeCart() {
        Product[] newCart = new Product[cart.length * 2];
        System.arraycopy(cart, 0, newCart, 0, cart.length);
        cart = newCart;
    }
    public Order payForCart() {
        LocalDate purchaseDate = LocalDate.now();
        Product[] cartCopy = new Product[cartCount];
        System.arraycopy(cart, 0, cartCopy, 0, cartCount);
        Order order = new Order(cartCopy, purchaseDate);
        if (orderCount == orders.length) {
            resizeOrders();
        }
        orders[orderCount++] = order;
        clearCart();
        return order;
    }

    private void resizeOrders() {
        Order[] newOrders = new Order[orders.length * 2];
        System.arraycopy(orders, 0, newOrders, 0, orders.length);
        orders = newOrders;
    }

    public Order[] getOrders() {
        Order[] result = new Order[orderCount];
        System.arraycopy(orders, 0, result, 0, orderCount);
        return result;
    }

        @Override
        public String toString() {
            String result = "Username: " + username + "\nPassword: " + password + "\nAddress: " + address.toString();
            result += "\n\nCurrent Cart: ";
            if (cartCount == 0) {
                result += "The cart is empty";
            } else {
                for (int i = 0; i < cartCount; i++) {
                    result += "\n" + cart[i].toString();
                }
            }
            result += "\n\nOrder History: ";
            if (orderCount == 0) {
                result += "No history yet";
            } else {
                for (int i = 0; i < orderCount; i++) {
                    result += "\n" + orders[i].toString();
                }
            }
            return result;
        }
    }

