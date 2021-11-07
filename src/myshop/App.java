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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    
    
     public void run() {
        String repeat = "r";
        do {

            System.out.println("Выберите номер задачи: ");
            System.out.println("0: Выход из программы");
            System.out.println("1: Добавить продукт");
            System.out.println("2: Список продуктов");
            System.out.println("3: Добавить покупателя");
            System.out.println("4: Список покупателей");
            System.out.println("5: Купить продукт");
            System.out.println("6: Список купленных продуктов");
            System.out.println("7: Возврат просроченного продукта");

            int task = scanner.nextInt();
            scanner.nextLine();
            switch (task) {
                case 0:
                    repeat = "q";
                    System.out.println("Пока! :)");
                    break;
                case 1:
                    System.out.println("---- Добавить продукт ----");
                    addProduct();
                    System.out.println("-----------------------");
                    break;
                case 2:
                    System.out.println("---- Список продуктов -----");
                    printListProducts();
                    System.out.println("-----------------------");
                    break;
                case 3:
                    addCustomer();
                    break;
                case 4:
                    printListCustomers();
                    break;
                case 5:
                    addHistory();
                    System.out.println("-----------------------");
                    break;
                case 6:
                    System.out.println("----- Список купленных продуктов -----");
                    printGivenProducts();
                    System.out.println("-----------------------");
                    break;
                case 7:
                    System.out.println("---- Возврат просроченного продукта -----");
                    returnProduct();
                    break;
                default:
                    System.out.println("Введите номер из списка!");
            }

        } while ("r".equals(repeat));
    }
    private boolean quit(){
        System.out.println("Чтобы закончить операцию нажмите \"q\", для продолжения любой другой символ");
        String quit = scanner.nextLine();
        if("q".equals(quit)) return true;
        return false;
    }
    
    private void returnProduct() {
        System.out.println("Вернуть просроченный продукт: ");
        if(quit()) return;
        Set<Integer> numbersGivenProducts = printGivenProducts();
        if(numbersGivenProducts.isEmpty()){
            return;
        }
        int historyNumber = insertNumber(numbersGivenProducts);
        Calendar c = new GregorianCalendar();
        histories.get(historyNumber - 1).setOverdueDate(c.getTime());
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getProductname().equals(histories.get(historyNumber-1).getProduct().getProductname())){
                products.get(i).setCount(products.get(i).getCount()+1);
            }
        }
        keeper.saveProducts(products);
        keeper.saveHistories(histories);
    }

  private Set<Integer> printGivenProducts(){
        System.out.println("Список купленных продуктов: ");
        Set<Integer> setNumberGivenProducts = new HashSet<>();
        for (int i = 0; i < histories.size(); i++) {
            //если history не null и книга не возварщена и книг в наличии меньше
            // чем записано в quantity -
            // печатаем книгу
            if(histories.get(i) != null 
                    && histories.get(i).getOverdueDate() == null
                    && histories.get(i).getProduct().getCount()
                        <histories.get(i).getProduct().getQuantity()
                    ){
                System.out.printf("%d. Продукт %s купил %s %s.",
                        i+1,
                        histories.get(i).getProduct().getProductname(),
                        histories.get(i).getCustomer().getFirstname(),
                        histories.get(i).getCustomer().getLastname()
                );
                setNumberGivenProducts.add(i+1);
            }
        }
        if(setNumberGivenProducts.isEmpty()){
            System.out.println("Купленных продуктов нет");
        }
        return setNumberGivenProducts;
    }

    private void addProduct() {
        Product product = new Product();
        System.out.print("Введите название продукта: ");
        product.setProductname(scanner.nextLine());
        System.out.print("Введите стоимость продукта: ");
        product.setPrice(getNumber());
        System.out.print("Введите количество экзамепляров продукта: ");
        product.setQuantity(getNumber());
        product.setCount(product.getQuantity());
        
        products.add(product);
        keeper.saveProducts(products);
    }

    private void addCustomer() {
        Customer customer = new Customer();
        System.out.println("Введите имя покупателя: ");
        customer.setFirstname(scanner.nextLine());
        System.out.println("Введите фамилию покупателя: ");
        customer.setLastname(scanner.nextLine());
        System.out.println("Введите количество денег покупателя: ");
        customer.setMoney(getNumber());
        customers.add(customer);
        keeper.saveCustomers(customers);
    }

    private void addHistory() {
        History history = new History();
        Product product = new Product();
        /**
         * 1. Вывести пронумерованный список книг библиотеки
         * 2. Попросить пользователя выбрать номер книги 
         * 3. Вывести пронумерованный список читателей
         * 4. Попросить пользователя выбрать номер читателя
         * 5. Сгенерировать текущую дату выдачи 6. Инициировать объект History (задать состояние)
         */
        
        System.out.println("-------- Купить продукт --------");
        
        System.out.println("Список продуктов: ");
        Set<Integer> setNumbersProducts = printListProducts();
        if(setNumbersProducts.isEmpty()){
            return;
        }
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i) != null && products.get(i).getCount() > 0) {
                System.out.println(i + 1 + ". " + products.get(i).toString());
                
                System.out.print("Введите номер продукта: ");
                int numberProduct = insertNumber(setNumbersProducts);

                System.out.println("Список покупателей: ");
                Set<Integer> setNumbersCustomers = printListCustomers();
                System.out.print("Введите номер покупателя: ");
                int numberCustomer = insertNumber(setNumbersCustomers);
                history.setProduct(products.get(numberProduct - 1));
                if(products.get(numberProduct - 1).getCount() > 0){
                    products.get(numberProduct - 1).setCount(products.get(numberProduct - 1).getCount() - 1);
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
        keeper.saveProducts(products);
        histories.add(history);
        keeper.saveHistories(histories);
    }
    
    private Set<Integer> printListProducts() {
        System.out.println("Список продуктов: ");
        products = keeper.loadProducts();
        Set<Integer> setNumbersProducts = new HashSet<>();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i) != null && products.get(i).getCount() > 0) {
                System.out.printf("%d. %s. В наличии экземпляров: %d%n",i+1,products.get(i).getProductname(),products.get(i).getCount());
                setNumbersProducts.add(i+1);
            }else if(products.get(i) != null){
                System.out.printf("%d. Продукта %s нет в наличии.",i+1,products.get(i).getProductname());
                System.out.println("Предполагаемая дата возврата книги: "+histories.get(i).getLocalReturnedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
        }
        return setNumbersProducts;
    }

    private int getNumber() {
        do{
            try{
                String strNumber = scanner.nextLine();
                return Integer.parseInt(strNumber);
            } catch (Exception e) {
                System.out.println("Попробуй еще раз: ");
            }
        }while(true);
    }
    
    private int insertNumber(Set<Integer> setNumbers) {
        do{
            int historyNumber = getNumber();
            if(setNumbers.contains(historyNumber)){
                return historyNumber;
            }
            System.out.println("Попробуй еще раз: ");
        }while(true);
    }
    
    private Set<Integer> printListCustomers() {
        Set<Integer> setNumbersCustomers = new HashSet<>();
        System.out.println("Список покупателей: ");
        for (int i = 0; i < customers.size(); i++) {
            if(customers.get(i) != null){
                System.out.printf("%d. %s%n"
                        ,i+1
                        ,customers.get(i).toString()
                );
                setNumbersCustomers.add(i+1);
            }
        }
        return setNumbersCustomers;
    }
}
