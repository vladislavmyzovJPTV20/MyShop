/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myshop;

import java.util.Calendar;
import java.util.GregorianCalendar;
import myclasses.Customer;
import myclasses.History;
import myclasses.Product;

/**
 *
 * @author Влад
 */
public class App {
    
    public void run(){
        
        Customer customer1 = new Customer();
        customer1.setFirstname("Alexander");
        customer1.setLastname("Drouz");
        customer1.setMoney(50);
        
        Customer customer2 = new Customer();
        customer2.setFirstname("Viktor");
        customer2.setLastname("Korchnoi");
        customer2.setMoney(100);
        
        Product product1 = new Product();
        product1.setProductname("Meat");
        product1.setPrice(4.99);
        
        Product product2 = new Product();
        product2.setProductname("Milk");
        product2.setPrice(1.15);
        
        History history1 = new History();
        history1.setCustomer(customer1);
        history1.setProduct(product1);
        Calendar c = new GregorianCalendar();
        history1.setPurchaseDate(c.getTime());
        System.out.println("history1 - " + history1.toString());
        
        History history2 = new History();
        history2.setCustomer(customer2);
        history2.setProduct(product2);
        c = new GregorianCalendar();
        history2.setPurchaseDate(c.getTime());
        System.out.println("history2 - " + history2.toString());
    }
}
