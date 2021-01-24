package com.example.challengemutanteml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

@SpringBootTest
class ChallengeMutanteMlApplicationTests {
	@Autowired
	private MutantController mutantController;

	@Test
	void ResponseOK() throws Exception {
		String[] adnList = {"AAAACT",
							"TTCTGA",
							"TTATGT",
							"AGAAGG",
							"CCCCTA",
							"TCACTG"};

		ResponseEntity responseEntity = mutantController.mutant(new DNARequest(adnList, 4));

		Assertions.assertEquals(new ResponseEntity(HttpStatus.OK), responseEntity);
	}

	@Test
	void ResponseForbidden() throws Exception {
		String[] adnList = {"AAGACT",
							"TTCTGA",
							"TTATGT",
							"AGAAGG",
							"CCTCTA",
							"TCACTG"};

		ResponseEntity responseEntity = mutantController.mutant(new DNARequest(adnList, 4));

		Assertions.assertEquals(new ResponseEntity(HttpStatus.FORBIDDEN), responseEntity);
	}

	@Test
	void stats()  {
		StatsDNA stats = mutantController.stats();

		Assertions.assertFalse(stats.getCount_mutant_dna() == 0);
		Assertions.assertFalse(stats.getCount_human_dna() == 0);
		Assertions.assertFalse(stats.getRatio() == 0);
	}

	@Test
	public void MutanteSecuenciasHorizontales() throws Exception {
		String[] adnList = {"AAAACT",
							"TTCTGA",
							"TTATGT",
							"AGAAGG",
							"CCCCTA",
							"TCACTG"};

		boolean isMutant = Mutant.isMutant(adnList, 4);

		Assertions.assertTrue(isMutant);
	}

	@Test
	public void MutanteSecuenciasVerticales() throws Exception {
		String[] adnList = {"ATGCGA",
							"ACGTGC",
							"ATATGT",
							"AGAAGG",
							"GCCCTA",
							"GCACTG"};

		boolean isMutant = Mutant.isMutant(adnList, 4);

		Assertions.assertTrue(isMutant);
	}

	@Test
	public void MutanteSecuenciasOblicuasDerechas() throws Exception {
		String[] adnList = {"ATGCTA",
							"CATGGC",
							"TTATGT",
							"AGAAGG",
							"CGACTA",
							"TCACTG"};

		boolean isMutant = Mutant.isMutant(adnList, 4);

		Assertions.assertTrue(isMutant);
	}

	@Test
	public void MutanteSecuenciasOblicuasIzquierdas() throws Exception {
		String[] adnList = {"ATGCGA",
							"CACTGC",
							"TCCTGT",
							"GGTGAG",
							"CTGCTA",
							"TGACTG"};

		boolean isMutant = Mutant.isMutant(adnList, 4);

		Assertions.assertTrue(isMutant);
	}

	@Test
	public void MutanteTresXTres() throws Exception {
		String[] adnList = {"GTG",
							"CGA",
							"GTG"};

		boolean isMutant = Mutant.isMutant(adnList, 3);

		Assertions.assertTrue(isMutant);
	}

	@Test
	public void NoMutanteTresXTres() throws Exception {
		String[] adnList = {"ATG",
							"CAA",
							"TTC"};

		boolean isMutant = Mutant.isMutant(adnList, 3);

		Assertions.assertFalse(isMutant);
	}

	@Test
	public void ExcepcionADNVacio() {
		try {
			String[] adnList = {"ATG",
								"",
								"TTC"};

			Mutant.isMutant(adnList, 4);
		}
		catch (Exception ex) {
			Assertions.assertEquals(Mutant.ADNVacioException, ex.getMessage());
		}
	}

	@Test
	public void ExcepcionLetrasNoAceptadas() {
		try {
			String[] adnList = {"ATG",
								"ABC",
								"TTC"};

			Mutant.isMutant(adnList, 4);
		}
		catch (Exception ex) {
			Assertions.assertEquals(Mutant.LetrasNoAceptadasException, ex.getMessage());
		}
	}

	@Test
	public void ExcepcionMatrizNoCuadrada() {
		try {
			String[] adnList = {"ATGC",
								"ABC",
								"TTC"};

			Mutant.isMutant(adnList, 4);
		}
		catch (Exception ex) {
			Assertions.assertEquals(Mutant.MatrizNoCuadradaException, ex.getMessage());
		}
	}
}