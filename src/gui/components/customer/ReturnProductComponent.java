/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.customer;

import gui.components.*;
import entity.Category;
import entity.Product;
import entity.History;
import facade.ProductFacade;
import facade.HistoryFacade;
import gui.GuiApp;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


/**
 *
 * @author Melnikov
 */
public class ReturnProductComponent extends JPanel{
    private HistoryFacade historyFacade;
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private ListHistoriesComponent listHistoriesComponent;
    private EditComponent nameBookComponent;
    private ListCategoriesComponent listCategoriesComponent;
    private EditComponent publishedYearComponent;
    private EditComponent quantityComponent;
    private ButtonComponent buttonComponent;
    public ReturnProductComponent() {
        historyFacade = new HistoryFacade(History.class);
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW, GuiApp.HEIGHT_WINDOW));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setBorder(BorderFactory.createLineBorder(Color.yellow));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Возврат просроченного продукта в магазин", GuiApp.WIDTH_WINDOW, 30);
        captionComponent.setBorder(BorderFactory.createLineBorder(Color.red));
        this.add(captionComponent);
        infoComponent = new InfoComponent("", GuiApp.WIDTH_WINDOW,27);
        infoComponent.setBorder(BorderFactory.createLineBorder(Color.red));
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        listHistoriesComponent = new ListHistoriesComponent(false,"Купленные продукты", 10, GuiApp.HEIGHT_WINDOW - 50, GuiApp.WIDTH_WINDOW);
        this.add(listHistoriesComponent);
        buttonComponent = new ButtonComponent("Вернуть продукт",GuiApp.WIDTH_WINDOW, 30, 200,150);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<History> historyReturnBooks = listHistoriesComponent.getList().getSelectedValuesList(); 
                if(historyReturnBooks.isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Выберите название продукта");
                    return;
                }
                for (int i = 0; i < historyReturnBooks.size(); i++) {
                    historyReturnBooks.get(i).setOverdueDate(Calendar.getInstance().getTime());
                    historyFacade.edit(historyReturnBooks.get(i));
                }
            }
    });
}
    
}
