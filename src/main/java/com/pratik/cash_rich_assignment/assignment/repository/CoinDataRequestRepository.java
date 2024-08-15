package com.pratik.cash_rich_assignment.assignment.repository;

import com.pratik.cash_rich_assignment.assignment.model.CoinDataRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinDataRequestRepository extends JpaRepository<CoinDataRequest, Long> {
}
