# java-advanced-hello-world
An advanced hello world: With DB and Completable Future

## Prerequisites
You'll need a local kubernetes cluster to set up the Postgres SQL. The attached kubernetes manifest files will mount a 2GB local storage and some pre-configured databse (the credentials used are also part of the configuration).

## Setup
once your local luster is running, run `kubectl apply -f k8s` to start the server. You may also need to expose that local service (`minikube start <svc>`);

## Running
The application is pretty straight forward. Hibernate creates the table himself,
and the main program flow adds, updates, removes users and printing the users between each step.
