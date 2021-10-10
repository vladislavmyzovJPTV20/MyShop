/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myshop;

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
}
