#HOW TO START



1) ### **Ensure that both .jar files are generated by maven** 
In case .jar files are not generated run the following command from the necessary project directory
   
```mvn clean install```

2) ### Run docker compose
To start both apps at the same time run the following command fro root project directory:

```docker-compose up --build```

3) ### Try API

call the following url with Postman or any other tool:

```localhost:8082/product/BB5476```


***

#ALTERNATIVE START

You can try other ways to launch apps:

1) launch through IDE.

2) Build jars and run with your command line