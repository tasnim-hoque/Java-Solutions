import java.util.*;

class User {
    private String username;
    private String password;
    private boolean isAdmin;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean checkPassword(String pass) {
        return this.password.equals(pass);
    }
}

class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceStock(int qty) {
        if (qty <= quantity)
            quantity -= qty;
    }

    public String toString() {
        return id + ": " + name + " - $" + price + " (" + quantity + " in stock)";
    }
}

class CartItem {
    Product product;
    int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return quantity * product.getPrice();
    }

    public String toString() {
        return product.getName() + " x " + quantity + " = $" + getTotalPrice();
    }
}

public class OnlineShopping {
    private static List<Product> products = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static List<CartItem> cart = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        seedData();
        login();
        mainMenu();
    }

    private static void seedData() {
        products.add(new Product(1, "Laptop", 1200.00, 5));
        products.add(new Product(2, "Smartphone", 800.00, 10));
        products.add(new Product(3, "Headphones", 150.00, 20));

        users.add(new User("customer", "1234", false));
        users.add(new User("admin", "admin", true));
    }

    private static void login() {
        System.out.println("Welcome to Online Shopping!");
        while (currentUser == null) {
            System.out.print("Username: ");
            String user = sc.nextLine();
            System.out.print("Password: ");
            String pass = sc.nextLine();
            for (User u : users) {
                if (u.getUsername().equals(user) && u.checkPassword(pass)) {
                    currentUser = u;
                    break;
                }
            }
            if (currentUser == null)
                System.out.println("Invalid credentials. Try again.");
        }
        System.out.println("Hello, " + currentUser.getUsername() + "!");
    }

    private static void mainMenu() {
        int choice;
        do {
            System.out.println("\nMain Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> viewProducts();
                case 2 -> addToCart();
                case 3 -> viewCart();
                case 4 -> checkout();
                case 5 -> System.out.println("Thanks for shopping!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }

    private static void viewProducts() {
        System.out.println("\nAvailable Products:");
        for (Product p : products) {
            System.out.println(p);
        }
    }

    private static void addToCart() {
        System.out.print("Enter product ID to add: ");
        int id = Integer.parseInt(sc.nextLine());
        Product selected = null;
        for (Product p : products) {
            if (p.getId() == id)
                selected = p;
        }

        if (selected == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = Integer.parseInt(sc.nextLine());

        if (qty > selected.getQuantity()) {
            System.out.println("Not enough stock.");
            return;
        }

        cart.add(new CartItem(selected, qty));
        selected.reduceStock(qty);
        System.out.println("Added to cart!");
    }

    private static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("\nYour Cart:");
        double total = 0;
        for (CartItem item : cart) {
            System.out.println(item);
            total += item.getTotalPrice();
        }
        System.out.println("Total: $" + total);
    }

    private static void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        viewCart();
        System.out.print("Proceed to checkout? (y/n): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            cart.clear();
            System.out.println("Order placed! Thank you for shopping.");
        } else {
            System.out.println("Checkout canceled.");
        }
    }
}
