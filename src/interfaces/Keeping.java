/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entity.Category;
import entity.Customer;
import entity.History;
import entity.Product;
import java.util.List;

/**
 *
 * @author Влад
 */
public interface Keeping {
    public void saveProducts(List<Product> products);
    public List<Product> loadProducts();
    public void saveCategories(List<Category> categories);
    public List<Category> loadCategories();
    public void saveCustomers(List<Customer> customers);
    public List<Customer> loadCustomers();
    public void saveHistories(List<History> histories);
    public List<History> loadHistory();
}
