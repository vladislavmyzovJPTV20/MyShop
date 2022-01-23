/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.manager;

import gui.components.customer.*;
import gui.components.*;
import entity.Category;
import entity.Product;
import facade.ProductFacade;
import gui.GuiApp;
import static gui.GuiApp.HEIGHT_WINDOW;
import static gui.GuiApp.WIDTH_WINDOW;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Melnikov
 */
public class ManagerComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent nameBookComponent;
    private ListCategoriesComponent listCategoriesComponent;
    private EditComponent publishedYearComponent;
    private EditComponent quantityComponent;
    private ButtonComponent buttonComponent;
    public ManagerComponent() {
        initComponents();
    }

    private void initComponents() {
        JTabbedPane readerTabbed = new JTabbedPane();
        readerTabbed.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
        readerTabbed.setMinimumSize(readerTabbed.getPreferredSize());
        readerTabbed.setMaximumSize(readerTabbed.getPreferredSize());
        this.add(readerTabbed);
        readerTabbed.addTab("Добавить продукт", new TabAddProductComponent());
        readerTabbed.addTab("Редактировать продукт", new EditProductComponent());
        readerTabbed.addTab("Добавить категорию", new TabAddCategoryComponent());
        readerTabbed.addTab("Редактировать категорию", new EditCategoryComponent());
    }

    public InfoComponent getInfoComponent() {
        return infoComponent;
    }
       
    
    
}
