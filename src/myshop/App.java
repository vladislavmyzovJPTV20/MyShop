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
import facade.CategoryFacade;
import facade.CustomerFacade;
import facade.HistoryFacade;
import facade.ProductFacade;
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
    private CategoryFacade categoryFacade = new CategoryFacade(Category.class);
    private ProductFacade productFacade = new ProductFacade(Product.class);
    private CustomerFacade customerFacade = new CustomerFacade(Customer.class);
    private HistoryFacade historyFacade = new HistoryFacade(History.class);

    public App() {
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
            System.out.println("12: Редактирование продукта");
            System.out.println("13: Редактирование покупателя");
            System.out.println("14: Редактирование категории");

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
                case 12:
                    System.out.println("---- Редактирование продукта -----");
                    updateProduct();
                    break;
                case 13:
                    System.out.println("---- Редактирование покупателя -----");
                    updateCustomer();
                    break;
                case 14:
                    System.out.println("---- Редактирование категории -----");
                    updateCategory();
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
        History history = historyFacade.find((long) historyNumber);
        Calendar c = new GregorianCalendar();
        history.setOverdueDate(c.getTime());
        Product product = productFacade.find(history.getProduct().getId());
        product.setCount(product.getCount()+1);
        productFacade.edit(product);
        historyFacade.edit(history);
    }

  private Set<Integer> printGivenProducts(){
        System.out.println("Список выданных продуктов: ");
        Set<Integer> setNumberGivenProducts = new HashSet<>();
        List<History> historyesWithGivenBooks = historyFacade.findWithGivenBooks();
        for (int i = 0; i < historyesWithGivenBooks.size(); i++) {
            System.out.printf("%d. Продукт: %s купил %s %s%n",
                    historyesWithGivenBooks.get(i).getId(),
                    historyesWithGivenBooks.get(i).getProduct().getProductname(),
                    historyesWithGivenBooks.get(i).getCustomer().getFirstname(),
                    historyesWithGivenBooks.get(i).getCustomer().getLastname()
            );
            setNumberGivenProducts.add(historyesWithGivenBooks.get(i).getId().intValue());
        }
        if(setNumberGivenProducts.isEmpty()){
            System.out.println("Купленных продуктов нет");
        }
        return setNumberGivenProducts;
    }

    private void addProduct(){
        System.out.println("---- Добавление продукта ----");
        Product product = new Product();
        Set<Integer> setNumbersCategories = printListCategories();
        if(setNumbersCategories.isEmpty()){
            System.out.println("Добавьте для начала категорию!");
            return;
        }
        System.out.print("Если в списке есть категории продуктов нажмите 1: ");
        if(getNumber() != 1){
            System.out.println("Введите категорию.");
            return;
        }
        System.out.println();
        System.out.print("Введите количество категорий: ");
        int countCategories = getNumber();
        List<Category> categoriesProduct = new ArrayList<>();
        for (int i = 0; i < countCategories; i++) {
            System.out.println("Введите номер автора "+(i+1)+" из списка: ");
            int numberCategory = insertNumber(setNumbersCategories);
            categoriesProduct.add(categoryFacade.find((long)numberCategory));
        }
        product.setCategory(categoriesProduct);
        System.out.print("Введите название продукта: ");
        product.setProductname(scanner.nextLine());
        System.out.print("Введите цену продукта: ");
        product.setPrice(getNumber());
        System.out.print("Введите количество экземпляров продуктов: ");
        product.setQuantity(getNumber());
        product.setCount(product.getQuantity());
        productFacade.create(product);
        System.out.println("-----------------------------");
    }

    private void addCustomer() {
        Customer customer = new Customer();
        System.out.println("Введите имя покупателя: ");
        customer.setFirstname(scanner.nextLine());
        System.out.println("Введите фамилию покупателя: ");
        customer.setLastname(scanner.nextLine());
        System.out.println("Введите количество денег покупателя: ");
        customer.setMoney(getNumber());
        customerFacade.create(customer);
    }

    private void addHistory() {
        History history = new History();
        System.out.println("------------ Купить продукт ----------");
        Set<Integer> setNumbersProducts = printListProducts();
        if(setNumbersProducts.isEmpty()){
            return;
        }
        System.out.print("Введите номер книги: ");
        int numberProduct = insertNumber(setNumbersProducts);
        Product product = productFacade.find((long)numberProduct);
        Set<Integer> setNumbersCustomers = printListCustomers();
        if(setNumbersCustomers.isEmpty()){
            return;
        }
        System.out.print("Введите номер покупателя: ");
        int numberCustomer = insertNumber(setNumbersCustomers);
        Customer customer = customerFacade.find((long)numberCustomer);
        history.setProduct(product);
        if(product.getCount() > 0){
            product.setCount(product.getCount()-1);
        }
        history.setCustomer(customer);
        Calendar c = new GregorianCalendar();
        history.setPurchaseDate(c.getTime());
        productFacade.edit(product);
        historyFacade.create(history);
        System.out.println("========================");
    }
    
    private Set<Integer> printListProducts() {
        System.out.println("Список продуктов: ");
        List<Product> products = productFacade.findAll();
        Set<Integer> setNumbersProducts = new HashSet<>();
        for (int i = 0; i < products.size(); i++) {
            StringBuilder cbCategories = new StringBuilder();
            for (int j = 0; j < products.get(i).getCategory().size(); j++) {
                cbCategories.append(products.get(i).getCategory().get(j).getCategoryName())
                        .append(". ");
            }
            if(products.get(i) != null && products.get(i).getCount() > 0){
                System.out.printf("%d. %s. %s В наличии экземпряров: %d%n"
                        ,products.get(i).getId()
                        ,products.get(i).getProductname()
                        ,cbCategories.toString()
                        ,products.get(i).getCount()
                );
                setNumbersProducts.add(i+1);
            }else if(products.get(i) != null){
                System.out.printf("%d. %s. %s Нет в наличии.%n"
                        ,products.get(i).getId()
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
        List<History> histories = historyFacade.findAll();
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
        List<Customer> customers = customerFacade.findAll();
        Set<Integer> setNumbersCustomers = new HashSet<>();
        System.out.println("Список покупателей: ");
        for (int i = 0; i < customers.size(); i++) {
            if(customers.get(i) != null){
                System.out.printf("%d. %s %s. Деньги: %s%n"
                        ,customers.get(i).getId()
                        ,customers.get(i).getFirstname()
                        ,customers.get(i).getLastname()
                        ,customers.get(i).getMoney()
                );
                setNumbersCustomers.add(customers.get(i).getId().intValue());
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
        categoryFacade.create(category);
        System.out.println("-----------------------");
    }

    private Set<Integer> printListCategories() {
        List<Category> categories = categoryFacade.findAll();
        Set<Integer> setNumbersCategories = new HashSet<>();
        System.out.println("Список категорий товаров: ");
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i) != null){
                System.out.printf("%d. Название категории: %s%n"
                        ,categories.get(i).getId()
                        ,categories.get(i).getCategoryName()
                );
                setNumbersCategories.add(categories.get(i).getId().intValue());
            }
        }
        return setNumbersCategories;
    }

    private void selectionOfProductsByCategory() {
        System.out.println("----- Выборка продуктов по категории -----");
        List<Product> products = productFacade.findAll();
        Set<Integer> setNumbersCategories = printListCategories();
        if(setNumbersCategories.isEmpty()){
            System.out.println("Список категорий товаров пуст. Добавьте категорию!");
            return;
        }
        System.out.println("Выберите номер категории товара: ");
        Category category = categoryFacade.find((long)insertNumber(setNumbersCategories));
        for (int i = 0; i < products.size(); i++) {
            List<Category>categoriesProduct = products.get(i).getCategory();
            for (int j = 0; j < categoriesProduct.size(); j++) {
                Category categoryProduct = categoriesProduct.get(j);
                if(category.equals(categoryProduct)){
                    System.out.printf("%d. %s.%n"
                            ,products.get(i).getId()
                            ,products.get(i).getProductname()
                    );
                }
                
            }
            
        }
        System.out.println("----------------------------");
    }

    private void selectionOfProductsByWord() {
        List<Product> products = productFacade.findAll();
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
    
    private void updateProduct() {
        Set<Integer> setNumbersProducts = printListAllProducts();
        if(setNumbersProducts.isEmpty()){
            System.out.println("Нет продуктов в базе данных!");
            return;
        }
        System.out.println("Выберите номер продукта: ");
        int numProduct = insertNumber(setNumbersProducts);
        Set<Integer> setNum = new HashSet<>();
        setNum.add(1);
        setNum.add(2);
        Product products = productFacade.find((long)numProduct);
        System.out.println("Название продукта: "+products.getProductname());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        int change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новое название продукта: ");
            products.setProductname(scanner.nextLine());
        }
        System.out.println("Стоимость обуви: "+products.getPrice());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новую цену продукта: ");
            products.setPrice(scanner.nextDouble());
        }
        System.out.println("Количество экземпляров продукта: "+products.getQuantity());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новое количество продуктов: ");
            int oldCount = products.getCount();
            int oldQuantity = products.getQuantity();
            int newQuantity;
            do {                
                newQuantity = getNumber();
                if(newQuantity >= 0 && newQuantity >= oldQuantity - oldCount){
                    break;
                }
                System.out.println("Попробуй еще (>"+(oldQuantity - oldCount)+"): ");
            } while (true);
            int newCount = oldCount + (newQuantity - oldQuantity);
            products.setQuantity(newQuantity);
            products.setCount(newCount);
        }
        productFacade.edit(products);
    }
    
   private Set<Integer> printListAllProducts() {
        System.out.println("Список продуктов: ");
        List<Product> products = productFacade.findAll();
        Set<Integer> setNumbersProducts = new HashSet<>();
        for (int i = 0; i < products.size(); i++) {
            StringBuilder cbCategories = new StringBuilder();
            for (int j = 0; j < products.get(i).getCategory().size(); j++) {
                cbCategories.append(products.get(i).getCategory().get(j).getCategoryName())
                        .append(". ");
            }
            if(products.get(i) != null && products.get(i).getCount() >= 0){
                System.out.printf("%d. Продукт: %s. Категория продукта: %s. В наличии экземпляров: %d%n"
                        ,products.get(i).getId()
                        ,products.get(i).getProductname()
                        ,cbCategories.toString()
                        ,products.get(i).getCount()
                );
                setNumbersProducts.add(i+1);
            }else if(products.get(i) != null){
                System.out.printf("%d. Продукт %s. Категория продукта: %s. Нет в наличии."
                        ,products.get(i).getId()
                        ,products.get(i).getProductname()
                        ,cbCategories.toString()
                );
            }
        }
        return setNumbersProducts;        
    }

    private void updateCustomer() {
        Set<Integer> setNumbersCustomers = printListCustomers();
        if(setNumbersCustomers.isEmpty()){
            System.out.println("Нет покупателей в базе");
            return;
        }
        System.out.println("Выберите номер покупателя: ");
        int numСustomer = insertNumber(setNumbersCustomers);
        Set<Integer> setNum = new HashSet<>();
        setNum.add(1);
        setNum.add(2);
        Customer customers = customerFacade.find((long)numСustomer);
        System.out.println("Имя покупателя: "+customers.getFirstname());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        int change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новое имя покупателя: ");
            customers.setFirstname(scanner.nextLine());
        }
        System.out.println("фамилия покупателя: "+customers.getLastname());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новую фамилию покупателя: ");
            customers.setLastname(scanner.nextLine());
        }
        System.out.println("Количество денег покупателя: "+customers.getMoney());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новое количество денег покупателя: ");
            customers.setMoney(scanner.nextInt());
        }
        customerFacade.edit(customers);
    }
    
    private void updateCategory() {
        Set<Integer> setNumbersCategories = printListCategories();
        if(setNumbersCategories.isEmpty()){
            System.out.println("Нет категорий в базе");
            return;
        }
        System.out.println("Выберите номер категории: ");
        int numCategory = insertNumber(setNumbersCategories);
        Category categories = categoryFacade.find((long)numCategory);
        Set<Integer> setNum = new HashSet<>();
        setNum.add(1);
        setNum.add(2);
        System.out.println("Название категории: "+categories.getCategoryName());
        System.out.println("Хотите изменить нажмите 1, оставить без изменения 2");
        int change = insertNumber(setNum);
        if(1 == change){
            System.out.println("Введите новое название категории: ");
            categories.setCategoryName(scanner.nextLine());
        }
        categoryFacade.edit(categories);
    }
}
