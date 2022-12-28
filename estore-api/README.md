# Estore API: Handles http requests and contains all the business logic of the site

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

For the powershell terminal the curl executable must be specified and quotation mark characters
need to be escaped.
1. `curl.exe -X GET http://localhost:8080/products`
2. `curl.exe -X POST 'http://localhost:8080/products' -H 'Content-Type:application/json' -d '{\"name\": \"<name>\", (and so on)}'`

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
