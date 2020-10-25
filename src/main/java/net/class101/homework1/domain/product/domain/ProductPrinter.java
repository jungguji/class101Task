package net.class101.homework1.domain.product.domain;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.domain.model.Printer;
import net.class101.homework1.domain.product.dto.ViewProduct;
import net.class101.homework1.global.common.Domain;
import net.class101.homework1.global.util.PropertiesUtil;

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
        StringBuilder header = new StringBuilder();
        header.append(PropertiesUtil.getMessage(Domain.PRODUCT.getDomain(), "product.list.view.header.number"));
        header.append("\t\t\t\t\t\t\t");
        header.append(PropertiesUtil.getMessage(Domain.PRODUCT.getDomain(), "product.list.view.header.name"));
        header.append("\t\t");
        header.append(PropertiesUtil.getMessage(Domain.PRODUCT.getDomain(), "product.list.view.header.price"));
        header.append("\t\t\t");
        header.append(PropertiesUtil.getMessage(Domain.PRODUCT.getDomain(), "product.list.view.header.stock"));

        return header.toString();
    }
}
