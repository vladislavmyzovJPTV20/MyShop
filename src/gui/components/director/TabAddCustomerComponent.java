/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.director;

import entity.Customer;
import facade.CustomerFacade;
import gui.GuiApp;
import gui.components.ButtonComponent;
import gui.components.CaptionComponent;
import gui.components.EditComponent;
import gui.components.InfoComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Влад
 */
public class TabAddCustomerComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent nameComponent;
    private EditComponent lastNameComponent;
    private EditComponent moneyComponent;
    private ButtonComponent buttonComponent;
    
    public TabAddCustomerComponent() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Добавление нового покупателя", GuiApp.WIDTH_WINDOW, 31);
        this.add(captionComponent); 
        infoComponent = new InfoComponent("", GuiApp.WIDTH_WINDOW, 30);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        nameComponent = new EditComponent("Имя:", 240, 30, 300);
        this.add(nameComponent);
        lastNameComponent = new EditComponent("Фамилия:", 240, 30, 300);
        this.add(lastNameComponent);
        moneyComponent = new EditComponent("Деньги:", 240, 30, 200);
        this.add(moneyComponent);
        buttonComponent = new ButtonComponent("Добавить покупателя", GuiApp.WIDTH_WINDOW, 30, 350, 150);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(ButtonAddReader());
    }
    private ActionListener ButtonAddReader(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                Customer customer = new Customer();
                if(nameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите имя");
                    return;
                }
                customer.setFirstname(nameComponent.getEditor().getText());
                if(lastNameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите фамилию");
                    return;
                }
                customer.setLastname(lastNameComponent.getEditor().getText());
                
                if(moneyComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите деньги");
                    return;
                } 
                customer.setMoney(Integer.parseInt(moneyComponent.getEditor().getText()));
                
                CustomerFacade customerFacade = new CustomerFacade(Customer.class);
                
                try {
                    customerFacade.create(customer);
                    infoComponent.getInfo().setText("Покупатель успешно добавлен");
                    infoComponent.getInfo().setForeground(Color.BLUE);
                    moneyComponent.getEditor().setText("");
                    lastNameComponent.getEditor().setText("");
                    nameComponent.getEditor().setText("");
                } catch (Exception e) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Покупателя добавить не удалось");
                }
            }
        };
    }
    
    
}
