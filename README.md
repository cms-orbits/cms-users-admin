# cms-users-admin
This application is the result of doing reversing engineering to the Contest Management System and provides a backend layer for controlling the register of new users, also present a new entity to complete the user's information.


### Run Instructions

1) mvn install
2) mvn package
3) mvn spring-boot:run

### Dependencies

As it is described at the previous section the system has depencies about the two services which this interact with.

#### Database connection

To set up a connection to the CMS's database it mandatory to describe it into the application properties file.

```yml
---
spring:
  profiles: development
  db-one:
    datasource:
      url: jdbc:postgresql://192.168.187.133:5432/cmsdb
      username: postgres
      password: alex77
```

#### RabbitQM connection

As same as the database connection it is requeried the connection to 

```yml
---
spring:
  rabbitmq:
    host: 192.168.187.133
    port: 5672
    username: joel
    password: joel1
```


#### RabbitQM queue and exchange

As same as the database connection it is requeried the connection to 

```bash
$ rabbitmqadmin declare queue name=my-new-queue durable=false

$ rabbitmqadmin declare exchange name=my-new-exchange type=fanout

```
 
