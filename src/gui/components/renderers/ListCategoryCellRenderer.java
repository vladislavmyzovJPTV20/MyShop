/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.renderers;

import entity.Category;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.UIManager;

/**
 *
 * @author Влад
 */
public class ListCategoryCellRenderer extends DefaultListCellRenderer{

    private final Color background = new Color(0, 100, 255, 15);
    private final Color defaultBackground = (Color) UIManager.get("List.background");
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(component instanceof JLabel) {
                JLabel label = (JLabel) component;
                Category category = (Category) value;
                label.setText(String.format("%d. %s"
                    ,category.getId()
                    ,category.getCategoryName()
                ));
            if(!isSelected) {
                label.setBackground(index % 2 == 0 ? background : defaultBackground);
            }
        }
            return component;
    }  
}