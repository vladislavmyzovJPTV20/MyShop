/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import gui.GuiApp;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GuestComponent extends JPanel{
    private ListProductsComponent listProductsComponent;
    
    public GuestComponent(int widthWindow, int heightPanel) {
        initComponents(widthWindow, heightPanel);
    }

    private void initComponents(int widthWindow, int heightPanel) {
       this.setPreferredSize(new Dimension(widthWindow,heightPanel));
       this.setMinimumSize(this.getPreferredSize());
       this.setMaximumSize(this.getPreferredSize());
       listProductsComponent = new ListProductsComponent(false, "Список продуктов магазина", GuiApp.HEIGHT_WINDOW, GuiApp.HEIGHT_WINDOW - 100, GuiApp.WIDTH_WINDOW);
       this.add(Box.createRigidArea(new Dimension(0,10)));
       this.add(listProductsComponent);
       this.add(Box.createRigidArea(new Dimension(0,10)));
    }

}
