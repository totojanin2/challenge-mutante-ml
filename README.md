# challenge-mutante-ml

Aca se encuentra el proyecto completo, incluyendo la lógica para saber si una persona es mutante o no, el código para la API Rest y los Unit Test.

Para poder probar la API, utilizando el software Postman o algún otro software para consumir servicios, ingresar la URL: https://challenge-mutante-ml.herokuapp.com/

Para usar el servicio "mutant", usar la URL https://challenge-mutante-ml.herokuapp.com/mutant (por POST) y como parámetro pasar un JSON con el formato:
{
    "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"],
    "cantLetrasMutante": 4
}

(El campo "cantLetrasMutante" sirve para indicar de cuántas letras iguales debe ser una secuencia para poder determinar si esa secuencia es mutante o no)

Para usar el servicio "stats", usar la URL https://challenge-mutante-ml.herokuapp.com/stats (por GET) sin enviar ningún parámetro.
