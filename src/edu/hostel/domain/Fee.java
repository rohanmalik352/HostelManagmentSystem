package edu.hostel.domain;

public class Fee {
    public enum PaymentStatus { PAID, PENDING, OVERDUE }

    private String feeId;
    private String studentId;
    private double amount;
    private String dueDate;
    private String paymentDate;
    private PaymentStatus status;
    private String description;

    public Fee(String feeId, String studentId, double amount, String dueDate, String description) {
        this.feeId = feeId;
        this.studentId = studentId;
        this.amount = amount;
        this.dueDate = dueDate;
        this.description = description;
        this.status = PaymentStatus.PENDING;
        this.paymentDate = "N/A";
    }

    public void markAsPaid(String paymentDate) {
        this.status = PaymentStatus.PAID;
        this.paymentDate = paymentDate;
    }

    public void markAsOverdue() {
        if (this.status == PaymentStatus.PENDING) {
            this.status = PaymentStatus.OVERDUE;
        }
    }

    // Getters and Setters
    public String getFeeId() { return feeId; }
    public String getStudentId() { return studentId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getDueDate() { return dueDate; }
    public String getPaymentDate() { return paymentDate; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    public String getDescription() { return description; }

    public String toCSV() {
        return feeId + "," + studentId + "," + amount + "," + dueDate + "," + paymentDate + "," + status + "," + description;
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-12s | Rs.%-8.0f | %-12s | %-12s | %-8s |",
                feeId, studentId, amount, dueDate, paymentDate, status);
    }
}