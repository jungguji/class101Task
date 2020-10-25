package net.class101.homework1.domain.product.domain;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.domain.model.Printer;
import net.class101.homework1.domain.product.dto.ViewProduct;

import java.util.List;

@RequiredArgsConstructor
public class ProductPrinter implements Printer<Product> {
    private final List<ViewProduct> viewProducts;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(header()).append("\n");
        for (ViewProduct viewProduct : viewProducts) {
            sb.append(viewProduct.getId()).append("\t");
            sb.append(viewProduct.getName()).append("\t");
            sb.append(viewProduct.getPrice()).append("\t");
            sb.append(viewProduct.getStock()).append("\t");
            sb.append("\n");
        }

        return sb.toString();
    }

    private String header() {
        String str = "상품번호\t\t\t\t\t\t\t상품명\t\t판매가격\t\t\t재고수";

        return str;
    }
}
