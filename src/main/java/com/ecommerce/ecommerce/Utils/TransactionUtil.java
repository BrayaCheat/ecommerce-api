package com.ecommerce.ecommerce.Utils;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionUtil {
    public String GenerateTransactionID(){
        return "TXN-" + UUID.randomUUID().toString();
    }
}
