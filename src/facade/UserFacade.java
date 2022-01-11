/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.User;
import javax.persistence.EntityManager;
import tools.Singleton;

/**
 *
 * @author Melnikov
 */
public class UserFacade extends AbstractFacade<User>{
   
    private EntityManager em;
    
    public UserFacade() {
        super(User.class);
        init();
    }

    private void init(){
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public User find(String login) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
