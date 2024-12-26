package AmitGarfel_KamilSason_DanSwirsky;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Manager manager = new Manager();
        int choice = -1;
        System.out.println("Welcome to our electronic trade system!");
        do {
            printMenu();
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // clean buffer
                System.out.println();
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a number.\n");
                scanner.nextLine();  // clean buffer
                continue;
            }
            switch (choice) {
                case 0:
                    System.out.println("Thank you, goodbye!");
                    break;
                case 1:
                    addSeller(manager);
                    break;
                case 2:
                    addBuyer(manager);
                    break;
                case 3:
                    addProductToSeller(manager);
                    break;
                case 4:
                    addProductToBuyer(manager);
                    break;
                case 5:
                    makePayment(manager);
                    break;
                case 6:
                    if (emptyArray(manager.getBuyerCounter(), "buyer")) break;
                    printAllBuyersDetails(manager.getBuyersSortedByProducts());
                    break;
                case 7:
                    if (emptyArray(manager.getSellerCounter(), "seller")) break;
                    printAllSellersDetails(manager.getSellersSortedByName());
                    break;
                case 8:
                    ProductCategory category = chooseProductCategory();
                    if (category != null) {
                        printProductsByCategory(manager, category);
                    }
                    break;
                case 9:
                    if (emptyArray(manager.getBuyerCounter(), "buyer")) break;
                    replaceCartWithOrderHistory(manager);
                    break;
                default:
                    System.out.println("Invalid choice, please choose between 0 to 9 :\n");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("0 - Exit");
        System.out.println("1 - Add seller");
        System.out.println("2 - Add buyer");
        System.out.println("3 - Add product to seller");
        System.out.println("4 - Add product to buyer");
        System.out.println("5 - Payment for buyer");
        System.out.println("6 - Print details of all buyers");
        System.out.println("7 - Print details of all sellers");
        System.out.println("8 - Print all products from a specific category");
        System.out.println("9 - Create a new cart from order history");
        System.out.print("Enter your choice: ");
    }

    private static void addSeller(Manager manager) {
        while (true) {
            try {
                String sellerUsername = getNewUsername("seller", manager);
                String sellerPassword = getNewPassword();
                manager.addSeller(sellerUsername, sellerPassword);
                System.out.println("The seller has been added successfully.\n");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // clean buffer
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void addBuyer(Manager manager) {
        while (true) {
            try {
                String buyerUsername = getNewUsername("buyer", manager);
                String buyerPassword = getNewPassword();
                Address address = getAddress();
                manager.addBuyer(buyerUsername, buyerPassword, address);
                System.out.println("The buyer has been added successfully.\n");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // clean buffer
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void addProductToSeller(Manager manager) {
        while (true) {
            try {
                if (emptyArray(manager.getSellerCounter(), "seller")) {
                    return;
                }
                int sellerIndex = chooseSeller(manager);
                if (sellerIndex == -1) return;
                ProductCategory productCategory = chooseProductCategory();
                if (productCategory == null) return;
                scanner.nextLine(); // clean buffer
                String productName = getNameOfNewProduct();
                double productPrice = getPriceOfNewProduct();
                double specialPackingPrice = getSpecialPackingPrice();
                manager.addProductToSeller(sellerIndex, productName, productPrice, productCategory, specialPackingPrice);
                System.out.println("The product has been added successfully to the seller.\n");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // clean buffer
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid index. Please try again.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void addProductToBuyer(Manager manager) {
        while (true) {
            try {
                if (emptyArray(manager.getBuyerCounter(), "buyer")) return;
                if (emptyArray(manager.getSellerCounter(), "seller")) return;
                int buyerIndex = chooseBuyer(manager);
                if (buyerIndex == -1) return;
                int sellerIndex = chooseSeller(manager);
                if (sellerIndex == -1) return;
                if (!productAvailable(manager, sellerIndex)) return;
                int productIndex = chooseProductFromSeller(manager, sellerIndex);
                if (productIndex == -1) return;
                manager.addProductToBuyer(buyerIndex, sellerIndex, productIndex);
                System.out.println("The product has been added successfully to the buyer.\n");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // clean buffer
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid index. Please try again.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void makePayment(Manager manager) {
        while (true) {
            try {
                if (emptyArray(manager.getBuyerCounter(), "buyer")) return;
                int buyerIndex = chooseBuyer(manager);
                if (buyerIndex == -1) return;
                if (manager.isBuyerCartEmpty(buyerIndex)) {
                    System.out.println("The cart is empty. Please add products to the cart before going to the payment.\n");
                    return;
                }
                Order order = manager.payForBuyerCart(buyerIndex);
                System.out.println("Total price of the cart: " + String.format("%.2f", order.getTotalPrice()));
                System.out.println("Purchase date: " + order.getBuyDate() + "\n");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // clean buffer
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static String getNewUsername(String type, Manager manager) {
        String username = "";
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Please enter the " + type + "'s username: ");
            username = scanner.nextLine();
            if (!isOnlyLettersAndSpaces(username)) {
                System.out.println("Invalid username. Only letters and spaces are allowed. Please try again.");
            } else if (manager.usernameTaken(username)) {
                System.out.println("The username you've entered is already taken, please try again.");
            } else {
                validInput = true;
            }
        }
        return username;
    }

    private static boolean isOnlyLettersAndSpaces(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z') && c != ' ') {
                return false;
            }
        }
        return true;
    }

    private static String getNewPassword() {
        System.out.println("Please enter a password: ");
        return scanner.nextLine();
    }

    private static Address getAddress() {
        String country = "", city = "", streetName = "";
        int houseNumber = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Please enter the country: ");
            country = scanner.nextLine();
            if (!isOnlyLettersAndSpaces(country)) {
                System.out.println("Invalid country. Only letters and spaces are allowed. Please try again.");
            } else {
                validInput = true;
            }
        }
        validInput = false;
        while (!validInput) {
            System.out.println("Please enter the city: ");
            city = scanner.nextLine();
            if (!isOnlyLettersAndSpaces(city)) {
                System.out.println("Invalid city. Only letters and spaces are allowed. Please try again.");
            } else {
                validInput = true;
            }
        }
        validInput = false;
        while (!validInput) {
            System.out.println("Please enter the street name: ");
            streetName = scanner.nextLine();
            if (!isOnlyLettersAndSpaces(streetName)) {
                System.out.println("Invalid street name. Only letters and spaces are allowed. Please try again.");
            } else {
                validInput = true;
            }
        }
        validInput = false;
        while (!validInput) {
            try {
                System.out.println("Please enter the house number: ");
                houseNumber = scanner.nextInt();
                scanner.nextLine(); // clean buffer
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for house number.");
                scanner.nextLine();  // clean buffer
            }
        }
        return new Address(country, city, streetName, houseNumber);
    }

    private static String getNameOfNewProduct() {
        String productName = "";
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Please enter the product name: ");
            productName = scanner.nextLine();
            if (isOnlyLettersAndSpaces(productName)) {
                validInput = true;
            } else {
                System.out.println("Invalid product name. Only letters and spaces are allowed. Please try again.");
            }
        }
        return productName;
    }

    private static double getPriceOfNewProduct() {
        double price = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Please enter the product price: ");
                price = scanner.nextDouble();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for price.");
                scanner.nextLine();  // clean buffer
            }
        }
        return price;
    }

    private static ProductCategory chooseProductCategory() {
        int index = -1;
        while (true) {
            try {
                System.out.println("Please enter a number from the following list of product categories (enter 0 to exit): ");
                System.out.println("0 --> Go back to the menu");

                ProductCategory[] categories = ProductCategory.values();
                for (int i = 0; i < categories.length; i++) {
                    System.out.println(categories[i].getIndex() + " --> " + categories[i].getName());
                }

                index = scanner.nextInt();
                scanner.nextLine(); // clean buffer

                if (index == 0) return null;

                ProductCategory category = ProductCategory.getByIndex(index);
                if (category != null) {
                    return category;
                } else {
                    System.out.println("Invalid number. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();  // clean buffer
            }
        }
    }
    private static double getSpecialPackingPrice() {
        double price = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Would you like to add special packing for this product? (1 for Yes, 2 for No)");
                int response = scanner.nextInt();
                if (response == 1) {
                    System.out.println("Please enter the special packing price: ");
                    price = scanner.nextDouble();
                    validInput = true;
                } else if (response == 2) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter 1 for Yes or 2 for No.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for the response.");
                scanner.nextLine();  // clean buffer
            }
        }
        return price;
    }
    private static int chooseSeller(Manager manager) {
        return getIndex(manager, "seller", manager.getSellerCounter());
    }
    private static int chooseBuyer(Manager manager) {
        return getIndex(manager, "buyer", manager.getBuyerCounter());
    }
    private static int getIndex(Manager manager, String type, int count) {
        int index = -1; //initializing
        do {
            try {
                System.out.println("Please enter a number from the following list of " + type + "s (enter 0 to exit): ");
                System.out.println("0 --> Go back to the menu");
                if (type.equals("seller")) {
                    manager.printAllSellers();
                } else if (type.equals("buyer")) {
                    manager.printAllBuyers();
                }
                index = scanner.nextInt() - 1;
                if (index == -1) {
                    return -1;
                }
                if (index < 0 || index >= count) {
                    System.out.println("Invalid selection. Please try again: ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();  // clean buffer
            }
        } while (index < 0 || index >= count);
        return index;
    }

    private static int chooseProductFromSeller(Manager manager, int sellerIndex) {
        int index = -1; //initializing
        do {
            try {
                System.out.println("Please enter the number of the product from the following list (enter 0 to exit): ");
                System.out.println("0 --> Go back to the menu");
                Product[] products = manager.getProductsOfSeller(sellerIndex);
                for (int i = 0; i < products.length; i++) {
                    System.out.println((i + 1) + " --> " + products[i].getProductName() + " - " + products[i].getProductPrice() + " (Serial: " + products[i].getProductSerialNumber() + ")");
                }
                index = scanner.nextInt() - 1;
                if (index == -1) return -1;
                if (index < 0 || index >= manager.getSellerProductCounter(sellerIndex)) {
                    System.out.println("Invalid index. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();  // clean buffer
            }
        } while (index < 0 || index >= manager.getSellerProductCounter(sellerIndex));
        return index;
    }

    private static boolean emptyArray(int counter, String type) {
        if (counter == 0) {
            System.out.println("There are no " + type + "s. Please add a " + type + " first.\n");
            return true;
        }
        return false;
    }

    private static boolean productAvailable(Manager manager, int sellerIndex) {
        if (manager.noProducts(sellerIndex)) {
            System.out.println("This seller does not have any products.\n");
            return false;
        }
        return true;
    }
    private static void printAllBuyersDetails(Buyer[] buyers) {
        for (int i = 0; i < buyers.length; i++) {
            printBuyerDetails(buyers[i]);
        }
    }
    private static void printBuyerDetails(Buyer buyer) {
        System.out.println(buyer.toString());
        System.out.println("----------------------------------------------------");
    }
    private static void printAllSellersDetails(Seller[] sellers) {
        for (int i = 0; i < sellers.length; i++) {
            printSellerDetails(sellers[i]);
        }
    }
    private static void printSellerDetails(Seller seller) {
        System.out.println(seller.toString());
        System.out.println("----------------------------------------------------");
    }
    private static void printProductsByCategory(Manager manager, ProductCategory category) {
        Seller[] sellers = manager.getSellers();
        boolean found = false;
        for (int i = 0; i < sellers.length; i++) {
            Product[] products = sellers[i].getProducts();
            for (int j = 0; j < products.length; j++) {
                if (products[j].getProductCategory() == category) {
                    System.out.println(products[j].toString());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No products found in the selected category.");
        }
    }

    private static void replaceCartWithOrderHistory(Manager manager) {
        int buyerIndex = chooseBuyer(manager);
        if (buyerIndex == -1) return;

        Buyer buyer = manager.getBuyers()[buyerIndex];
        Order[] orders = buyer.getOrders();
        if (orders.length == 0) {
            System.out.println("No orders found in your history.\n");
            return;
        }
        int orderIndex = -1; //initializing
        do {
            try {
                System.out.println("Enter the number of the order you'd like to replace with your current cart:\n");
                for (int i = 0; i < orders.length; i++) {
                    System.out.println((i + 1) + " --> " + orders[i].toString());
                }
                orderIndex = scanner.nextInt() - 1;
                if (orderIndex >= 0 && orderIndex < orders.length) {
                    buyer.clearCart();
                    buyer.addToCart(orders[orderIndex].getProducts());
                    System.out.println("Your cart has been replaced with the selected order.\n");
                } else {
                    System.out.println("Invalid selection, please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // clean buffer
            }
        } while (orderIndex < 0 || orderIndex >= orders.length);
        scanner.nextLine();  // clean buffer
    }

}
