package com.example.challengemutanteml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ChallengeMutanteMlApplicationTests {
	@Test
	void ResponseOK() throws Exception {
		MutantController mutantController = new MutantController();

		String[] adnList = {"AAAACT",
							"TTCTGA",
							"TTATGT",
							"AGAAGG",
							"CCCCTA",
							"TCACTG"};

		ResponseEntity responseEntity = mutantController.mutant(adnList);

		Assertions.assertEquals(new ResponseEntity(HttpStatus.OK), responseEntity);
	}

	@Test
	void ResponseForbidden() throws Exception {
		MutantController mutantController = new MutantController();

		String[] adnList = {"AAGACT",
							"TTCTGA",
							"TTATGT",
							"AGAAGG",
							"CCTCTA",
							"TCACTG"};

		ResponseEntity responseEntity = mutantController.mutant(adnList);

		Assertions.assertEquals(new ResponseEntity(HttpStatus.FORBIDDEN), responseEntity);
	}

	@Test
	void stats()  {
		MutantController mutantController = new MutantController();

		StatsADN stats = mutantController.stats();

		Assertions.assertEquals(4, stats.getCount_mutant_dna());
		Assertions.assertEquals(10, stats.getCount_human_dna());
		Assertions.assertEquals(0.4, stats.getRatio());
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