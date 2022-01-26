/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Customer;
import entity.History;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import tools.Singleton;

/**
 *
 * @author Влад
 */
public class HistoryFacade extends AbstractFacade<History>{
    private EntityManager em;

    public HistoryFacade(Class<History> entityClass) {
        super(entityClass);
        init();
    }
    
    private void init() {
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<History> findWithGivenBooks() {
        try {
            return em.createQuery(
                        "SELECT history FROM History history WHERE history.overdueDate = null AND history.book.count < history.book.quantity")
                     .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public List<History> find(Customer customer) {
        try {
            return em.createQuery("SELECT h FROM History h WHERE h.customer=:customer AND h.overdueDate = null")
                    .setParameter("customer", customer)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
