package net.class101.homework1.domain.product.controller;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.domain.product.application.ProductService;
import net.class101.homework1.domain.product.domain.Product;
import net.class101.homework1.global.cmmon.Response;

@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public Response update(Integer id, Integer count) {
        Product product = this.productService.findById(id);

        if (product == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("해당하는 상품번호의 상품이 존재하지 않습니다.\n");
            sb.append("상품번호를 다시 한번 확인하여 주세요.");

            return new Response(404, sb.toString(), null);
        }

        product.update(count);

        return new Response().ok(product);
    }
}
