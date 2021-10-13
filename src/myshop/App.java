/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myshop;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import myclasses.Customer;
import myclasses.History;
import myclasses.Product;

/**
 *
 * @author Влад
 */
public class App {
    private Scanner scanner = new Scanner(System.in);
    private Product[] products = new Product[10];
    private Customer[] customers = new Customer[10];
    
    public void run(){
        String repeat = "r";
        do{
            
            System.out.println("Выберите номер задачи: ");
            System.out.println("0: Выход из программы");
            System.out.println("1: Добавить продукт");
            System.out.println("2: Список продуктов");
            System.out.println("3: Добавление покупателя");
            System.out.println("4: Список покупателей");
            System.out.println("5: Купить продукт");
            System.out.println("6: Список купленных продуктов");
            System.out.println("7. Возврат просроченного продукта");
            System.out.println("8: Расходы");
            
            int task = scanner.nextInt(); scanner.nextLine();
            switch(task) {
                case 0:
                    repeat = "q";
                    System.out.println("Пока! :)");
                    break;
                case 1:
                    System.out.println("----- Добавление продукта -----");
                    for (int i = 0; i < products.length; i++) {
                        if(products[i] == null) {
                            products[i] = addProduct();
                            break;
                        }
                    }
                    break;
                case 2:
                    System.out.println("----- Список продуктов -----");
                    for (int i = 0; i < products.length; i++) {
                        if(products[i] != null) {
                            System.out.println(products[i].toString());
                        }
                    }
                    break;
                case 3:
                    System.out.println("----- Добавление покупателя -----");
                    for (int i = 0; i < customers.length; i++) {
                        if(customers[i] == null) {
                            customers[i] = addCustomer();
                            break;
                        }
                    }
                    break;
                case 4:
                    System.out.println("----- Список покупателей -----");
                    for (int i = 0; i < customers.length; i++) {
                        if(customers[i] != null) {
                            System.out.println(customers[i].toString());
                        }
                    }
                    break;
            }
            
        }while("r".equals(repeat));
        
    }
    
    private Product addProduct() {
        Product product = new Product();
        System.out.println("Введите название продукта: ");
        product.setProductname(scanner.nextLine());
        System.out.println("Введите цену продукта: ");
        product.setPrice(scanner.nextDouble()); scanner.nextLine();
        return product;
    }
    
    private Customer addCustomer() {
        Customer customer = new Customer();
        System.out.println("Введите имя покупателя: ");
        customer.setFirstname(scanner.nextLine());
        System.out.println("Введите фамилию покупателя: ");
        customer.setLastname(scanner.nextLine());
        System.out.println("Введите количество денег данного покупателя: ");
        customer.setMoney(scanner.nextDouble()); scanner.nextLine();
        return customer;
    }
    
    private History addHistory() {
        History history = new History();
        /**
         * 1. Вывести пронумерованный список продуктов магазина
         * 2. Попросить пользователя выбрать номер продукта
         * 3. Вывести пронумерованный список покупателей
         * 4. Попросить пользователя выбрать номер покупателя
         * 5. Сгенерировать текущую дату покупки продукта
         * 6. Инициировать объект History (задать состояние)
         */
        System.out.println("Пронумерованный список продуктов магазина");
        for (int i = 0; i < products.length; i++) {
            if(products[i] != null) {
                System.out.println(i+1+". "+products[i].toString());
            }
        }
        
        System.out.println("Введите номер продукта: ");
        int numberProduct = scanner.nextInt(); scanner.nextLine();
        
        System.out.println("Пронумерованный список покупателей");
        for (int i = 0; i < customers.length; i++) {
            if(customers[i] != null) {
                System.out.println(i+1+". "+customers[i].toString());
            }
        }
        
        System.out.println("Введите номер покупателей: ");
        int numberCustomer = scanner.nextInt(); scanner.nextLine();
        
        history.setCustomer(customers[numberCustomer-1]);
        history.setProduct(products[numberProduct-1]);
        Calendar c = new GregorianCalendar();
        history.setPurchaseDate(c.getTime());
        return history;
    }
}
