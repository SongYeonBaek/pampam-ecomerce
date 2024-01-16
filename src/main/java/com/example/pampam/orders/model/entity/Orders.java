package com.example.pampam.orders.model.entity;


import com.example.pampam.member.model.entity.Consumer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idx;

    String impUid;

    LocalDate orderDate;

    Integer price;

    @ManyToOne
    @JoinColumn(name = "Consumer_idx")
    Consumer consumer;

    @OneToMany(mappedBy = "orders")
    private List<OrderedProduct> orderProductsList = new ArrayList<>();

}
