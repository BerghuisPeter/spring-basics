package com.example.springbasics.entity;


import com.example.springbasics.models.BUY_SELL;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trades")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private Float price;
    private String currency = "JPY";
    @Column(nullable = false)
    @JsonProperty("buy_sell")
    private BUY_SELL buySell;
    private String assetType = "EQUITY";
    private String originCountry = "JP";
}
