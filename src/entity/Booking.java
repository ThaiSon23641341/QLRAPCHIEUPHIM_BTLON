package entity;

public class Booking {
    private String id;
    private String movie;
    private String seats;
    private String date;
    private double amount;
    private String status;
    private String customerName;
    private String customerEmail;
    private String paymentMethod;
    
    public Booking(String id, String movie, String seats, String date, double amount, String status,
                  String customerName, String customerEmail, String paymentMethod) {
        this.id = id;
        this.movie = movie;
        this.seats = seats;
        this.date = date;
        this.amount = amount;
        this.status = status;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.paymentMethod = paymentMethod;
    }
    
    public String getId() { return id; }
    public String getMovie() { return movie; }
    public String getSeats() { return seats; }
    public String getDate() { return date; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public String getPaymentMethod() { return paymentMethod; }
}