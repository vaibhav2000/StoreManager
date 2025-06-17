package com.vab.transactionmanager.controller;

import com.vab.transactionmanager.dto.StandardResponse;
import com.vab.transactionmanager.dto.TransactionDto;
import com.vab.transactionmanager.entity.TransactionDetails;
import com.vab.transactionmanager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping()
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/")
    public String basePage() {
        return "redirect:transactions";
    }

    @GetMapping("/transactions")
    public String getAllTransactions(Model model) {

        List<TransactionDetails> transactionDetails = transactionService.getAllTransactionsByDate(LocalDateTime.now());
        BigDecimal totalAmount = transactionDetails.stream().map(TransactionDetails::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("transactions", transactionDetails);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("newAmount", BigDecimal.ZERO);
        model.addAttribute("postedDate", LocalDateTime.now());

        return "homepage.html";
    }

    @PostMapping("/transactions")
    public String postAllTransactions(@RequestParam("dateDifference") Integer dateDifference, @RequestParam("postedDate") LocalDateTime postedDate, Model model) {

        postedDate = postedDate.plusDays(dateDifference);

        List<TransactionDetails> transactionDetails = transactionService.getAllTransactionsByDate(postedDate);
        BigDecimal totalAmount = transactionDetails.stream().map(TransactionDetails::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("transactions", transactionDetails);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("newAmount", BigDecimal.ZERO);
        model.addAttribute("postedDate", postedDate);
        return "homepage.html";
    }

    @PostMapping("/transactions/delete")
    public String deleteTransaction(@RequestParam("transactionId") Integer transactionId) {
        transactionService.deleteTransaction(transactionId);
        return "redirect:/transactions";
    }

    private final AtomicBoolean dataUpdated = new AtomicBoolean(false);

    @GetMapping("/transactions/add")
    public String addNewTransaction(Model model) {
        model.addAttribute("transactionDto", new TransactionDto());
        return "addTransaction.html";
    }

    @PostMapping("/transactions/addtransaction")
    public String addTransaction(@ModelAttribute("transactionDto") TransactionDto transactionDto) {
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setAmount(transactionDto.getAmount());
        transactionDetails.setTimeStamp(LocalDateTime.now());
        transactionService.saveTransaction(transactionDetails);

        dataUpdated.set(true);
        return "redirect:/transactions";
    }

    @ResponseBody
    @PostMapping("/transactions/add")
    public StandardResponse addRestTransaction(@RequestBody TransactionDto transactionDto) {
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setAmount(transactionDto.getAmount());
        transactionDetails.setTimeStamp(LocalDateTime.now());
        transactionService.saveTransaction(transactionDetails);

        dataUpdated.set(true);
        return new StandardResponse("Transaction saved to database");
    }

    @ResponseBody
    @GetMapping("/api/has-update")
    public Map<String, Boolean> hasUpdate() {
        boolean updated = dataUpdated.getAndSet(false);
        return Map.of("updated", updated);
    }
}
