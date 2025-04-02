spring-rest-basics-recap
==================
Simple Spring REST application with exeption messages (Ceate/Read operations)

REST API usage example:

* GET http://localhost:8080/people (get all)
* GET http://localhost:8080/people/{id} (get by person id)
* POST http://localhost:8080/people (wrong data)
```
{
    "email": "thats_what_she@said.com"
}
```
Response example: 
```
{
    "message": "name - Name should not be empty;",
    "timestamp": 1743608555201
}
```

* POST http://localhost:8080/people (correct data)
```
{
    "name": "Said",
    "age": 27,
    "email": "thats_what_she@said.com"
}
```
