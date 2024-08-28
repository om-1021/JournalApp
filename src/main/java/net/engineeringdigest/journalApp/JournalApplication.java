package net.engineeringdigest.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
// It will find all the methods with @Transactional annotation and
// will create a corresponding transactional context to that method
//simply it creates a container for the corresponding method where all the operations related to db takes place
// and these operations will be treated as a single operations
// If suppose Ram and Shyam simultaneously called this api then two context will be created which will be totally independent of each other
// all of this done through a manager known as PlatformTransactionManager which is interface
// implementation of that interface is provided by MongoTransactionManager
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
    public PlatformTransactionManager ptm(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}