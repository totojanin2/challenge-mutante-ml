package com.example.challengemutanteml;

import org.springframework.stereotype.Component;

@Component
public class StatsDNA {
    public int count_mutant_dna;
    public int count_human_dna;
    public double ratio;

    public int getCount_mutant_dna() {
        return count_mutant_dna;
    }

    public int getCount_human_dna() {
        return count_human_dna;
    }

    public double getRatio() {
        return ratio;
    }
}
