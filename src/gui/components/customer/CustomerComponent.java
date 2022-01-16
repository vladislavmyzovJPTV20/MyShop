/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.customer;

import static gui.GuiApp.HEIGHT_WINDOW;
import static gui.GuiApp.WIDTH_WINDOW;
import gui.components.*;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class CustomerComponent extends JPanel{

    public CustomerComponent(int widthWindow, int heightPanel) {
        initComponents(widthWindow, heightPanel);
    }

    private void initComponents(int widthWindow, int heightPanel) {
        JTabbedPane customerTabbed = new JTabbedPane();
        customerTabbed.setPreferredSize(new Dimension(WIDTH_WINDOW,HEIGHT_WINDOW));
        customerTabbed.setMinimumSize(customerTabbed.getPreferredSize());
        customerTabbed.setMaximumSize(customerTabbed.getPreferredSize());
        this.add(customerTabbed);
        customerTabbed.addTab("Взять продукт", new TakeProductComponent(WIDTH_WINDOW, HEIGHT_WINDOW));
        customerTabbed.addTab("Вернуть продукт", new ReturnProductComponent(WIDTH_WINDOW, HEIGHT_WINDOW));    
    }

}

