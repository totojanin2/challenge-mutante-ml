package com.example.challengemutanteml;

public class DNARequest {
    public DNARequest(String[] dna, int cantLetrasMutante) {
        this.dna = dna;
        this.cantLetrasMutante = cantLetrasMutante;
    }

    private String[] dna;

    private int cantLetrasMutante;

    public String[] getDna() {
        return dna;
    }

    public int getCantLetrasMutante() {
        return cantLetrasMutante;
    }
}
