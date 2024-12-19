package org.carrefour.mvp.repositories;

import org.carrefour.mvp.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
