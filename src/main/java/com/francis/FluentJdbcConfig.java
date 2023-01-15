package com.francis;

import com.francis.model.Person;
import com.francis.model.TestObject;
import com.google.gson.Gson;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.integration.ConnectionProvider;
import org.codejargon.fluentjdbc.api.integration.providers.DataSourceConnectionProvider;
import org.codejargon.fluentjdbc.api.mapper.ObjectMappers;
import org.codejargon.fluentjdbc.api.query.BatchQuery;
import org.codejargon.fluentjdbc.api.query.Mapper;
import org.codejargon.fluentjdbc.api.query.Query;
import org.codejargon.fluentjdbc.api.query.SelectQuery;
import org.codejargon.fluentjdbc.api.query.UpdateQuery;
import org.codejargon.fluentjdbc.api.query.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.simple.SimpleLoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public class FluentJdbcConfig {
//    private

    private final String DB_USER = "database.username";
    private final String DB_PASSWORD = "database.password";
    private final String DB_URL = "database.url";

    private final Logger logger = new SimpleLoggerFactory().getLogger(FluentJdbcConfig.class.getName());

    private static final ObjectMappers objectMapper;
    private static final Mapper<Person> personMapper;
    static {
        objectMapper = ObjectMappers.builder().build();
        personMapper = objectMapper.forClass(Person.class);
    }
    public FluentJdbc getFluentJdbc()  {

        Properties properties = new Properties();
        MysqlDataSource mysqlDataSource = new MysqlDataSource();

        String DB_USER = System.getenv("DATABASE_USERNAME");
        String DB_PASSWORD = System.getenv("DATABASE_PASSWORD");
        String DB_URL = System.getenv("DATABASE_URL");

        if (DB_PASSWORD == null || DB_USER == null || DB_URL == null) {
            logger.error("Invalid database credentials");
            throw new NullPointerException("Invalid database credentials");
        }


        try{
                properties.load(new FileInputStream("src/main/resources/database.properties"));
                mysqlDataSource.setUrl(DB_URL);
                mysqlDataSource.setUser(DB_USER);
                mysqlDataSource.setPassword(DB_PASSWORD);

            System.out.println(properties.getProperty(DB_URL));

            System.out.println(System.getenv());
            System.out.println(System.getenv("DATABASE_URL"));
            System.out.println(System.getProperty("database.url"));

            } catch (FileNotFoundException ex){
                ex.printStackTrace();
            System.out.println("File not found");
            } catch (IOException exception) {
                exception.printStackTrace();
                System.out.println("Erro occured");
            }


        ConnectionProvider conn = new DataSourceConnectionProvider(mysqlDataSource);
        return new FluentJdbcBuilder()
                .connectionProvider(conn)
                .defaultBatchSize(2000)
                .build();
    }

    public  Query getQuery() {
        FluentJdbc fluentJdbc = getFluentJdbc();
        return fluentJdbc.query();
    }

    public  void testDB(){
        Query query = this.getQuery();
        SelectQuery select = query
                .select("select * from persons");
        List<Person> people = select.listResult(personMapper);
        System.out.println(people);
        makeExternalRequest();
        Stream.of("Chuks", 182, "Sangotedo", "Lagos");

        Map<String, Object> namedParams = Map.of("name", "CHuks", "height", 182, "address2", "Sangotedo", "address3", "Lagos");
        try{
//            namedParams
            BatchQuery batch = query.batch("""
                        INSERT INTO persons
                        (`name`, height, address2, address3)
                        VALUES ("Chuks", "182", "Sangotedo", "Lagos");
                    """);

//            System.out.println(run);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void makeExternalRequest(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://catfact.ninja/fact"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        Gson javalinGson = new Gson();

        String responseBody = "";
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            TestObject testObject = javalinGson.fromJson(responseBody, TestObject.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
