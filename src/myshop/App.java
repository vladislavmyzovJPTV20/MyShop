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
import entity.Category;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import tools.SaverToBase;
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
    private List<Category> categories = new ArrayList<>();
    //private Keeping keeper = new SaverToFile();
    private Keeping keeper = new SaverToBase();

    public App() {
        products = keeper.loadProducts();
        categories = keeper.loadCategories();
        //customers = keeper.loadCustomers();
        //histories = keeper.loadHistory();
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
            System.out.println("8: Добавить категорию товара");
            System.out.println("9: Список категорий товаров");
            System.out.println("10: Выборка продуктов по категории");
            System.out.println("11: Выборка продуктов по какому-то слову");

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
                case 8:
                    System.out.println("---- Добавить категорию товара -----");
                    addCategory();
                    break;
                case 9:
                    System.out.println("---- Список категорий товаров -----");
                    printListCategories();
                    break;
                case 10:
                    System.out.println("---- Выборка продуктов по категории -----");
                    selectionOfProductsByCategory();
                    break;
                case 11:
                    System.out.println("---- Выборка продуктов по какому-то слову -----");
                    selectionOfProductsByWord();
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
                System.out.printf("%d. Продукт %s купил %s %s. Срок годности товара: %s",
                        i+1,
                        histories.get(i).getProduct().getProductname(),
                        histories.get(i).getCustomer().getFirstname(),
                        histories.get(i).getCustomer().getLastname(),
                        getOverdueDate(products.get(i))
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
        Set<Integer> setNumbersCategories = printListCategories();
        if(setNumbersCategories.isEmpty()){
            System.out.println("Добавьте категорию!");
            return;
        }
        System.out.print("Если в списке есть категории товаров нажмите 1: ");
        if(getNumber() != 1){
            System.out.println("Введите категорию товара.");
            return;
        }
        System.out.println();
        System.out.print("Введите количество категорий: ");
        int countCategories = getNumber();
        List<Category> categoriesProduct = new ArrayList<>();
        for (int i = 0; i < countCategories; i++) {
            System.out.println("Введите номер категории товара "+(i+1)+" из списка: ");
            int numberCategory = insertNumber(setNumbersCategories);
            categoriesProduct.add(categories.get(numberCategory - 1));
        }
        product.setCategory(categoriesProduct);
        System.out.print("Введите название продукта: ");
        product.setProductname(scanner.nextLine());
        System.out.print("Введите стоимость продукта: ");
        product.setPrice(getNumber());
        System.out.print("Введите количество экземпляров продукта: ");
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
                if(setNumbersCustomers.isEmpty()) {
                    return;
                }
                System.out.print("Введите номер покупателя: ");
                int numberCustomer = insertNumber(setNumbersCustomers);
                history.setProduct(products.get(numberProduct - 1));
                if(products.get(numberProduct - 1).getCount() > 0){
                    products.get(numberProduct - 1).setCount(products.get(numberProduct - 1).getCount() - 1);
                }
                history.setCustomer(customers.get(numberCustomer - 1));
                Calendar c = new GregorianCalendar();
                history.setPurchaseDate(c.getTime());
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
            StringBuilder cbCategories = new StringBuilder();
            for (int j = 0; j < products.get(i).getCategory().size(); j++) {
                cbCategories.append(products.get(i).getCategory().get(j).getCategoryName())
                        .append(". ");
            }
            if(products.get(i) != null && products.get(i).getCount() > 0){
                System.out.printf("%d. %s. %s В наличии экземпряров: %d%n"
                        ,i+1
                        ,products.get(i).getProductname()
                        ,cbCategories.toString()
                        ,products.get(i).getCount()
                );
                setNumbersProducts.add(i+1);
            }else if(products.get(i) != null){
                System.out.printf("%d. %s. %s Нет в наличии.%n"
                        ,i+1
                        ,products.get(i).getProductname()
                        ,cbCategories.toString()
                );
            }
        }
        return setNumbersProducts;
    }
    
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
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
    
    private String getOverdueDate(Product product){
        
        for (int i = 0; i < histories.size(); i++) {
            if(product.getProductname().equals(histories.get(i).getProduct().getProductname())){
                LocalDate localGivenDate = histories.get(i).getPurchaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                localGivenDate = localGivenDate.plusDays(14);
                return localGivenDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }
        }
        return "";
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
        if(setNumbersCustomers.isEmpty()) {
            System.out.println("Добавьте покупателей!");
        }
        return setNumbersCustomers;
    }

    private void addCategory() {
        System.out.println("---- Добавить категорию товара ----");
        Category category = new Category();
        System.out.print("Введите название категории товара: ");
        category.setCategoryName(scanner.nextLine());
        categories.add(category);
        keeper.saveCategories(categories);
        System.out.println("-----------------------");
    }

    private Set<Integer> printListCategories() {
        Set<Integer> setNumbersCategories = new HashSet<>();
        System.out.println("Список категорий товаров: ");
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i) != null){
                System.out.printf("%d. %s%n"
                        ,i+1
                        ,categories.get(i).toString()
                );
                setNumbersCategories.add(i+1);
            }
        }
        return setNumbersCategories;
    }

    private void selectionOfProductsByCategory() {
        System.out.println("----- Выборка продуктов по категории -----");
        Set<Integer> setNumbersCategories = printListCategories();
        if(setNumbersCategories.isEmpty()){
            System.out.println("Список категорий товаров пуст. Добавьте категорию!");
            return;
        }
        System.out.println("Выберите номер категории товара: ");
        Category category = categories.get(insertNumber(setNumbersCategories)-1);
        for (int i = 0; i < products.size(); i++) {
            List<Category>categoriesProduct = products.get(i).getCategory();
            for (int j = 0; j < categoriesProduct.size(); j++) {
                Category categoryProduct = categoriesProduct.get(j);
                if(category.equals(categoryProduct)){
                    System.out.printf("%d. %s.%n"
                            ,i+1
                            ,products.get(i).getProductname()
                    );
                }
                
            }
            
        }
        System.out.println("----------------------------");
    }

    private void selectionOfProductsByWord() {
        System.out.println("Введите часть названия продукта: ");
        String a = scanner.nextLine();
        int n = 0;
        
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getProductname().contains(a)) {
                System.out.printf("Данные символы содержатся в названии продукта/тов: %s %n",products.get(i).getProductname());
                n++;
            }
        }
        if(n == 0) {
            System.out.println("Продуктов, содержащих данные символы - нет!");
        }
    }
}
