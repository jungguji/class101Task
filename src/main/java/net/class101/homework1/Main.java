package net.class101.homework1;

import net.class101.homework1.domain.db.InsertTestData;
import net.class101.homework1.domain.order.Order;
import net.class101.homework1.domain.order.OrderHistory;
import net.class101.homework1.domain.product.domain.Product;
import net.class101.homework1.domain.product.domain.ProductRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Main {
    public static final String EXIT = "q";
    public static final String ORDER = "o";
    public static final String EMPTY = " ";
    public static final String REGEX_NUMERIC = "^[0-9]*$";

    private static ProductRepository productRepository = new ProductRepository(Product.class);

    public static void main(String[] args) throws SQLException {
        InsertTestData.insertTestData();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            Map<Integer, Product> entityMap = new HashMap<>();
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
                        //print주문내역();
                        orderHistory = new OrderHistory();
                        break;
                    }

                    System.out.print("수량 : ");
                    String count = br.readLine();

                    if (!isNumeric(number) || !isNumeric(count)) {
                        continue;
                    }

                    int productNumber = Integer.parseInt(number);
                    int productCount = Integer.parseInt(count);

                    Product chooseProduct = entityMap.computeIfAbsent(productNumber, p -> (Product) productRepository.findById(productNumber).orElse(null));

                    if (chooseProduct == null) {
                        System.out.println("해당하는 상품번호의 상품이 존재하지 않습니다.");
                        System.out.println("상품번호를 다시 한번 확인하여 주세요.");
                        continue;
                    }

                    entityMap.put(productNumber, chooseProduct);
                    chooseProduct.update(productCount);

                    Order order = orderHistory.getOrderOrCreate(productNumber, chooseProduct.getName(), chooseProduct.getType());
                    order.add(productCount, chooseProduct.getPrice());

                    orderHistory.addOrder(order);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isNumeric(String str) {
        boolean isNumeric = Pattern.matches(REGEX_NUMERIC, str);

        if (!isNumeric) {
            System.out.println("상품번호와 수량은 자연수만 입력 가능 합니다.");
        }

        return isNumeric;
    }
}