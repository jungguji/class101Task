package net.class101.homework1.domain.product.application;

import net.class101.homework1.domain.db.InsertTestData;
import net.class101.homework1.domain.product.dao.ProductRepository;
import net.class101.homework1.domain.product.domain.Kit;
import net.class101.homework1.domain.product.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductServiceTest {

    ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() throws SQLException {
        productRepository = new ProductRepository(Product.class);
        productService = new ProductService(productRepository);

        InsertTestData.createTable();
        InsertTestData.insertTestData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        InsertTestData.dropTable();
    }

    @Test
    void findById() {
        //given
        //when
        Product product = productService.findById(16374);

        //then
        assertEquals(16374, product.getId());
        assertEquals("KLASS", product.getType());
        assertEquals("스마트스토어로 월 100만원 만들기, 평범한 사람이 돈을 만드는 비법", product.getName());
        assertEquals(151950, product.getPrice());
        assertEquals(99999, product.getStock());
    }

    @Test
    void findById_null() {
        //given
        //when
        Product product = productService.findById(1);
        //then
        assertNull(product);
    }

    @Test
    void findAll() {
        //given
        //when
        List<Product> whens = productService.findAll();

        //then
        assertEquals(20, whens.size());
    }

    @Test
    void update() {
        //given
        Kit kit = new Kit("test", 1000, 10);
        this.productRepository.save(kit);

        //when
        Product when = this.productService.update(kit.getId(), 5);

        //then
        assertEquals(5, when.getStock());
    }

    @Test
    void update_multi_thread() {
        //given
        ExecutorService service = Executors.newFixedThreadPool(3);

        Kit kit = new Kit("test", 1000, 100);
        this.productRepository.save(kit);

        //when
        class Task extends Thread {
            private Product product;
            private Integer count;

            Task(Product product, Integer count) {
                this.product = product;
                this.count = count;
            }

            public void run() {
                while (true) {
                    product.update(count);
                    System.out.println(Thread.currentThread().getName() + " - " + count + " 번째 스레드 남은 갯수 : " + product.getStock());
                }
            }
        }

        Thread task1 = new Task(kit, 70);
        Thread task2 = new Task(kit, 2);
        Thread task3 = new Task(kit, 3);

        task2.start();
        task3.start();
        task1.start();

//        //when
//        service.execute(() -> {
//            while (kit.getStock() > 0) {
//                Product when = this.productService.update(kit.getId(), 3);
//                System.out.println("두 번째 스레드 남은 갯수 : " + when.getStock());
//                sleep();
//            }
//        });
//
//        //when
//        service.execute(() -> {
//            while (kit.getStock() > 0) {
//                Product when = this.productService.update(kit.getId(), 3);
//                System.out.println("세 번째 스레드 남은 갯수 : " + when.getStock());
//                sleep();
//            }
//        });

        //then
    }

    public void sleep() {
        try {
            // 스레드 1초 대기
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}