/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.manager;

import entity.Category;
import facade.CategoryFacade;
import gui.GuiApp;
import gui.components.ButtonComponent;
import gui.components.CaptionComponent;
import gui.components.ComboBoxCategoriesComponent;
import gui.components.EditComponent;
import gui.components.InfoComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author Влад
 */
public class EditCategoryComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent nameComponent;
    private ButtonComponent buttonComponent;
    
    private CategoryFacade categoryFacade;
    private Category editCategory;
    private ComboBoxCategoriesComponent comboBoxCategoriesComponent;
    
    public EditCategoryComponent() {
        categoryFacade = new CategoryFacade(Category.class);
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Редактирование категории", GuiApp.WIDTH_WINDOW, 31);
        this.add(captionComponent); 
        infoComponent = new InfoComponent("", GuiApp.WIDTH_WINDOW, 30);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        comboBoxCategoriesComponent = new ComboBoxCategoriesComponent("Категории", 240, 30, 300);
        this.add(comboBoxCategoriesComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        nameComponent = new EditComponent("Название категории:", 240, 30, 300);
        this.add(nameComponent);
        buttonComponent = new ButtonComponent("Изменить категорию", GuiApp.WIDTH_WINDOW, 30, 350, 150);
        this.add(buttonComponent);
               buttonComponent.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Category updateCategory = categoryFacade.find(editCategory.getId());
                if(nameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите название категории продукта");
                    return;
                }
                updateCategory.setCategoryName(nameComponent.getEditor().getText());
                
                CategoryFacade categoryFacade = new CategoryFacade(Category.class);
                
                try {
                    categoryFacade.create(updateCategory);
                    infoComponent.getInfo().setText("Категория успешно изменена");
                    infoComponent.getInfo().setForeground(Color.BLUE);
                    comboBoxCategoriesComponent.getComboBox().setModel(comboBoxCategoriesComponent.getComboBoxModel());
                    comboBoxCategoriesComponent.getComboBox().setSelectedIndex(-1);
                } catch (Exception ex) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Категорию изменить не удалось");
                }
            }
        });
        comboBoxCategoriesComponent.getComboBox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                if (comboBox.getSelectedIndex() == -1) {
                    nameComponent.getEditor().setText("");
                }else{
                    editCategory = (Category) e.getItem();
                    nameComponent.getEditor().setText(editCategory.getCategoryName());
                }
            }
        }); {
        
    
    }
    
    
}
    
}
