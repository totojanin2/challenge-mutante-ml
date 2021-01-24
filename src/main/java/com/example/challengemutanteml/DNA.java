package com.example.challengemutanteml;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Entity
@Table
public class DNA {
    public DNA() {

    }

    public DNA(boolean isMutant, String dna) {
        this.isMutant = isMutant;
        this.secuenciaADN = dna;
    }

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String secuenciaADN;

    @Column
    private boolean isMutant;

    public Long getId() {
        return id;
    }

    public String getSecuenciaADN() {
        return secuenciaADN;
    }

    public boolean isMutant() {
        return isMutant;
    }
}

@Repository
interface DNARepository extends JpaRepository<DNA, Long> {
    boolean existsDNABySecuenciaADN(String secuenciaADN);
}
