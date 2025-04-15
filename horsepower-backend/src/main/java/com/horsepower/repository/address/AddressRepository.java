package com.horsepower.repository.address;

import com.horsepower.entity.address.Address;
import com.horsepower.entity.address.Address.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    // 특정 주문번호로 모든 주소(배송지 + 청구지) 조회
    List<Address> findByOrderNumber(String orderNumber);

    // 특정 주문번호의 배송지 or 청구지 구분 조회
    Optional<Address> findByOrderNumberAndType(String orderNumber, Type type);

    // 사용자 ID로 모든 주소 조회 (예: 내 주소록 관리용)
    List<Address> findByUserId(Long userId);
}

