package com.luxoft.springadvanced.transactions.optimisticlock;

import com.luxoft.springadvanced.transactions.dao.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import java.util.concurrent.CountDownLatch;

@Controller
public class OptimisticLockController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Async("optimistic")
    public void action(CountDownLatch wait, CountDownLatch notify) throws InterruptedException {
        wait.await();
        try {
            final var review = reviewRepository.findById(1).orElseThrow();
            review.setId(1);
            review.setText(review.getText().toUpperCase());
            reviewRepository.save(review);
        } finally {
            notify.countDown();
        }
    }
}
