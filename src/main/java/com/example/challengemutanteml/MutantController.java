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
    public ResponseEntity mutant(@RequestParam String[] dna, @RequestParam int cantLetrasMutante) throws Exception {
        boolean isMutant = Mutant.isMutant(dna, cantLetrasMutante);

        if (isMutant)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/stats")
    @ResponseBody
    public StatsADN stats() {
        StatsADN stats = new StatsADN();

        stats.count_mutant_dna = 4;
        stats.count_human_dna = 10;
        stats.ratio = (double)stats.getCount_mutant_dna() / (double)stats.getCount_human_dna();

        return stats;
    }
}