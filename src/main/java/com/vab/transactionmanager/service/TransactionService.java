package com.vab.transactionmanager.service;

import com.vab.transactionmanager.entity.TransactionDetails;
import com.vab.transactionmanager.repository.TransactionRepository;
import com.vab.transactionmanager.util.Constants;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void saveTransaction(TransactionDetails transactionDetails) {
        transactionRepository.save(transactionDetails);
//        log.info("Transaction saved to database: " + transactionDetails.toString());
    }

    public void deleteTransaction(Integer transactionId) {
        transactionRepository.deleteById(transactionId);
//        log.info("Transaction deleted from database: " + transactionId);
    }

    public List<TransactionDetails> getAllTransactionsByDate(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_FORMAT);

        String currentFormattedDate = localDateTime.format(dateTimeFormatter);
        List<TransactionDetails> transactionDetailsList = transactionRepository.findAll();
        transactionDetailsList = transactionDetailsList.stream().filter((transactionDetails) ->
                transactionDetails.getTimeStamp().format(dateTimeFormatter).equals(currentFormattedDate)).toList();

//        log.info("All transactions of " + currentFormattedDate + " fetched from database.");
        return transactionDetailsList;
    }
}
