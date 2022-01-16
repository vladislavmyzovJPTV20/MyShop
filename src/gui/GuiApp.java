/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Customer;
import entity.Role;
import entity.Product;
import entity.Category;
import entity.User;
import entity.UserRoles;
import facade.CustomerFacade;
import facade.RoleFacade;
import facade.ProductFacade;
import facade.UserFacade;
import facade.UserRolesFacade;
import gui.components.ButtonComponent;
import gui.components.CaptionComponent;
import gui.components.EditComponent;
import gui.components.GuestComponent;
import gui.components.InfoComponent;
import gui.components.ListProductsComponent;
import gui.components.ListCategoriesComponent;
import gui.components.TabAddCustomerComponent;
import gui.components.TabAddProductComponent;
import gui.components.TabAddCategoryComponent;
import gui.components.customer.CustomerComponent;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class GuiApp extends JFrame{
    public static final int WIDTH_WINDOW = 700;
    public static final int HEIGHT_WINDOW = 550;
    public static User user;
    public static String role;
    private JPanel guestPanel;
    private InfoComponent infoTopComponent;
    private CustomerComponent customerComponent;
    private ButtonComponent buttonChangePanelComponent;
    private ListProductsComponent listProductsComponent;
    private UserFacade userFacade = new UserFacade();
    private RoleFacade roleFacade = new RoleFacade();
    private UserRolesFacade userRolesFacade = new UserRolesFacade();
    private CustomerFacade customerFacade = new CustomerFacade(Customer.class);
    public GuiApp guiApp = this;

    public GuiApp() {
        superAdmin();
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        guestPanel = new GuestComponent(WIDTH_WINDOW,HEIGHT_WINDOW);
        buttonChangePanelComponent = new ButtonComponent("Войти", 50, 470, 200);
        guestPanel.add(buttonChangePanelComponent);
        this.add(guestPanel);
        buttonChangePanelComponent.getButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              //Аутентификация
                int widthWin = 350;
                int heightWin = 260;
                JDialog dialogLogin = new JDialog(guiApp,"Введите логин и пароль",Dialog.ModalityType.DOCUMENT_MODAL);
                dialogLogin.setPreferredSize(new Dimension(widthWin,heightWin));
                dialogLogin.setMaximumSize(dialogLogin.getPreferredSize());
                dialogLogin.setMinimumSize(dialogLogin.getPreferredSize());
                dialogLogin.getContentPane().setLayout(new BoxLayout(dialogLogin.getContentPane(), BoxLayout.Y_AXIS));
                dialogLogin.setLocationRelativeTo(null);
                CaptionComponent captionComponent = new CaptionComponent("Введите логин и пароль", widthWin, 27);
                InfoComponent infoComponent = new InfoComponent("", widthWin, 27);
                EditComponent loginComponent = new EditComponent("Логин",200, 27, 120);
                EditComponent passwordComponent = new EditComponent("Пароль", 200, 27, 120);
                ButtonComponent enterComponent = new ButtonComponent("Войти", 27,180, 100);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
                dialogLogin.getContentPane().add(captionComponent);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
                dialogLogin.getContentPane().add(infoComponent);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
                dialogLogin.getContentPane().add(loginComponent);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
                dialogLogin.getContentPane().add(passwordComponent);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,15)));
                dialogLogin.getContentPane().add(enterComponent);
                enterComponent.getButton().addActionListener( new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Аутентификация - узнать есть ли такой пользователь
                        User user = userFacade.find(loginComponent.getEditor().getText().trim());
                        if(user == null){
                            infoComponent.getInfo().setText("Нет такого пользователя");
                            return;
                        }
                        //Авторизация - он ли это пользователь и какие у него права.
                        if(!user.getPassword().equals(passwordComponent.getEditor().getText().trim())){
                            infoComponent.getInfo().setText("Нет такого пользователя, или неверный пароль");
                            return;
                        } 
                        GuiApp.user = user;
                        //Пользователь тот за кого себя выдает, устанавливаем разрешения.
                        String role = userRolesFacade.getTopRole(user);
                        GuiApp.role = role;
                        infoTopComponent.getInfo().setText("Hello "+user.getCustomer().getFirstname());
                        guiApp.getContentPane().remove(guestPanel);
                        guiApp.getContentPane().remove(buttonChangePanelComponent);
                        JTabbedPane jTabbedPane = new JTabbedPane();
                        jTabbedPane.setPreferredSize(new Dimension(WIDTH_WINDOW,HEIGHT_WINDOW));
                        jTabbedPane.setMinimumSize(jTabbedPane.getPreferredSize());
                        jTabbedPane.setMaximumSize(jTabbedPane.getPreferredSize());
                        if("CUSTOMER".equals(GuiApp.role)){
                            customerComponent = new CustomerComponent(WIDTH_WINDOW,HEIGHT_WINDOW);
                            jTabbedPane.addTab("Покупатель", customerComponent);
                        }
                        guiApp.getContentPane().add(jTabbedPane);
                        guiApp.repaint();
                        guiApp.revalidate();
                        dialogLogin.setVisible(false);
                        dialogLogin.dispose();
                    }
                    
                });
                dialogLogin.pack();
                dialogLogin.setVisible(true);
//            guiApp.getContentPane().removeAll();
//            JTabbedPane managerTabbed = new JTabbedPane();
//            managerTabbed.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
//            managerTabbed.setMinimumSize(managerTabbed.getPreferredSize());
//            managerTabbed.setMaximumSize(managerTabbed.getPreferredSize());
//            guiApp.add(managerTabbed);
//            TabAddShoesComponent tabAddShoesComponent = new TabAddShoesComponent(guiApp.getWidth());
//            managerTabbed.addTab("Добавить обувь", tabAddShoesComponent);
//            TabAddSizeComponent tabAddSizeComponent = new TabAddSizeComponent(guiApp.getWidth());
//            managerTabbed.addTab("Добавить размер обуви", tabAddSizeComponent);
//            TabAddCustomerComponent tabAddCustomerComponent = new TabAddCustomerComponent(guiApp.getWidth());
//            managerTabbed.addTab("Добавить покупателя", tabAddCustomerComponent);            
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
