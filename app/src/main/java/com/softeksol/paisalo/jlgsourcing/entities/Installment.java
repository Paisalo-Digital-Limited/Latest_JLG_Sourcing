package com.softeksol.paisalo.jlgsourcing.entities;

public class Installment {
    private String DueDate;
    private int Amount;
    private boolean isSelected;

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Installment{" +
                "DueDate='" + DueDate + '\'' +
                ", Amount=" + Amount +
                '}';
    }

    public Installment() {
    }

    public Installment(Installment installment) {
        this.DueDate = installment.DueDate;
        this.Amount = installment.Amount;
        this.isSelected = installment.isSelected;
    }
}
