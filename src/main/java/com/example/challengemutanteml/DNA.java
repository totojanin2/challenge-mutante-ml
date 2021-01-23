package com.example.challengemutanteml;

import org.springframework.data.repository.CrudRepository;

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
    private boolean isMutant;

    public Long getId() {
        return id;
    }

    public boolean isMutant() {
        return isMutant;
    }
}

interface DNARepository extends CrudRepository<DNA, Long> {

}
