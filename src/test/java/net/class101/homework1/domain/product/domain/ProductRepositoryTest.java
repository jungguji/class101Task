package net.class101.homework1.domain.product.domain;

import net.class101.homework1.domain.product.dao.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductRepositoryTest {

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository(Product.class);
    }

    private Kit createKit() {
        Kit kit = new Kit("테스트", 100, 10);
        productRepository.save(kit);
        return kit;
    }

    @Test
    public void findAll() {
        //given
        Kit kit = createKit();

        Klass klass = new Klass("test", 112);
        productRepository.save(klass);

        //when
        List<Product> whens = (List<Product>) productRepository.findAll();

        //then
        assertEquals(2, whens.size());
    }

    @Test
    void findById() {
        //given
        Kit kit = createKit();

        //when
        Kit when = (Kit) productRepository.findById(kit.getId()).get();

        //then
        assertEquals(when.getId(), kit.getId());
        assertEquals(when.getName(), kit.getName());
        assertEquals(when.getStock(), kit.getStock());
        assertEquals(when.getPrice(), kit.getPrice());
    }

    @Test
    void delete() {
        //given
        Kit kit = createKit();

        //when
        productRepository.delete(kit);

        //then
        assertThrows(NoSuchElementException.class, () -> {
            productRepository.findById(kit.getId()).get();
        });
    }

    @Test
    void deleteById() {
        //given
        Kit kit = createKit();

        //when
        productRepository.deleteById(kit.getId());

        //then
        assertThrows(NoSuchElementException.class, () -> {
            productRepository.findById(kit.getId()).get();
        });
    }
}