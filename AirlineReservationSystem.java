import java.util.ArrayList;
import java.util.Scanner;

public class AirlineReservationSystem {

    // ----- Flight Class -----
    static class Flight {
        private String flightNumber;
        private String origin;
        private String destination;
        private int capacity;
        private int bookedSeats;

        public Flight(String flightNumber, String origin, String destination, int capacity) {
            this.flightNumber = flightNumber;
            this.origin = origin;
            this.destination = destination;
            this.capacity = capacity;
            this.bookedSeats = 0;
        }

        public boolean bookSeat() {
            if (bookedSeats < capacity) {
                bookedSeats++;
                return true;
            }
            return false;
        }

        public String getFlightInfo() {
            return flightNumber + " from " + origin + " to " + destination + " | Seats left: "
                    + (capacity - bookedSeats);
        }

        public String getFlightNumber() {
            return flightNumber;
        }
    }

    // ----- Passenger Class -----
    static class Passenger {
        private String name;
        private String passportNumber;

        public Passenger(String name, String passportNumber) {
            this.name = name;
            this.passportNumber = passportNumber;
        }

        public String getName() {
            return name;
        }

        public String getPassportNumber() {
            return passportNumber;
        }
    }

    // ----- Booking Class -----
    static class Booking {
        private Passenger passenger;
        private Flight flight;

        public Booking(Passenger passenger, Flight flight) {
            this.passenger = passenger;
            this.flight = flight;
        }

        public void printBookingDetails() {
            System.out.println("Booking confirmed for " + passenger.getName() +
                    " on flight " + flight.getFlightInfo());
        }
    }

    // ----- AirlineReservationSystem Main Logic -----
    private ArrayList<Flight> flights = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();

    public AirlineReservationSystem() {
        flights.add(new Flight("AI101", "New York", "London", 2));
        flights.add(new Flight("AI202", "Paris", "Tokyo", 3));
    }

    public void displayFlights() {
        for (Flight flight : flights) {
            System.out.println(flight.getFlightInfo());
        }
    }

    public void bookFlight(String flightNumber, String passengerName, String passportNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                if (flight.bookSeat()) {
                    Passenger passenger = new Passenger(passengerName, passportNumber);
                    Booking booking = new Booking(passenger, flight);
                    bookings.add(booking);
                    booking.printBookingDetails();
                    return;
                } else {
                    System.out.println("Flight is full!");
                    return;
                }
            }
        }
        System.out.println("Flight not found!");
    }

    public static void main(String[] args) {
        AirlineReservationSystem system = new AirlineReservationSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. View Flights\n2. Book Flight\n3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                system.displayFlights();
            } else if (choice == 2) {
                System.out.print("Enter flight number: ");
                String fn = scanner.nextLine();
                System.out.print("Enter passenger name: ");
                String name = scanner.nextLine();
                System.out.print("Enter passport number: ");
                String pass = scanner.nextLine();
                system.bookFlight(fn, name, pass);
            } else if (choice == 3) {
                System.out.println("Exiting system. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}
