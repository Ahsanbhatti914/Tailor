package com.example.tailor.Models;

public class OrderDetails {
    private String oid;
    private String id;
    private String name;
    private String number;
    private String order_Status;
    private String start_date;
    private String completed_date;
    private String delivered_date;
    private String total_Payment;
    private String paid_Payment;
    private String suit_Quantity;
    private String selected_Date;
    private boolean seen;

    public OrderDetails(){}

    public OrderDetails(String oid, String id, String name, String number, String order_Status, String start_date, String completed_date, String delivered_date, String total_Payment, String paid_Payment, String suit_Quantity, String selected_Date, boolean seen) {
        this.oid = oid;
        this.id = id;
        this.name = name;
        this.number = number;
        this.order_Status = order_Status;
        this.start_date = start_date;
        this.completed_date = completed_date;
        this.delivered_date = delivered_date;
        this.total_Payment = total_Payment;
        this.paid_Payment = paid_Payment;
        this.suit_Quantity = suit_Quantity;
        this.selected_Date = selected_Date;
        this.seen = seen;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrder_Status() {
        return order_Status;
    }

    public void setOrder_Status(String order_Status) {
        this.order_Status = order_Status;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getCompleted_date() {
        return completed_date;
    }

    public void setCompleted_date(String completed_date) {
        this.completed_date = completed_date;
    }

    public String getDelivered_date() {
        return delivered_date;
    }

    public void setDelivered_date(String delivered_date) {
        this.delivered_date = delivered_date;
    }

    public String getTotal_Payment() {
        return total_Payment;
    }

    public void setTotal_Payment(String total_Payment) {
        this.total_Payment = total_Payment;
    }

    public String getPaid_Payment() {
        return paid_Payment;
    }

    public void setPaid_Payment(String paid_Payment) {
        this.paid_Payment = paid_Payment;
    }

    public String getSuit_Quantity() {
        return suit_Quantity;
    }

    public void setSuit_Quantity(String suit_Quantity) {
        this.suit_Quantity = suit_Quantity;
    }

    public String getSelected_Date() {
        return selected_Date;
    }

    public void setSelected_Date(String selected_Date) {
        this.selected_Date = selected_Date;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
