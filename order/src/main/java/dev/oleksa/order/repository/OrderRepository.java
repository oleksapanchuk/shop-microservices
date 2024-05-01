package dev.oleksa.order.repository;

import dev.oleksa.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderTrackingNumber(@Param("trackingNumber") String trackingNumber);

    Page<Order> findByUserEmailOrderByDateCreatedDesc(@Param("email") String email, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.orderTrackingNumber LIKE %:trackingNumber%")
    Page<Order> findByTrackingNumber(String trackingNumber, Pageable pageable);
}
