package com.example.challengemutanteml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("")
public class MutantController {
    @Autowired
    private DNARepository dnaRepository;

    @PostMapping("/mutant")
    public boolean mutant(@RequestParam String[] dna, @RequestParam int cantLetrasMutante) throws Exception {
        boolean isMutant = Mutant.isMutant(dna, cantLetrasMutante);

        DNA dnaInsert = new DNA(isMutant, String.join(",", dna));

        boolean exists = dnaRepository.existsDNABySecuenciaADN(dnaInsert.getSecuenciaADN());

        //dnaRepository.save(dnaInsert);
        return exists;
        /*if (isMutant)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.FORBIDDEN);*/
    }

    @GetMapping("/stats")
    @ResponseBody
    public StatsADN stats() {
        StatsADN stats = new StatsADN();

        int cantMutantes = 0;
        int cantHumanos = 0;

        List<DNA> dnaList = dnaRepository.findAll();

        for(DNA dna : dnaList) {
            if (dna.isMutant())
                cantMutantes++;
            else
                cantHumanos++;
        }

        stats.count_mutant_dna = cantMutantes;
        stats.count_human_dna = cantHumanos;
        stats.ratio = (double)stats.getCount_mutant_dna() / (double)stats.getCount_human_dna();

        return stats;
    }
}