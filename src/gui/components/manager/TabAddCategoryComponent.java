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
public class TabAddCategoryComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent nameComponent;
    private ButtonComponent buttonComponent;
    
    public TabAddCategoryComponent() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Добавление новой категории", GuiApp.WIDTH_WINDOW, 31);
        this.add(captionComponent); 
        infoComponent = new InfoComponent("", GuiApp.WIDTH_WINDOW, 30);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        nameComponent = new EditComponent("Название категории:", GuiApp.WIDTH_WINDOW, 30, 300);
        this.add(nameComponent);
        buttonComponent = new ButtonComponent("Добавить категорию", GuiApp.WIDTH_WINDOW, 30, 350, 150);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(ButtonAddReader());
    }
    private ActionListener ButtonAddReader(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                Category category = new Category();
                if(nameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите название категории продукта");
                    return;
                }
                category.setCategoryName(nameComponent.getEditor().getText());
                
                CategoryFacade categoryFacade = new CategoryFacade(Category.class);
                
                try {
                    categoryFacade.create(category);
                    infoComponent.getInfo().setText("Категория успешно добавлена");
                    infoComponent.getInfo().setForeground(Color.BLUE);
                    nameComponent.getEditor().setText("");
                } catch (Exception e) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Категорию добавить не удалось");
                }
            }
        };
    }
    
    
}
