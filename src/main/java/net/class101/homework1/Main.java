package net.class101.homework1;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.global.db.InsertTestData;
import net.class101.homework1.domain.model.Printer;
import net.class101.homework1.domain.order.controller.OrderController;
import net.class101.homework1.domain.order.domain.OrderHistory;
import net.class101.homework1.domain.order.domain.OrderHistoryPrinter;
import net.class101.homework1.domain.product.application.ProductService;
import net.class101.homework1.domain.product.controller.ProductController;
import net.class101.homework1.domain.product.dao.ProductRepository;
import net.class101.homework1.domain.product.domain.Product;
import net.class101.homework1.domain.product.domain.ProductPrinter;
import net.class101.homework1.domain.product.dto.ViewProduct;
import net.class101.homework1.global.common.Domain;
import net.class101.homework1.global.common.Response;
import net.class101.homework1.global.common.StatusCode;
import net.class101.homework1.global.util.PropertiesUtil;
import net.class101.homework1.global.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RequiredArgsConstructor
public class Main {
    private static ProductController productController;
    private static OrderController orderController;

    public static void main(String[] args) {
        InsertTestData.initData();
        dependencyInjection();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print(PropertiesUtil.getMessage(Domain.MAIN.getDomain(), "main.order.and.quit"));
                String orderOrQuit = StringUtil.getTrimString(br.readLine());

                Response orderResponse = orderController.orderOrQuit(orderOrQuit);

                if (orderResponse.getCode() == StatusCode.EXIT.getCode()) {
                    System.out.println(orderResponse.getMessage());
                    System.exit(0);
                }

                if (orderResponse.getCode() == StatusCode.BAD_REQUEST.getCode()) {
                    System.out.println(orderResponse.getMessage());
                    continue;
                }

                Response responseProductList = productController.findAll();
                Printer productPrinter = new ProductPrinter((List<ViewProduct>) responseProductList.getBody());
                System.out.println(productPrinter.print());

                OrderHistory orderHistory = new OrderHistory();
                while (true) {
                    System.out.print(PropertiesUtil.getMessage(Domain.MAIN.getDomain(), "main.product.id"));
                    String number = StringUtil.getTrimString(br.readLine());

                    if (number.isEmpty()) {
                        OrderHistoryPrinter orderHistoryPrinter = new OrderHistoryPrinter(orderHistory);
                        System.out.println(orderHistoryPrinter.print());

                        orderHistory.clear();
                        break;
                    }

                    System.out.print(PropertiesUtil.getMessage(Domain.MAIN.getDomain(), "main.product.count"));
                    String count = StringUtil.getTrimString(br.readLine());

                    if (StringUtil.isNotNumeric(number) || StringUtil.isNotNumeric(count)) {
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

                    Response responseAddOrder = orderController.addOrder(orderHistory, chooseProduct, productCount);
                    if (responseAddOrder.getCode() == StatusCode.BAD_REQUEST.getCode()) {
                        System.out.println(responseAddOrder.getMessage());
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dependencyInjection() {
        ProductRepository productRepository = new ProductRepository(Product.class);
        ProductService productService = new ProductService(productRepository);
        productController = new ProductController(productService);
        orderController = new OrderController();
    }
}