package com.example.pampam.orders.model.entity;


import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.product.model.entity.Product;
import com.example.pampam.product.model.request.PostProductRegisterReq;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Column(nullable=false)
    private String impUid;

    @Column(nullable=false)
    private LocalDate orderDate;

    @Column(nullable=false)
    private Integer price;

    //msa member 빼내기
    private Long consumerId;
    private String consumerEmail;
    private String consumerPassword;
    private String consumerAddress;
    private String consumerName;
    private String consumerPhoneNum;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "orders")
    private List<OrderedProduct> orderProductsList = new ArrayList<>();

    public static Orders dtoToEntity(String impUid, String userEmail, Long consumerId, Integer amount) {
        return Orders.builder()
                .consumerEmail(userEmail)
                .impUid(impUid)
                .consumerId(consumerId)
                .price(amount)
                .orderDate(LocalDate.now())
                .build();
    }
}
