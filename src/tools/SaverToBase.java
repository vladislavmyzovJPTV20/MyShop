/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import entity.Category;
import entity.Customer;
import entity.History;
import entity.Product;
import interfaces.Keeping;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Влад
 */
public class SaverToBase implements Keeping{
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyShopPU");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    @Override
    public void saveProducts(List<Product> products) {
        tx.begin();
            for (int i = 0; i < products.size(); i++) {
                if(products.get(i).getId() == null) {
                    em.persist(products.get(i));
                }
            }
        tx.commit();
    }

    @Override
    public List<Product> loadProducts() {
        List<Product> products = null;
        try {
            products = em.createQuery("SELECT product FROM Product product")
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return products;
    }
    
    @Override
    public void saveCategories(List<Category> categories) {
        tx.begin();
            for (int i = 0; i < categories.size(); i++) {
                if(categories.get(i).getId() == null) {
                    em.persist(categories.get(i));
                }
            }
        tx.commit();
    }

    @Override
    public List<Category> loadCategories() {
        List<Category> categories = null;
        try {
            categories = em.createQuery("SELECT category FROM Category category")
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return categories;
    }

    @Override
    public void saveCustomers(List<Customer> customers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Customer> loadCustomers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveHistories(List<History> histories) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<History> loadHistory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
