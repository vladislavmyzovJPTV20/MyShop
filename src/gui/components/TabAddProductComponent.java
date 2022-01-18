/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import entity.Category;
import entity.Product;
import facade.ProductFacade;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Влад
 */
public class TabAddProductComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent productName;
    private EditComponent productPrice;
    private EditComponent quantityComponent;
    private ButtonComponent buttonComponent;
    private ListCategoriesComponent listCategoriesComponent;
    
    public TabAddProductComponent(int widthPanel, int heightPanel) {
        initComponents(widthPanel, heightPanel);
    }

    private void initComponents(int widthPanel, int heightPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Добавление продукта в магазин", widthPanel, 30);
        this.add(captionComponent);
        infoComponent = new InfoComponent("", widthPanel,27);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        productName = new EditComponent("Название продукта:", widthPanel, 30, 300);
        this.add(productName);
        productPrice = new EditComponent("Стоимость продукта:", widthPanel, 30, 300);
        this.add(productPrice);
        listCategoriesComponent = new ListCategoriesComponent("Категории:", widthPanel, 120, 300);
        this.add(listCategoriesComponent);
        quantityComponent = new EditComponent("Количество экземпляров:", widthPanel, 30, 50);
        this.add(quantityComponent);
        buttonComponent = new ButtonComponent("Добавить продукт", widthPanel, 30, 350, 150);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product product = new Product();
                if(productName.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите название продукта");
                    return;
                }
                product.setProductname(productName.getEditor().getText());
                
                if(productPrice.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите цену продукта");
                    return;
                }
                product.setPrice(Integer.parseInt(productPrice.getEditor().getText()));
                
                List<Category> categoriesProduct = listCategoriesComponent.getList().getSelectedValuesList();
                if(categoriesProduct.isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Выберите категорию продукта");
                    return;
                }
                product.setCategory(categoriesProduct);
                try {
                    product.setQuantity(Integer.parseInt(quantityComponent.getEditor().getText()));
                    product.setCount(product.getQuantity());
                } catch (Exception ex) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите количество продукта (цифрами)");
                    return;
                }
                ProductFacade productFacade = new ProductFacade(Product.class);
                try {
                    productFacade.create(product);
                    infoComponent.getInfo().setForeground(Color.BLUE);
                    infoComponent.getInfo().setText("Продукт успешно добавлен");
                    productName.getEditor().setText("");
                    productPrice.getEditor().setText("");
                    quantityComponent.getEditor().setText("");
                    listCategoriesComponent.getList().clearSelection();
                } catch (Exception ex) {
                    infoComponent.getInfo().setForeground(Color.RED);
                    infoComponent.getInfo().setText("Продукт добавить не удалось");
                }
            }
        });
    }
}
