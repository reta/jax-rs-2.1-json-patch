Apache CXF and JSON Patch (RFC-6902) Demo
==============

- build and run: 
  
    mvn spring-boot:run
    
- add new book:
    
    curl -i -X POST http://localhost:19091/services/catalog -H "Content-Type: application\json" -d '{
          "title": "Microservice Architecture",
          "isbn": "978-1491956250",
          "authors": [
              "Ronnie Mitra",
              "Matt McLarty"
          ]
      }'

- patch the book:

    curl -i -X PATCH http://localhost:19091/services/catalog/978-1491956250 -H "Content-Type: application\json" -d '[
          { "op": "add", "path": "/authors/0", "value": "Irakli Nadareishvili" },
          { "op": "add", "path": "/authors/-", "value": "Mike Amundsen" },
          { "op": "replace", "path": "/title", "value": "Microservice Architecture: Aligning Principles, Practices, and Culture" }
      ]'
      
- get the book:

    curl http://localhost:19091/services/catalog/978-1491956250