/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.customer;

import gui.components.*;
import entity.Category;
import entity.Product;
import facade.ProductFacade;
import gui.GuiApp;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TakeProductComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent nameBookComponent;
    private EditComponent publishedYearComponent;
    private EditComponent quantityComponent;
    private ButtonComponent buttonComponent;
    private ListCategoriesComponent listCategoriesComponent;
    private GuestComponent guestComponent;
    
    public TakeProductComponent() {
        initComponents();
    }    

    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Выберите продукты для покупки", GuiApp.WIDTH_WINDOW, 30);
        this.add(captionComponent);
        infoComponent = new InfoComponent("", GuiApp.WIDTH_WINDOW,30);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,5)));
        this.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        guestComponent = new GuestComponent(200);
        this.add(guestComponent);
        this.add(Box.createRigidArea(new Dimension(0,5)));        
        buttonComponent = new ButtonComponent("Купить продукт", GuiApp.WIDTH_WINDOW, 20, 200, 200);
        this.add(buttonComponent);
    }
}
