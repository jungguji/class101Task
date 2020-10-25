package net.class101.homework1.domain.product.controller;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.domain.product.application.ProductService;
import net.class101.homework1.domain.product.domain.Product;
import net.class101.homework1.domain.product.dto.ViewProduct;
import net.class101.homework1.global.common.Domain;
import net.class101.homework1.global.common.Response;
import net.class101.homework1.global.common.StatusCode;
import net.class101.homework1.global.util.PropertiesUtil;

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
        Product product = this.productService.update(id, count);

        if (product == null) {
            String message = PropertiesUtil.getMessage(Domain.PRODUCT.getDomain(), "product.find.message.notfound");
            return new Response(StatusCode.NOT_FOUND.getCode(), message, null);
        }

        return new Response().ok(product);
    }
}
