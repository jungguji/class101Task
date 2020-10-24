package net.class101.homework1;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.domain.db.InsertTestData;
import net.class101.homework1.domain.order.controller.OrderController;
import net.class101.homework1.domain.order.domain.OrderHistory;
import net.class101.homework1.domain.order.domain.OrderHistoryPrinter;
import net.class101.homework1.domain.product.application.ProductService;
import net.class101.homework1.domain.product.controller.ProductController;
import net.class101.homework1.domain.product.dao.ProductRepository;
import net.class101.homework1.domain.product.domain.Product;
import net.class101.homework1.global.common.Response;
import net.class101.homework1.global.common.StatusCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class Main {
    public static final String EMPTY = " ";
    public static final String REGEX_NUMERIC = "^[0-9]*$";

    private static ProductController productController;
    private static OrderController orderController;

    public static void main(String[] args) {
        initData();
        dependencyInjection();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
                String orderOrQuit = br.readLine();

                Response orderResponse = orderController.orderOrQuit(orderOrQuit);

                if (orderResponse.getCode() == StatusCode.EXIT.getCode()) {
                    System.out.println(orderResponse.getMessage());
                    System.exit(0);
                }

                if (orderResponse.getCode() == StatusCode.BAD_REQUEST.getCode()) {
                    System.out.println(orderResponse.getMessage());
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

                    if (response.getCode() == StatusCode.NOT_FOUND.getCode()) {
                        System.out.println(response.getMessage());
                        continue;
                    }

                    Product chooseProduct = (Product) response.getBody();
                    orderHistory.addOrder(chooseProduct, productCount);
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
        boolean isNumeric = Pattern.matches(REGEX_NUMERIC, str.trim());

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