/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.director;

import gui.GuiApp;
import gui.components.customer.*;
import gui.components.*;
import static gui.GuiApp.HEIGHT_WINDOW;
import static gui.GuiApp.WIDTH_WINDOW;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class DirectorComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent nameBookComponent;
    private EditComponent publishedYearComponent;
    private EditComponent quantityComponent;
    private ButtonComponent buttonComponent;
    private ListCategoriesComponent listCategoriesComponent;
    
    public DirectorComponent() {
        initComponents();
    }    

    private void initComponents() {
        JTabbedPane readerTabbed = new JTabbedPane();
        readerTabbed.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
        readerTabbed.setMinimumSize(readerTabbed.getPreferredSize());
        readerTabbed.setMaximumSize(readerTabbed.getPreferredSize());
        this.add(readerTabbed);
        readerTabbed.addTab("Добавить покупателя", new TabAddCustomerComponent());
        readerTabbed.addTab("Изменить покупателя", new EditCustomerComponent());
        readerTabbed.addTab("Изменить роль", new EditRoleComponent()); 
    }

    public InfoComponent getInfoComponent() {
        return infoComponent;
    }
    
}
