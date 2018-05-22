## The Project
The project consist on a middle-ware async-reactive server **POC (Proof Of Concept)**, that manages the communication between two different actors:

* **Requester**: The server "client", it sends request to our server.
* **Provider**: The third-party provider. We act as their clients, asking them for survey information.

The services we provide to the requester can be separated in two main blocks:

* **surveys**: The requester can:
  * ask for a survey catalog based on the desired criteria
  * consult a survey knowing it id.
  * get the results of the survey's (Not the scope of the project sample)
* **subscriptions**: The requester can subscribe a survey-catalog request to have a periodic updated "catalog reception"

## The technologies

The main tech-stack is: **SpringBoot (with Groovy) + Webflux**

Additionally, some tools are being used:

* H2 (for an in-memory db)
* Guava
* ModelMapper


And finally, for the tests + docs generation: **WebtestClient + Spring RestDocs**.

## Try-it yourself

#### Execution
You can try the project and the request executing the server. To execute it there are several ways:

* **Build-it yourself**: You can build the project with ease, for it you must open a console in the root folder of the project and execute:
```
$ ./gradlew bootRun
```
> (This order will automatically execute the automated tests (building the docs) and executing the server.)

* **Execute the [provided jar](https://github.com/NemesisMate/caravelotrial/releases/)**
```
java -jar <JAR_NAME>.jar
```

#### Access

Now, (once the server is up) to manually test the project you can go the the generated documentation under: `http://localhost:8080/docs/index.html` and execute any of the example
curl orders for every resource.
