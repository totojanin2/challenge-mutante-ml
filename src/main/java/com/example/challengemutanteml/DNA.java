package com.example.challengemutanteml;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DNA {
    public DNA() {

    }

    public DNA(boolean isMutant) {
        this.isMutant = isMutant;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String DNA;

    @Column
    private boolean isMutant;

    public Long getId() {
        return id;
    }

    public String getDNA() {
        return DNA;
    }

    public boolean isMutant() {
        return isMutant;
    }
}

@Repository
interface DNARepository extends JpaRepository<DNA, Long> {
    boolean existsByDNA(String[] DNA);
}
