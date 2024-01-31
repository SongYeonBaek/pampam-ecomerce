package com.example.pampam.member.model.entity;


import com.example.pampam.cart.model.entity.Cart;
import com.example.pampam.orders.model.entity.Orders;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consumer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consumerIdx;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String consumerPW;
    @Column(nullable = false)
    private String consumerName;
    @Column(nullable = false)
    private String consumerAddr;
    @Column(nullable = false)
    private String consumerPhoneNum;
    private String authority;
    private Boolean socialLogin;
    private Boolean status;         // email 인증 여부


//    @OneToMany(fetch = FetchType.LAZY,mappedBy = "consumer")
//    private List<Orders> ordersList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> authority);
    }


    @Override
    public String getPassword() {
        return consumerPW;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
