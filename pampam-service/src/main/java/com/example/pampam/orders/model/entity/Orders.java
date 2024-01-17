package com.example.pampam.orders.model.entity;


import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.product.model.entity.Product;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "주문 idx", example = "log", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String impUid;

    private LocalDate orderDate;

    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Consumer_idx")
    private Consumer consumer;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "orders")
    private List<OrderedProduct> orderProductsList = new ArrayList<>();
}
