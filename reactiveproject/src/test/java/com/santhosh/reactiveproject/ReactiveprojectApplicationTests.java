package com.santhosh.reactiveproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

@SpringBootTest
class ReactiveprojectApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void workingWithMono() {
		Mono<String> errorMono = Mono.error(new RuntimeException("Error!!"));
		// Mono -> publisher with 0...1 items
		Mono<String> m1 = Mono
				.just("Test")
				.log()
				.then(errorMono);

		// consume the mono by subscribing
		m1.subscribe(System.out::println);
//		errorMono.subscribe(System.out::println);
	}

	@Test
	void workingWithMonoZip() {
		Mono<String> m1 = Mono.just("str1").log();
		Mono<String> m2 = Mono.just("str2").log();

		Mono<Tuple2<String, String>> combinedMono = Mono.zip(m1, m2);
		combinedMono.subscribe(data -> {
			System.out.println(data.getT1());
			System.out.println(data.getT2());
		});
	}

	@Test
	void workingWithTransformations() {
//		Mono<String> m1 = Mono.just("abc");
//		// map transform data using synchronous func
//		Mono<String> resMapMono = m1.map(item -> item.toUpperCase());
//		resMapMono.subscribe(System.out::println);

//		//flatMap() - transform the value emitted by current mono async,
//		// returning the value emitted by another mono (change value type possible)
//
//
//		Mono<String> m1 = Mono.just("m1 value");
//		Mono<String[]> stringMono = m1.flatMap(data -> Mono.just(data.split(" ")));
//		stringMono.subscribe(System.out::println);

		//flatMapMany
		// similar to flux but use this if we need to return flux
//		Mono<String> m1 = Mono.just("m1 value");
//		Flux<String> stringMono = m1.flatMapMany(data -> Flux.just(data.split(" ")));
//		stringMono.subscribe(data->{
//			System.out.println("data is" + data);
//		});

	}


	@Test
	public void concatWithTest(){

		Mono<String> m1 = Mono.just("str1");
		Mono<String> m2 = Mono.just("str2");
		//concat monos to emit flux
		Flux<String> stringFlux = m1.concatWith(m2).log()
				.delayElements(Duration.ofMillis(2000));
		// two next calls happens

		stringFlux.subscribe(data->{
			System.out.println(data);
		});

	}

}