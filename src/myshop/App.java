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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import tools.SaverToFile;

/**
 *
 * @author Влад
 */
public class App {
    private Scanner scanner = new Scanner(System.in);
    private List<Product> products = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<History> histories = new ArrayList<>();
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
                    products.add(addProduct());
                    keeper.saveProducts(products);
                    break;
                case 2:
                    System.out.println("----- Список продуктов -----");
                    printListProducts();
                    break;
                case 3:
                    System.out.println("----- Добавление покупателя -----");
                    customers.add(addCustomer());
                    keeper.saveCustomers(customers);
                    break;
                case 4:
                    System.out.println("----- Список покупателей -----");
                    for (int i = 0; i < customers.size(); i++) {
                        if(customers.get(i) != null) {
                            System.out.println(customers.get(i).toString());
                        }
                    }
                    break;
                case 5:
                    System.out.println("----- Купить продукт -----");
                    History history = addHistory();
                    if(history != null) {
                        histories.add(history);
                        keeper.saveHistories(histories);
                        keeper.saveProducts(products);
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
                    if(!printGivenProducts()){
                        break;
                    }
                    System.out.print("Введите номер продукта, который хотите вернуть: ");
                    int ProductNumber = scanner.nextInt(); scanner.nextLine();
                    Calendar c = new GregorianCalendar();
                    if(histories.get(ProductNumber - 1).getProduct().getCount() < histories.get(ProductNumber - 1).getProduct().getQuantity()) {
                        histories.get(ProductNumber - 1).setOverdueDate(c.getTime());
                        histories.get(ProductNumber - 1).getProduct().setCount(histories.get(ProductNumber - 1).getProduct().getCount()+1);
                        
                    }else{
                        System.out.println("Все экземпляыры продукта в магазине");
                        break;
                    }
                    
                    keeper.saveHistories(histories);
                    keeper.saveProducts(products);
                    System.out.printf("Продукт \"%s\" возвращен%n", histories.get(ProductNumber - 1).getProduct().getProductname());
                    break;
                default:
                    System.out.println("Введите номер из списка!");
            }
            
        }while("r".equals(repeat));
        
    }
    
    private boolean printGivenProducts() {
        int n = 0;
        for (int i = 0; i < histories.size(); i++) {
            if (histories.get(i) != null && histories.get(i).getOverdueDate() == null && histories.get(i).getProduct().getCount() < histories.get(i).getProduct().getQuantity()) {
                System.out.printf("%d. Покупатель %s %s купил %s. Дата покупки: %s. %n",
                        i+1,
                        histories.get(i).getCustomer().getFirstname(),
                        histories.get(i).getCustomer().getLastname(),
                        histories.get(i).getProduct().getProductname(),
                        histories.get(i).getPurchaseDate().toString());
                        System.out.println("Предполагаемое время возврата книги: "+histories.get(i).getLocalReturnedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                n++;
            }
        }
        if(n < 1) {
            System.out.println("Купленных товаров нет!");
            return false;
        }
        return true;
    }
    
    private Product addProduct() {
        Product product = new Product();
        System.out.println("Введите название продукта: ");
        product.setProductname(scanner.nextLine());
        System.out.println("Введите цену продукта: ");
        product.setPrice(scanner.nextDouble()); scanner.nextLine();
        System.out.print("Введите количество экзамепляров продукта: ");
        product.setQuantity(scanner.nextInt()); scanner.nextLine();
        product.setCount(product.getQuantity());
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
        Product product = new Product();
        /**
         * 1. Вывести пронумерованный список продуктов магазина
         * 2. Попросить пользователя выбрать номер продукта
         * 3. Вывести пронумерованный список покупателей
         * 4. Попросить пользователя выбрать номер покупателя
         * 5. Сгенерировать текущую дату покупки продукта
         * 6. Инициировать объект History (задать состояние)
         */
        System.out.print("Пронумерованный список продуктов магазина: ");
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i) != null && products.get(i).getCount() > 0) {
                System.out.println(i+1+". "+products.get(i).toString());
                
                System.out.print("Введите номер продукта: ");
                int numberProduct = scanner.nextInt(); scanner.nextLine();
        
                System.out.println("Пронумерованный список покупателей");
                for (int j = 0; j < customers.size(); j++) {
                    if(customers.get(j) != null) {
                        System.out.println(j+1+". "+customers.get(j).toString());
                    }
                }
        
                System.out.print("Введите номер покупателей: ");
                int numberCustomer = scanner.nextInt(); scanner.nextLine();
        
                history.setProduct(products.get(numberProduct - 1));
                if(history.getProduct().getCount() > 0){
                    history.getProduct().setCount(history.getProduct().getCount() - 1);
                }
                history.setCustomer(customers.get(numberCustomer - 1));
                Calendar c = new GregorianCalendar();
                history.setPurchaseDate(c.getTime());
                LocalDate localdate = LocalDate.now();
                localdate = localdate.plusWeeks(2);
                history.setLocalReturnedDate(localdate);
            }else{
                System.out.print("пуст");
                break;
            }
        }
        return history;
    }
    
    private void printListProducts() {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i) != null && products.get(i).getCount() > 0) {
                System.out.println(products.get(i).toString());
            }
        }
    }
}
