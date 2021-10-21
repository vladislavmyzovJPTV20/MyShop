/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entity.Customer;
import entity.History;
import entity.Product;

/**
 *
 * @author Влад
 */
public interface Keeping {
    public void saveProducts(Product[] products);
    public Product[] loadProducts();
    public void saveCustomers(Customer[] customers);
    public Customer[] loadCustomers();
    public void saveHistories(History[] histories);
    public History[] loadHistory();
}
