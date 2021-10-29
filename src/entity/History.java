/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Влад
 */
public class History implements Serializable{
    private Customer customer;
    private Product product;
    private Date purchaseDate;
    private Date overdueDate;
    private LocalDate LocalReturnedDate;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    
    public Date getOverdueDate() {
        return overdueDate;
    }

    public void setOverdueDate(Date overdueDate) {
        this.overdueDate = overdueDate;
    }

    public LocalDate getLocalReturnedDate() {
        return LocalReturnedDate;
    }

    public void setLocalReturnedDate(LocalDate LocalReturnedDate) {
        this.LocalReturnedDate = LocalReturnedDate;
    }

    @Override
    public String toString() {
        return "History{" + "customer=" + customer + ", product=" + product + ", purchaseDate=" + purchaseDate + ", overdueDate=" + overdueDate + ", LocalReturnedDate=" + LocalReturnedDate + '}';
    }
}
