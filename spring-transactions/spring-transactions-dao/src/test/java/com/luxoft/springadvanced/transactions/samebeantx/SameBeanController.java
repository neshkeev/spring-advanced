package com.luxoft.springadvanced.transactions.samebeantx;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class SameBeanController {
    private final SameBeanController controller;

    public SameBeanController(@Lazy SameBeanController controller) {
        this.controller = controller;
    }

    @Transactional(propagation = Propagation.NEVER)
    public void never() {
    }

    @Transactional
    public void actionWithProxy() {
        controller.never();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void actionDirect() {
        never();
    }
}
