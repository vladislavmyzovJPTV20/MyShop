/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.renderers;

import entity.Category;
import entity.Product;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.UIManager;

/**
 *
 * @author Melnikov
 */
public class ListProductsCellRenderer extends DefaultListCellRenderer{
    private final Color background = new Color(0, 100, 255, 15);
    private final Color defaultBackground = (Color) UIManager.get("List.background");
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,int index,
                                                    boolean isSelected, boolean cellHasFocus){
        Component component = super.getListCellRendererComponent(list, value, index, 
                isSelected, cellHasFocus);
            if(component instanceof JLabel){
                JLabel label = (JLabel) component;
                Product product = (Product) value;
                if(product == null) return component;
                StringBuilder sb = new StringBuilder();
                for (Category category : product.getCategory()) {
                    sb.append(category.getCategoryName())
                      .append(". ");
                }
                label.setText(String.format("%s. %s %d."
                        ,product.getProductname()
                        ,sb.toString()
                        ,product.getQuantity()
                ));
                if(!isSelected){
                    label.setBackground(index % 2 == 0 ? background : defaultBackground);
                }
            }
            return component;                                            
        }
    
}
