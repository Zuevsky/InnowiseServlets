package domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Product {
    String name;
    double price;
}

