package com.groupbcnc.shared.domain.exceptions;

public class ViolatedRuleException extends Exception {

    public ViolatedRuleException(String cause) {
        super(cause);
    }
}
