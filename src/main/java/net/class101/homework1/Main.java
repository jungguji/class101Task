package net.class101.homework1;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.domain.db.InsertTestData;
import net.class101.homework1.domain.order.domain.Order;
import net.class101.homework1.domain.order.domain.OrderHistory;
import net.class101.homework1.domain.order.domain.OrderHistoryPrinter;
import net.class101.homework1.domain.product.application.ProductService;
import net.class101.homework1.domain.product.controller.ProductController;
import net.class101.homework1.domain.product.dao.ProductRepository;
import net.class101.homework1.domain.product.domain.Product;
import net.class101.homework1.global.cmmon.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class Main {
    public static final String EXIT = "q";
    public static final String ORDER = "o";
    public static final String EMPTY = " ";
    public static final String REGEX_NUMERIC = "^[0-9]*$";

    private static ProductController productController;

    public static void main(String[] args) {
        initData();
        dependencyInjection();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
                String orderOrQuit = br.readLine();

                if (EXIT.equals(orderOrQuit)) {
                    System.out.println("고객님의 주문 감사합니다.");
                    System.exit(0);
                }

                if (!ORDER.equals(orderOrQuit)) {
                    System.out.println("잘못된 입력입니다.");
                    continue;
                }

                OrderHistory orderHistory = new OrderHistory();
                while (true) {

                    System.out.print("상품번호 : ");
                    String number = br.readLine();

                    if (EMPTY.equals(number)) {
                        OrderHistoryPrinter orderHistoryPrinter = new OrderHistoryPrinter(orderHistory);
                        System.out.println(orderHistoryPrinter.print());

                        orderHistory.clear();
                        break;
                    }

                    System.out.print("수량 : ");
                    String count = br.readLine();

                    if (!isNumeric(number) || !isNumeric(count)) {
                        continue;
                    }

                    int productId = Integer.parseInt(number);
                    int productCount = Integer.parseInt(count);

                    Response response = productController.update(productId, productCount);

                    if (response.getCode() == 400) {
                        System.out.println(response.getMessage());
                        continue;
                    }

                    Product chooseProduct = (Product) response.getBody();

                    Order order = orderHistory.getOrderOrCreate(productId, chooseProduct.getName(), chooseProduct.getType());
                    order.add(productCount, chooseProduct.getPrice());

                    orderHistory.addOrder(order);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initData() {
        try {
            InsertTestData.createTable();
            InsertTestData.insertTestData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static boolean isNumeric(String str) {
        boolean isNumeric = Pattern.matches(REGEX_NUMERIC, str);

        if (!isNumeric) {
            System.out.println("상품번호와 수량은 자연수만 입력 가능 합니다.");
        }

        return isNumeric;
    }

    private static void dependencyInjection() {
        ProductRepository productRepository = new ProductRepository(Product.class);
        ProductService productService = new ProductService(productRepository);
        productController = new ProductController(productService);
    }
}