package net.class101.homework1.domain.product.domain;

import net.class101.homework1.global.db.InsertTestData;
import net.class101.homework1.domain.product.dao.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    ProductRepository productRepository;

    @BeforeEach
    void setUp() throws SQLException {
        productRepository = new ProductRepository(Product.class);
        InsertTestData.createTable();
        InsertTestData.insertTestData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        InsertTestData.dropTable();
    }

    @Test
    void update_kit_수량추가() {
        //given
        Kit given = (Kit) productRepository.findById(91008).get();

        //when
        given.update(10);

        //then
        assertEquals(0, given.getStock());
    }

    @Test
    void update_klass_수량추가() {
        //given
        Klass given = (Klass) productRepository.findById(16374).get();

        //when
        given.update(100);

        //then
        assertEquals(99999, given.getStock());
    }


    @Test
    void update_multi_thread() throws InterruptedException {
        //given
        ExecutorService service = Executors.newFixedThreadPool(3);

        Kit kit = new Kit("test", 1000, 100);
        this.productRepository.save(kit);

        Kit given = (Kit) productRepository.findById(kit.getId()).get();

        //when
        service.execute(() -> {
            int count = 1;
            while (kit.getStock() > 0) {
                given.update(count);
                System.out.println(Thread.currentThread().getName() + " - " + count + "개씩 빼는 스레드 / 남은 갯수 : " + given.getStock());
                sleep();
            }
        });

        service.execute(() -> {
            int count = 3;
            while (kit.getStock() > 0) {
                given.update(count);
                System.out.println(Thread.currentThread().getName() + " - " + count + "개씩 빼는 스레드 / 남은 갯수 : " + given.getStock());
                sleep();
            }
        });

        //when
        service.execute(() -> {
            int count = 32;
            while (kit.getStock() > 0) {
                given.update(count);
                System.out.println(Thread.currentThread().getName() + " - " + count + "개씩 빼는 스레드 / 남은 갯수 : " + given.getStock());
                sleep();
            }
        });

        service.execute(() -> {
            int count = 12;
            while (kit.getStock() > 0) {
                given.update(count);
                System.out.println(Thread.currentThread().getName() + " - " + count + "개씩 빼는 스레드 / 남은 갯수 : " + given.getStock());
                sleep();
            }
        });

        service.execute(() -> {
            int count = 3;
            while (kit.getStock() > 0) {
                given.update(count);
                System.out.println(Thread.currentThread().getName() + " - " + count + "개씩 빼는 스레드 / 남은 갯수 : " + given.getStock());
                sleep();
            }
        });

        service.execute(() -> {
            int count = 2;
            while (kit.getStock() > 0) {
                given.update(count);
                System.out.println(Thread.currentThread().getName() + " - " + count + "개씩 빼는 스레드 / 남은 갯수 : " + given.getStock());
                sleep();
            }
        });

        Thread.sleep(10*1000);
    }

    public void sleep() {
        try {
            // 스레드 1초 대기
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}