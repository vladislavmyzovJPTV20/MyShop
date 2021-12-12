/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.components.ButtonComponent;
import gui.components.CaptionComponent;
import gui.components.EditComponent;
import gui.components.InfoComponent;
import gui.components.ListCategoriesComponent;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 *
 * @author Влад
 */
public class GuiApp extends JFrame{
    
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent editComponent;
    private EditComponent price;
    private EditComponent quantityComponent;
    private ListCategoriesComponent listCategoriesComponent;
    private ButtonComponent buttonComponent;

    public GuiApp() {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        this.setPreferredSize(new Dimension(600,400));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Добавление продукта в магазин", this.getWidth(), 30);
        this.add(captionComponent);
        infoComponent = new InfoComponent("Информация о добавлении продукта в магазин", this.getWidth(), 27);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        editComponent = new EditComponent("Название продукта",this.getWidth(), 30, 300);
        this.add(editComponent);
        price = new EditComponent("Цена продукта", this.getWidth(), 30, 50);
        this.add(price);
        quantityComponent = new EditComponent("Колличество экземпляров", this.getWidth(), 30, 50);
        this.add(quantityComponent);
        listCategoriesComponent = new ListCategoriesComponent("категории", this.getWidth(), 120, 300);
        this.add(listCategoriesComponent);
        buttonComponent = new ButtonComponent("Добавть книгу", this.getWidth(), 30, 350, 150);
        this.add(buttonComponent);
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuiApp().setVisible(true);
            }
        });
    }
}
