/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import tools.Singleton;

/**
 *
 * @author Влад
 */
public class CustomerFacade extends AbstractFacade<Customer>{
    private EntityManager em;

    public CustomerFacade(Class<Customer> entityClass) {
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
    
}
