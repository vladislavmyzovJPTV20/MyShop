/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Customer;
import entity.Role;
import entity.User;
import entity.UserRoles;
import facade.CustomerFacade;
import facade.RoleFacade;
import facade.UserFacade;
import facade.UserRolesFacade;
import gui.components.ButtonComponent;
import gui.components.ListProductsComponent;
import gui.components.TabAddCategoryComponent;
import gui.components.TabAddProductComponent;
import gui.components.TabAddCustomerComponent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Melnikov
 */
public class GuiApp extends JFrame{
    public static final int WIDTH_WINDOW = 700;
    public static final int HEIGHT_WINDOW = 450;
    private ButtonComponent buttonChangePanelComponent;
    private ListProductsComponent listProductsComponent;
    public GuiApp guiApp = this;
    public static User user;
    public static Role role;
    private UserFacade userFacade = new UserFacade();
    private RoleFacade roleFacade = new RoleFacade();
    private UserRolesFacade userRolesFacade = new UserRolesFacade();
    private CustomerFacade customerFacade = new CustomerFacade(Customer.class);
    
    public GuiApp() {
        superAdmin();
        setTitle("Магазин продуктов группы JPTV20");
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        this.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        JTabbedPane managerTabbed = new JTabbedPane();
        managerTabbed.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
        managerTabbed.setMinimumSize(managerTabbed.getPreferredSize());
        managerTabbed.setMaximumSize(managerTabbed.getPreferredSize());
        this.add(managerTabbed);
        TabAddProductComponent tabAddProductComponent = new TabAddProductComponent(this.getWidth());
        managerTabbed.addTab("Добавить продукт", tabAddProductComponent);
        TabAddCustomerComponent tabAddCustomerComponent = new TabAddCustomerComponent(this.getWidth());
        managerTabbed.addTab("Добавить покупателя", tabAddCustomerComponent);
        TabAddCategoryComponent tabAddCategoryComponent = new TabAddCategoryComponent(this.getWidth());
        managerTabbed.addTab("Добавить категорию", tabAddCategoryComponent);
        JPanel guestPanel = new JPanel();
        listProductsComponent = new ListProductsComponent(false, "Список товаров магазина", GuiApp.HEIGHT_WINDOW, GuiApp.HEIGHT_WINDOW - 100, GuiApp.WIDTH_WINDOW);
        guestPanel.add(Box.createRigidArea(new Dimension(0,10)));
        guestPanel.add(listProductsComponent);
        guestPanel.add(Box.createRigidArea(new Dimension(0,10)));
        buttonChangePanelComponent = new ButtonComponent("К Выбору книг", 50, 470, 200);
        guestPanel.add(buttonChangePanelComponent);
        this.add(guestPanel);
        buttonChangePanelComponent.getButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            guiApp.getContentPane().removeAll();
            JTabbedPane managerTabbed = new JTabbedPane();
            managerTabbed.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
            managerTabbed.setMinimumSize(managerTabbed.getPreferredSize());
            managerTabbed.setMaximumSize(managerTabbed.getPreferredSize());
            guiApp.add(managerTabbed);
            TabAddProductComponent tabAddProductComponent = new TabAddProductComponent(guiApp.getWidth());
            managerTabbed.addTab("Добавить продукт", tabAddProductComponent);
            TabAddCustomerComponent tabAddCustomerComponent = new TabAddCustomerComponent(guiApp.getWidth());
            managerTabbed.addTab("Добавить покупателя", tabAddCustomerComponent);
            TabAddCategoryComponent tabAddCategoryComponent = new TabAddCategoryComponent(guiApp.getWidth());
            managerTabbed.addTab("Добавить категорию", tabAddCategoryComponent);              
            }
        });
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuiApp().setVisible(true);
            }
        });
    }

    private void superAdmin() {
        List<User> users = userFacade.findAll();
        if(!users.isEmpty()) return;
        Customer customer = new Customer();
        customer.setFirstname("Vladislav");
        customer.setLastname("Myzov");
        customer.setMoney(50);
        customerFacade.create(customer);
        
        User user = new User();
        user.setLogin("admin");
        user.setPassword("12345");
        user.setCustomer(customer);
        userFacade.create(user);
        
        Role role = new Role();
        role.setRoleName("ADMINISTRATOR");
        roleFacade.create(role);
        UserRoles userRoles = new UserRoles();
        userRoles.setUser(user);
        userRoles.setRole(role);
        userRolesFacade.create(userRoles);
        
        role = new Role();
        role.setRoleName("MANAGER");
        roleFacade.create(role);
        userRoles = new UserRoles();
        userRoles.setUser(user);
        userRoles.setRole(role);
        userRolesFacade.create(userRoles);
        
        role = new Role();
        role.setRoleName("CUSTOMER");
        roleFacade.create(role);
        userRoles = new UserRoles();
        userRoles.setUser(user);
        userRoles.setRole(role);
        userRolesFacade.create(userRoles);
    }

}
