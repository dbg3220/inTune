# E-Store: InTune: A instrument E-Commerce Store

An online E-store system built in Java 8=>11 and the Angular CLI
  
## Team

- Hayden Cieniawski
- Jonathan Zhu
- Donovan Cataldo 
- Clayton Acheson
- Damon Gonzalez


## Prerequisites

- Java 8=>11 (Make sure to have correct JAVA_HOME setup in your environment)
- Maven
- Angular
- Tailwind CSS


## How to run it

1. Clone the repository
2. go to /estore-api and execute `mvn compile exec:java`
4. go to /estore-ui and execute `ng serve --open`

## Known bugs and disclaimers

Cart persistence between users

## How to test it

The Maven build script provides hooks for run unit tests and generate code coverage
reports in HTML.

To run REST API commands using curl, use the following commands:
(Be sure to delete all '|' before executing commands)

1. Make sure you are in the estore-api directory. By default you will be in the root, to change directories use `cd estore-api`.
2. `curl -X GET http://localhost:8080/products` - get all products
3. `curl -X GET http://localhost:8080/products/|id|` - get product with id
4. `curl -X POST -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{"name": "|String name|", "price": |double price|, "category": "|String Category|", "subcategory": "|String subcategory|", "quantity": |int quantity| }'` - create a product
5. `curl -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{"id": |int id|, "name": "|String name|", "price": |double price|, "category": "|String Category|", "subcategory": "|String subcategory|", "quantity": |int quantity| }'` - update a product
6. `curl -X DELETE http://localhost:8080/products/|id|` - delete a product
7. `curl -X GET 'http://localhost:8080/products/?name=|String name|'` - get all products with name containing {String name}
8. For the powershell terminal
9. `curl.exe -X GET http://localhost:8080/products`
10. `curl.exe -X GET http://localhost:8080/products/|id|`
11. `curl.exe -X POST -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"name\": \"|String name|\", \"price\": |double price|, \"category\": \"|String Category|\", \"subcategory\": \"|String subcategory|\", \"quantity\": |int quantity| }'`
12. `curl.exe -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"id\": |int id|, \"name\": \"|String name|\", \"price\": |double price|, \"category\": \"|String Category|\", \"subcategory\": \"|String subcategory|\", \"quantity\": |int quantity| }'`
13. `curl.exe -X DELETE http://localhost:8080/products/|id|`
14. `curl.exe -X GET 'http://localhost:8080/products/?name=|String name|'`

To run tests on all tiers together do this:

1. Execute `mvn clean test jacoco:report`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/index.html`

To run tests on a single tier do this:

1. Execute `mvn clean test-compile surefire:test@tier jacoco:report@tier` where `tier` is one of `controller`, `model`, `persistence`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/{controller, model, persistence}/index.html`

To run tests on all the tiers in isolation do this:

1. Execute `mvn exec:exec@tests-and-coverage`
2. To view the Controller tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
3. To view the Model tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
4. To view the Persistence tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`

*(Consider using `mvn clean verify` to attest you have reached the target threshold for coverage)
  
To view code coverage of Junit testing

1. Open /estore-api/target/site/jacoco/index.html
  
## How to generate the Design documentation PDF

1. Access the `PROJECT_DOCS_HOME/` directory
2. Execute `mvn exec:exec@docs`
3. The generated PDF will be in `PROJECT_DOCS_HOME/` directory


## How to setup/run/test program 
1. Tester, first obtain the Acceptance Test plan
2. See `How to run it`
3. Test functionality and mark off where implementation fails

## License

MIT License

See LICENSE for details.
