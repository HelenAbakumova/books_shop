package entity;

import java.sql.Timestamp;

public class OrderForm {
    private int id;
    private String status;
    private Timestamp date;
    private int userId;
    private String amount;

    public OrderForm(int id, String status, Timestamp date, int userId, String amount) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.userId = userId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
