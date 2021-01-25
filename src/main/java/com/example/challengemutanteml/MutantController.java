package com.example.challengemutanteml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
public class MutantController {
    @Autowired
    private DNARepository dnaRepository;

    private final Lock lock = new ReentrantLock(true);

    @Async
    @PostMapping("/mutant")
    public CompletableFuture<ResponseEntity> mutant(@RequestBody DNARequest dnaRequest) throws Exception {
        try {
            boolean isMutant = Mutant.isMutant(dnaRequest.getDna(), dnaRequest.getCantLetrasMutante());

            DNA dnaInsert = new DNA(isMutant, ConvertArrayToString(dnaRequest.getDna()));

            boolean exists = dnaRepository.existsDNABySecuenciaADN(dnaInsert.getSecuenciaADN());

            // Bloqueo el thread para evitar problemas de concurrencia al insertar en la BD
            lock.lock();

            if (!exists)
                dnaRepository.save(dnaInsert);

            if (isMutant)
                return CompletableFuture.completedFuture(new ResponseEntity(HttpStatus.OK));
            else
                return CompletableFuture.completedFuture(new ResponseEntity(HttpStatus.FORBIDDEN));
        }
        catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        finally {
            lock.unlock();
        }
    }

    @GetMapping("/stats")
    @ResponseBody
    public StatsDNA stats() {
        StatsDNA stats = new StatsDNA();

        long cantMutantes = dnaRepository.countDNAByIsMutant(true);
        long cantHumanos = dnaRepository.countDNAByIsMutant(false);

        stats.count_mutant_dna = (int)cantMutantes;
        stats.count_human_dna = (int)cantHumanos;

        if (stats.getCount_human_dna() != 0)
            stats.ratio = (double)stats.getCount_mutant_dna() / (double)stats.getCount_human_dna();
        else
            stats.ratio = 0;

        return stats;
    }

    private String ConvertArrayToString(String[] array) {
        StringBuilder builder = new StringBuilder();

        for(String s : array) {
            builder.append(s);
        }

        return builder.toString();
    }
}