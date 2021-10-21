/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myshop;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import entity.Customer;
import entity.History;
import entity.Product;
import interfaces.Keeping;
import tools.SaverToFile;

/**
 *
 * @author Влад
 */
public class App {
    private Scanner scanner = new Scanner(System.in);
    private Product[] products = new Product[10];
    private Customer[] customers = new Customer[10];
    private History[] histories = new History[10];
    private Keeping keeper = new SaverToFile();

    public App() {
        products = keeper.loadProducts();
        customers = keeper.loadCustomers();
        histories = keeper.loadHistory();
    }
    
    
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
                            keeper.saveProducts(products);
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
                            keeper.saveCustomers(customers);
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
                case 5:
                    System.out.println("----- Купить продукт -----");
                    for (int i = 0; i < histories.length; i++) {
                        if(histories[i] == null) {
                            histories[i] = addHistory();
                            keeper.saveHistories(histories);
                            break;
                        }
                    }
                    break;
                case 6:
                    System.out.println("----- Список купленных продуктов -----");
                    printGivenProducts();
                    System.out.println("*******************************************");
                    break;
                case 7:
                    System.out.println("----- Возврат просроченного продукта -----");
                    System.out.println("*******************************************");
                    System.out.println("----- Список продуктов -----");
                    printGivenProducts();
                    System.out.print("Введите номер продукта, который хотите вернуть: ");
                    int ProductNumber = scanner.nextInt(); scanner.nextLine();
                    Calendar c = new GregorianCalendar();
                    histories[ProductNumber - 1].setOverdueDate(c.getTime());
                    System.out.printf("Продукт \"%s\" возвращён%n",histories[ProductNumber - 1].getProduct().getProductname());
                    System.out.println("*******************************************");
                    break;
                default:
                    System.out.println("Введите номер из списка!");
            }
            
        }while("r".equals(repeat));
        
    }
    
    private void printGivenProducts() {
        int n = 0;
        for (int i = 0; i < histories.length; i++) {
            if(histories[i] != null) {
                System.out.printf("%d. Покупатель %s %s купил %s. Дата покупки: %s. %n",
                        i + 1,
                        histories[i].getCustomer().getFirstname(),
                        histories[i].getCustomer().getLastname(),
                        histories[i].getProduct().getProductname(),
                        histories[i].getPurchaseDate().toString());
                n++;
            }
            if(n < 1) {
                System.out.println("Купленных товаров нет!");
            }
        }
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
        
        System.out.print("Введите номер продукта: ");
        int numberProduct = scanner.nextInt(); scanner.nextLine();
        
        System.out.println("Пронумерованный список покупателей");
        for (int i = 0; i < customers.length; i++) {
            if(customers[i] != null) {
                System.out.println(i+1+". "+customers[i].toString());
            }
        }
        
        System.out.print("Введите номер покупателей: ");
        int numberCustomer = scanner.nextInt(); scanner.nextLine();
        
        history.setCustomer(customers[numberCustomer-1]);
        history.setProduct(products[numberProduct-1]);
        Calendar c = new GregorianCalendar();
        history.setPurchaseDate(c.getTime());
        return history;
    }
}
