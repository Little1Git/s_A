package qy.server_a;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DataReceiverController {

    private static final Logger logger = LoggerFactory.getLogger(DataReceiverController.class);

    @PostMapping(value = "/receive", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<ResponseEntity<String>> receiveData(@RequestBody Flux<MyCustomClass> dataFlux) {
        logger.info("Start receiving data");

        return dataFlux
                .doOnNext(data -> logger.info("Received: {}", data.getData()))
                .doOnError(error -> logger.error("Error occurred during data reception", error))
                .then(Mono.just(ResponseEntity.ok("All data received")));
    }
}