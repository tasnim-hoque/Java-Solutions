import java.io.*;
import java.util.*;

// User class
class User {
    private static int idCounter = 1000;
    private int userId;
    private String name;
    private String phone;
    private String email;
    private String address;

    public User(String name, String phone, String email, String address) {
        this.userId = idCounter++;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return userId + "," + name + "," + phone + "," + email + "," + address;
    }

    public static void saveToFile(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
            bw.write(user.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }
}

// Room class
class Room {
    private int number;
    private String type;
    private double price;
    private boolean available = true;

    public Room(int number, String type, double price) {
        this.number = number;
        this.type = type;
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String toString() {
        return "Room " + number + " [" + type + "] - $" + price + " - " + (available ? "Available" : "Booked");
    }
}

// Booking class
class Booking {
    private String guestName;
    private Room room;
    private boolean checkedIn = false;
    private boolean checkedOut = false;
    private String review = "None";

    public Booking(String guestName, Room room) {
        this.guestName = guestName;
        this.room = room;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void generateInvoice() {
        System.out.println("--- Invoice ---");
        System.out.println("Guest: " + guestName);
        System.out.println("Room Number: " + room.getNumber());
        System.out.println("Room Type: " + room.getType());
        System.out.println("Amount Due: $" + room.getPrice());
        System.out.println("----------------");
    }

    public String toString() {
        return guestName + "," + room.getNumber() + "," + room.getType() + "," + room.getPrice() + "," + checkedIn + "," + checkedOut + "," + review;
    }

    public static void saveToFile(Booking booking) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("bookings.txt", true))) {
            bw.write(booking.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving booking: " + e.getMessage());
        }
    }
}

// Hotel class
class Hotel {
    private String name;
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public Hotel(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void searchAndBookRoom(Scanner scanner) {
        System.out.println("Select room type:");
        System.out.println("1. Single\n2. Double\n3. Suite");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        String type = switch (choice) {
            case 1 -> "Single";
            case 2 -> "Double";
            case 3 -> "Suite";
            default -> {
                System.out.println("Invalid choice."); yield "";
            }
        };

        if (type.equals("")) return;

        for (Room room : rooms) {
            if (room.getType().equalsIgnoreCase(type) && room.isAvailable()) {
                System.out.print("Enter your name: ");
                scanner.nextLine(); // consume newline
                String guestName = scanner.nextLine();
                room.setAvailable(false);
                Booking booking = new Booking(guestName, room);
                bookings.add(booking);
                Booking.saveToFile(booking);
                System.out.println("Room booked successfully! Room Number: " + room.getNumber());
                return;
            }
        }
        System.out.println("No available rooms of type: " + type);
    }

    public void checkIn(Scanner scanner) {
        System.out.print("Enter your name to check in: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        for (Booking b : bookings) {
            if (b.getGuestName().equalsIgnoreCase(name) && !b.isCheckedIn()) {
                b.setCheckedIn(true);
                System.out.println("Check-in successful.");
                return;
            }
        }
        System.out.println("Booking not found.");
    }

    public void checkOut(Scanner scanner) {
        System.out.print("Enter your name to check out: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        for (Booking b : bookings) {
            if (b.getGuestName().equalsIgnoreCase(name) && b.isCheckedIn()) {
                b.setCheckedOut(true);
                b.getRoom().setAvailable(true);
                b.generateInvoice();
                return;
            }
        }
        System.out.println("Check-in not found.");
    }

    public void leaveReview(Scanner scanner) {
        System.out.print("Enter your name to leave a review: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        for (Booking b : bookings) {
            if (b.getGuestName().equalsIgnoreCase(name)) {
                System.out.print("Enter your review: ");
                String review = scanner.nextLine();
                b.setReview(review);
                System.out.println("Review saved. Thank you!");
                return;
            }
        }
        System.out.println("Booking not found.");
    }

    public void viewBookings() {
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }
}

// Main class
public class HotelBookingSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("Sunrise Inn");
        Scanner scanner = new Scanner(System.in);

        // Add rooms
        hotel.addRoom(new Room(101, "Single", 50.0));
        hotel.addRoom(new Room(102, "Double", 80.0));
        hotel.addRoom(new Room(201, "Suite", 150.0));

        System.out.println("Welcome to Sunrise Inn - Register below to continue:");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        User user = new User(name, phone, email, address);
        User.saveToFile(user);
        System.out.println("Registration successful. Your ID is: " + user.getUserId());

        int choice;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Book Room\n2. Check-In\n3. Check-Out\n4. Leave Review\n5. View Bookings\n6. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> hotel.searchAndBookRoom(scanner);
                case 2 -> hotel.checkIn(scanner);
                case 3 -> hotel.checkOut(scanner);
                case 4 -> hotel.leaveReview(scanner);
                case 5 -> hotel.viewBookings();
                case 6 -> System.out.println("Thank you!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
