package repository;

import domain.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductList implements Serializable {
    private final ArrayList<Product> allProducts = new ArrayList<>(setAllProductsList());
    private final ArrayList<Product> selectedProducts = new ArrayList<>();

    public List<Product> getAllProducts() {
        return allProducts;
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public void addSelectedProduct(Product product) {
        selectedProducts.add(product);
    }

    public void clearSelectedProducts() {
        selectedProducts.clear();
    }

    private ArrayList<Product> setAllProductsList() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(Product.builder()
                .name("book")
                .price(5.5)
                .build());
        products.add(Product.builder()
                .name("TV")
                .price(40.0)
                .build());
        products.add(Product.builder()
                .name("Mobile phone")
                .price(25.0)
                .build());
        products.add(Product.builder()
                .name("Textbook")
                .price(8.5)
                .build());
        products.add(Product.builder()
                .name("Pencil")
                .price(2.0)
                .build());
        return products;
    }
}
