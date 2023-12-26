## Aplicação REST com Springboot utlizando Lombok, Logger, Junit e H2 DB

Está aplicação é um CRUD de gerenciamento de produtos com a funcionalidade adicional de converter os valores dos produtos de Real (BRL) para Dólar (USD).

Aplicação feita para o assessment (entrega final) da disciplina de Desenvolvimento de Serviços Web e Testes com Java do Instituto Infnet (Universidade).

Chamada dos endpoints:
Rodando localmente: http://localhost:8080/products

 #### GET:

* /Products -> Lista com todos os produtos cadastrados.
    * Parâmetros opcionais:
	    * quantity(int) -> quantidade de produtos exibida por chamada.
	    * order(String: "name" || "id") -> ordena os produtos listados por nome ou id.
		* sortBy(String: "asc" || "desc")  -> ordena os produtos de forma crescente ou decrescente.

* /id -> Lista produto com o id informado.

* /usd/id -> Lista produto com o id informado com seu valor convertido para dólar(USD).
		
_Exemplo:_

		[
			{
			"name": "Cenoura",
			"id": 1,
			"price": 0.99,
			"size": [ 10.0 , 2.0 , 2.0 ]
			}
		]
		

* /dolarPrice -> Informa valor da cotação do dólar atualizada.

#### POST:

* /add -> adiciona um produto na listagem.

* /addMultiple -> adiciona multiplos produtos na listagem.

		{ 
		String name;    
		 Double price;  
		 List<Double> size[0.0, 0.0, 0.0];
		}
_Exemplo:_

	    {
		  "name": "Arroz",
		  "price": 29.99,
		  "size": [30.0, 20.0, 10.0]
		}

#### PUT:

* /id -> Altera informações do produto do id informado.
		{ 
		String name;    
		 Double price;  
		 List<Double> size[0.0, 0.0, 0.0];
		}
		
_Exemplo:_

	    {
		  "name": "Feijão",
		  "price": 10.00,
		  "size": [18.0, 12.0, 6.0]
		}


#### DELETE:

* /id -> Exclui produto do id informado.

* /deleteAll -> Exclui todos os produtos cadastrados.


##### [API usada para obter cotação do dolar](https://api.invertexto.com/api-conversor-moedas)


