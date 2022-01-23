/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import gui.GuiApp;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JPanel;

/**
 *
 * @author Melnikov
 */
public class GuestComponent extends JPanel{
    private ListProductsComponent listProductsComponent;
    public GuestComponent() {
        initComponents(GuiApp.HEIGHT_WINDOW);
    }

    public GuestComponent(int heightList) {
        initComponents(heightList);
    }

    private void initComponents(int heightList) {
       this.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
       this.setMinimumSize(this.getPreferredSize());
       this.setMaximumSize(this.getPreferredSize());
       listProductsComponent = new ListProductsComponent(false, "Список продуктов магазина", 0, heightList, GuiApp.WIDTH_WINDOW);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(listProductsComponent);
    }

    public ListProductsComponent getListProductsComponent() {
        return listProductsComponent;
    }

    public void setListProductsComponent(ListProductsComponent listProductsComponent) {
        this.listProductsComponent = listProductsComponent;
    }
    
}
