/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.customer;

import gui.GuiApp;
import static gui.GuiApp.HEIGHT_WINDOW;
import static gui.GuiApp.WIDTH_WINDOW;
import gui.components.*;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class CustomerComponent extends JPanel{
    private InfoComponent infoComponent;

    public CustomerComponent() {
        initComponents();
    }

    private void initComponents() {
        JTabbedPane customerTabbed = new JTabbedPane();
        customerTabbed.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
        customerTabbed.setMinimumSize(customerTabbed.getPreferredSize());
        customerTabbed.setMaximumSize(customerTabbed.getPreferredSize());
        this.add(customerTabbed);
        customerTabbed.addTab("Взять продукт", new TakeProductComponent());
        customerTabbed.addTab("Вернуть продукт", new ReturnProductComponent());    
    }

    public InfoComponent getInfoComponent() {
        return infoComponent;
    }

    
}

