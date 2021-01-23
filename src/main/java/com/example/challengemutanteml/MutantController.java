package com.example.challengemutanteml;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MutantController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/mutant")
    @ResponseBody
    public ResponseEntity mutant(@RequestParam(value = "dna") String[] adnList) throws Exception {
        boolean isMutant = Mutant.isMutant(adnList, 4);

        if (isMutant)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}