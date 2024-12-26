package AmitGarfel_KamilSason_DanSwirsky;

public class Manager {
    private Seller[] sellers;
    private Buyer[] buyers;
    private int sellerCount;
    private int buyerCount;

    public Manager() {
        sellers = new Seller[10];
        buyers = new Buyer[10];
        sellerCount = 0;
        buyerCount = 0;
    }

    public void addSeller(String username, String password) {
        if (sellerCount == sellers.length) {
            resizeSellers();
        }
        sellers[sellerCount++] = new Seller(username, password);
    }

    public void addBuyer(String username, String password, Address address) {
        if (buyerCount == buyers.length) {
            resizeBuyers();
        }
        buyers[buyerCount++] = new Buyer(username, password, address);
    }

    private void resizeSellers() {
        Seller[] newSellers = new Seller[sellers.length * 2];
        System.arraycopy(sellers, 0, newSellers, 0, sellers.length);
        sellers = newSellers;
    }

    private void resizeBuyers() {
        Buyer[] newBuyers = new Buyer[buyers.length * 2];
        System.arraycopy(buyers, 0, newBuyers, 0, buyers.length);
        buyers = newBuyers;
    }

    public boolean usernameTaken(String username) {
        for (int i = 0; i < sellerCount; i++) {
            if (sellers[i].getUsername().equals(username)) {
                return true;
            }
        }
        for (int i = 0; i < buyerCount; i++) {
            if (buyers[i].getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void addProductToSeller(int sellerIndex, String productName, double productPrice, ProductCategory productCategory, double specialPackingPrice) {
        Product newProduct = new Product(productName, productPrice, productCategory);
        if (specialPackingPrice > 0) {
            newProduct.setSpecialPackingPrice(specialPackingPrice);
        }
        sellers[sellerIndex].addProductToSeller(newProduct);
    }

    public void addProductToBuyer(int buyerIndex, int sellerIndex, int productIndex) {
        Buyer chosenBuyer = buyers[buyerIndex];
        Seller chosenSeller = sellers[sellerIndex];
        Product chosenProduct = chosenSeller.getProducts()[productIndex];
        chosenBuyer.addToCart(chosenProduct);
    }

    public void printAllSellers() {
        for (int i = 0; i < sellerCount; i++) {
            System.out.println((i + 1) + " --> " + sellers[i].getUsername());
        }
    }

    public void printAllBuyers() {
        for (int i = 0; i < buyerCount; i++) {
            System.out.println((i + 1) + " --> " + buyers[i].getUsername());
        }
    }

    public int getSellerCounter() {
        return sellerCount;
    }

    public int getBuyerCounter() {
        return buyerCount;
    }

    public Product[] getProductsOfSeller(int sellerIndex) {
        return sellers[sellerIndex].getProducts();
    }

    public int getSellerProductCounter(int sellerIndex) {
        return sellers[sellerIndex].getProductCount();
    }

    public boolean noProducts(int sellerIndex) {
        return sellers[sellerIndex].getProductCount() == 0;
    }

    public Order payForBuyerCart(int buyerIndex) {
        return buyers[buyerIndex].payForCart();
    }

//    public String getBuyerAddress(Buyer buyer) {
//        return buyer.getAddress().toString();
//    }
//
//    public Product[] getCartDetails(Buyer buyer) {
//        return buyer.getCart();
//    }
//
//    public Product[] getProductDetails(Seller seller) {
//        return seller.getProducts();
//    }

    public Buyer[] getBuyers() {
        return buyers;
    }

    public Seller[] getSellers() {
        return sellers;
    }

    public boolean isBuyerCartEmpty(int buyerIndex) {
        return buyers[buyerIndex].getCart().length == 0;
    }

    public Buyer[] getBuyersSortedByProducts() {
        Buyer[] sortedBuyers = new Buyer[buyerCount];
        System.arraycopy(buyers, 0, sortedBuyers, 0, buyerCount);
        for (int i = 0; i < sortedBuyers.length - 1; i++) {
            for (int j = 0; j < sortedBuyers.length - i - 1; j++) {
                if (sortedBuyers[j].getCart().length > sortedBuyers[j + 1].getCart().length) {
                    Buyer temp = sortedBuyers[j];
                    sortedBuyers[j] = sortedBuyers[j + 1];
                    sortedBuyers[j + 1] = temp;
                }
            }
        }
        return sortedBuyers;
    }

    public Seller[] getSellersSortedByName() {
        Seller[] sortedSellers = new Seller[sellerCount];
        System.arraycopy(sellers, 0, sortedSellers, 0, sellerCount);
        for (int i = 0; i < sortedSellers.length - 1; i++) {
            for (int j = 0; j < sortedSellers.length - i - 1; j++) {
                if (sortedSellers[j].getUsername().compareTo(sortedSellers[j + 1].getUsername()) > 0) {
                    Seller temp = sortedSellers[j];
                    sortedSellers[j] = sortedSellers[j + 1];
                    sortedSellers[j + 1] = temp;
                }
            }
        }
        return sortedSellers;
    }
}
