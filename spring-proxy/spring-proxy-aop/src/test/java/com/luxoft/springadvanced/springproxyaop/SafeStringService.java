package com.luxoft.springadvanced.springproxyaop;

import com.luxoft.springadvanced.springproxyaop.safestrings.SafeStrings;
import org.springframework.stereotype.Service;

@Service
public class SafeStringService {

    public int length(String passenger) {
        return passenger.length();
    }

    @SafeStrings
    public int safeLength(String name) {
        return name.length();
    }
}
