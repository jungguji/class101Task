package net.class101.homework1.domain.product.controller;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.domain.product.application.ProductService;
import net.class101.homework1.domain.product.domain.Product;
import net.class101.homework1.domain.product.dto.ViewProduct;
import net.class101.homework1.global.common.Response;
import net.class101.homework1.global.common.StatusCode;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public Response findAll() {
        List<Product> products = this.productService.findAll();
        List<ViewProduct> viewProducts = products.stream().map(product -> ViewProduct.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build()).collect(Collectors.toList());

        return new Response().ok(viewProducts);
    }

    public Response update(Integer id, Integer count) {
        Product product = this.productService.findById(id);

        if (product == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("해당하는 상품번호의 상품이 존재하지 않습니다.\n");
            sb.append("상품번호를 다시 한번 확인하여 주세요.");

            return new Response(StatusCode.NOT_FOUND.getCode(), sb.toString(), null);
        }

        product.update(count);

        return new Response().ok(product);
    }
}
