package com.vab.transactionmanager.repository;

import com.vab.transactionmanager.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDetails, Integer> {
    @Query(value = "SELECT * FROM transaction_details u WHERE DATE(time_stamp) = ?1", nativeQuery = true)
    List<TransactionDetails> findByTimeStamp(LocalDate currentDate);
}
