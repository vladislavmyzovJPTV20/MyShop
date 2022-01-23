/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.manager;

import entity.Category;
import entity.Product;
import facade.ProductFacade;
import gui.GuiApp;
import gui.components.ButtonComponent;
import gui.components.CaptionComponent;
import gui.components.ComboBoxProductsComponent;
import gui.components.EditComponent;
import gui.components.InfoComponent;
import gui.components.ListCategoriesComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.ListModel;

/**
 *
 * @author Влад
 */
public class EditProductComponent extends JPanel{
    public EditProductComponent editProductComponent = this;
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent productName;
    private EditComponent productPrice;
    private EditComponent quantityComponent;
    private ButtonComponent buttonComponent;
    private ListCategoriesComponent listCategoriesComponent;
    private ProductFacade productFacade;
    private Product editProduct;
    private ComboBoxProductsComponent comboBoxProductsComponent;
    
    public EditProductComponent() {
        productFacade = new ProductFacade(Product.class);
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Изменение продукта в магазине", GuiApp.WIDTH_WINDOW, 30);
        this.add(captionComponent);
        infoComponent = new InfoComponent("", GuiApp.WIDTH_WINDOW,27);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        productName = new EditComponent("Название продукта:", GuiApp.WIDTH_WINDOW, 30, 300);
        this.add(productName);
        productPrice = new EditComponent("Стоимость продукта:", GuiApp.WIDTH_WINDOW, 30, 300);
        this.add(productPrice);
        listCategoriesComponent = new ListCategoriesComponent("Категории:", GuiApp.WIDTH_WINDOW, 120, 300);
        this.add(listCategoriesComponent);
        quantityComponent = new EditComponent("Количество экземпляров:", GuiApp.WIDTH_WINDOW, 30, 50);
        this.add(quantityComponent);
        buttonComponent = new ButtonComponent("Изменить продукт", GuiApp.WIDTH_WINDOW, 30, 350, 150);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product updateProduct = productFacade.find(editProduct.getId());
                
                if(productName.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите название продукта");
                    return;
                }
                updateProduct.setProductname(productName.getEditor().getText());
                
                if(productPrice.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите цену продукта");
                    return;
                }
                updateProduct.setPrice(Double.parseDouble(productPrice.getEditor().getText()));
                
                List<Category> categoriesProduct = listCategoriesComponent.getList().getSelectedValuesList();
                if(categoriesProduct.isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Выберите категорию продукта");
                    return;
                }
                updateProduct.setCategory(categoriesProduct);
                try {
                    updateProduct.setQuantity(Integer.parseInt(quantityComponent.getEditor().getText()));
                    updateProduct.setCount(updateProduct.getQuantity());
                } catch (Exception ex) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите количество продукта (цифрами)");
                    return;
                }
                ProductFacade productFacade = new ProductFacade(Product.class);
                try {
                    productFacade.edit(updateProduct);
                    infoComponent.getInfo().setForeground(Color.BLUE);
                    infoComponent.getInfo().setText("Продукт успешно изменён");
                    comboBoxProductsComponent.getComboBox().setModel(comboBoxProductsComponent.getComboBoxModel());
                    comboBoxProductsComponent.getComboBox().setSelectedIndex(-1);
                } catch (Exception ex) {
                    infoComponent.getInfo().setForeground(Color.RED);
                    infoComponent.getInfo().setText("Продукт изменить не удалось");
                }
            }
        });
        comboBoxProductsComponent.getComboBox().addItemListener((ItemEvent e) -> {
           JComboBox comboBox = (JComboBox) e.getSource();
           if(comboBox.getSelectedIndex() == -1){
                productName.getEditor().setText("");
                productPrice.getEditor().setText("");
                quantityComponent.getEditor().setText("");
                listCategoriesComponent.getList().clearSelection();
           }else{
                editProduct = (Product) e.getItem();
                productName.getEditor().setText(editProduct.getProductname());
                productPrice.getEditor().setText(((Double)editProduct.getPrice()).toString());
                quantityComponent.getEditor().setText(((Integer)editProduct.getQuantity()).toString());
                listCategoriesComponent.getList().clearSelection();
                ListModel<Category> listModel = listCategoriesComponent.getList().getModel();
                for (int i=0;i<listModel.getSize();i++) {
                    if(editProduct.getCategory().contains(listModel.getElementAt(i))){
                        listCategoriesComponent.getList().getSelectionModel().addSelectionInterval(i, i);
                    }
                }
           }
        });
}
       
    
    
}