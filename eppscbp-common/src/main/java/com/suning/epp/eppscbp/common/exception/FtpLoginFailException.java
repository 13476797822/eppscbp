/**
 * SUNING APPLIANCE CHAINS.
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package com.suning.epp.eppscbp.common.exception;

/**
 * 
 * 异常
 * 
 * @author qinwei 2011-11-25
 */
public class FtpLoginFailException extends Exception {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public FtpLoginFailException() {

    }

    public FtpLoginFailException(String msg) {
        super(msg);
    }

    public FtpLoginFailException(String msg, Throwable t) {
        super(msg, t);
    }
}
