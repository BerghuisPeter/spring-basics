package com.example.springbasics.models;

import com.example.springbasics.entity.Trade;
import org.springframework.data.jpa.domain.Specification;

public class TradeSpecifications {
    public static Specification<Trade> withPrice(Float price) {
        return (root, query, builder) -> {
            if (price == null) {
                return null;
            }
            return builder.equal(root.get("price"), price);
        };
    }

    public static Specification<Trade> withQuantity(Integer quantity) {
        return (root, query, builder) -> {
            if (quantity == null) {
                return null;
            }
            return builder.equal(root.get("quantity"), quantity);
        };
    }
}
