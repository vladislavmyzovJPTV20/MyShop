/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import entity.Category;
import facade.CategoryFacade;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Влад
 */
public class ListCategoriesComponent extends JPanel{
    private JLabel title;
    private JList<Category> list;
    public ListCategoriesComponent(String text, int widthWindow, int heightPanel, int widthEditor) {
        initComponents(text, widthWindow, heightPanel,widthEditor);
    }

    private void initComponents(String text, int widthWindow, int heightPanel,int widthEditor) {
       this.setPreferredSize(new Dimension(widthWindow,heightPanel));
       this.setMinimumSize(this.getPreferredSize());
       this.setMaximumSize(this.getPreferredSize());
       this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
       title = new JLabel(text);
       title.setPreferredSize(new Dimension(widthWindow/3,27));
       title.setMinimumSize(title.getPreferredSize());
       title.setMaximumSize(title.getPreferredSize());
       title.setHorizontalAlignment(JLabel.RIGHT);
       title.setAlignmentY(TOP_ALIGNMENT);
       title.setFont(new Font("Tahoma",0,12));
       this.add(title);
       this.add(Box.createRigidArea(new Dimension(5,0)));
       list = new JList<>();
       list.setModel(getListModel());
       list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
       list.setLayoutOrientation(JList.HEIGHT);
       JScrollPane scrollPane = new JScrollPane(list);
       scrollPane.setPreferredSize(new Dimension(widthEditor,heightPanel));
       scrollPane.setMinimumSize(scrollPane.getPreferredSize());
       scrollPane.setMaximumSize(scrollPane.getPreferredSize());
       scrollPane.setAlignmentX(LEFT_ALIGNMENT);
       scrollPane.setAlignmentY(TOP_ALIGNMENT);
       this.add(scrollPane);
    }

    private ListModel<Category> getListModel() {
        CategoryFacade categoryFacade = new CategoryFacade(Category.class);
        List<Category> categories = categoryFacade.findAll();
        DefaultListModel<Category> defaultListModel = new DefaultListModel<>();
        for (Category category : categories) {
            defaultListModel.addElement(category);
        }
        return defaultListModel;
    }

    public JList<Category> getList() {
        return list;
    }
    
}