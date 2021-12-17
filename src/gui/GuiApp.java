/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Category;
import entity.Product;
import facade.ProductFacade;
import gui.components.ButtonComponent;
import gui.components.CaptionComponent;
import gui.components.EditComponent;
import gui.components.InfoComponent;
import gui.components.ListCategoriesComponent;
import gui.components.TabAddCategoryComponent;
import gui.components.TabAddCustomerComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Влад
 */
public class GuiApp extends JFrame{
private CaptionComponent captionComponent;
private InfoComponent infoComponent;
private EditComponent nameProductComponent;
private EditComponent priceComponent;
private EditComponent quantityComponent;
private ButtonComponent buttonComponent;
private ListCategoriesComponent listCategoriesComponent;

    public GuiApp() {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        this.setPreferredSize(new Dimension(600,400));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        JTabbedPane managerTabbed = new JTabbedPane();
        managerTabbed.setPreferredSize(new Dimension(600,400));
        managerTabbed.setMinimumSize(managerTabbed.getPreferredSize());
        managerTabbed.setMaximumSize(managerTabbed.getPreferredSize());
        this.add(managerTabbed);
        JPanel addBookPanel = new JPanel();
        addBookPanel.setLayout(new BoxLayout(addBookPanel, BoxLayout.Y_AXIS));
        addBookPanel.add(Box.createRigidArea(new Dimension(0,25)));
        managerTabbed.addTab("Добавить продукт", addBookPanel);
        TabAddCustomerComponent tabAddCustomerComponent = new TabAddCustomerComponent(this.getWidth());
        managerTabbed.addTab("Добавить покупателя", tabAddCustomerComponent);
        TabAddCategoryComponent tabAddCategoryComponent = new TabAddCategoryComponent(this.getWidth());
        managerTabbed.addTab("Добавить категорию", tabAddCategoryComponent);
        
        captionComponent = new CaptionComponent("Добавление продукта в магазин", this.getWidth(), 30);
        addBookPanel.add(captionComponent);
        infoComponent = new InfoComponent("", this.getWidth(),30);
        addBookPanel.add(infoComponent);
        addBookPanel.add(Box.createRigidArea(new Dimension(0,10)));
        nameProductComponent = new EditComponent("Название продукта:",this.getWidth(), 30, 300);
        addBookPanel.add(nameProductComponent);
        listCategoriesComponent = new ListCategoriesComponent("Категории:", this.getWidth(), 120, 300);
        addBookPanel.add(listCategoriesComponent);
        priceComponent = new EditComponent("Цена:", this.getWidth(), 30, 100);
        addBookPanel.add(priceComponent);
        quantityComponent = new EditComponent("Количество экземпляров:", this.getWidth(), 30, 50);
        addBookPanel.add(quantityComponent);
        buttonComponent = new ButtonComponent("Добавить продукт", this.getWidth(), 30, 350, 150);
        addBookPanel.add(buttonComponent);
        buttonComponent.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product product = new Product();
                if(nameProductComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите название продукта");
                    return;
                }
                product.setProductname(nameProductComponent.getEditor().getText());
                
                List<Category> categoriesProduct = listCategoriesComponent.getList().getSelectedValuesList();
                if(categoriesProduct.isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Выберите категорию продукта");
                    return;
                }
                product.setCategory(categoriesProduct);
                try {
                    product.setPrice(Integer.parseInt(priceComponent.getEditor().getText()));
                } catch (Exception ex) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите цену продукта (цифрами)");
                    return;
                }
                try {
                    product.setQuantity(Integer.parseInt(quantityComponent.getEditor().getText()));
                    product.setCount(product.getQuantity());
                } catch (Exception ex) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите количество экземпляров продукта (цифрами)");
                    return;
                }
                ProductFacade productFacade = new ProductFacade(Product.class);
                try {
                    productFacade.create(product);
                    infoComponent.getInfo().setForeground(Color.BLUE);
                    infoComponent.getInfo().setText("Продукт успешно добавлен");
                    nameProductComponent.getEditor().setText("");
                    priceComponent.getEditor().setText("");
                    quantityComponent.getEditor().setText("");
                    listCategoriesComponent.getList().clearSelection();
                } catch (Exception ex) {
                    infoComponent.getInfo().setForeground(Color.RED);
                    infoComponent.getInfo().setText("Продукт добавить не удалось");
                }
               
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

}
