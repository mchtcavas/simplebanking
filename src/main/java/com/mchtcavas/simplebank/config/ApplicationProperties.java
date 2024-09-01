package com.mchtcavas.simplebank.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private BillPayment billPayment;

    public BillPayment getBillPayment() {
        return billPayment;
    }

    public void setBillPayment(BillPayment billPayment) {
        this.billPayment = billPayment;
    }

    public static class BillPayment {
        private List<String> operators;

        public List<String> getOperators() {
            return operators;
        }

        public void setOperators(List<String> operators) {
            this.operators = operators;
        }
    }
}
