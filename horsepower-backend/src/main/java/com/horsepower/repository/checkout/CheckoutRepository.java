package com.horsepower.repository.checkout;

import com.horsepower.entity.checkout.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    List<Checkout> findByUserEmail(String userEmail);
}

