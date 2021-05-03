package create;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Executors;

public class CreateTest {

	@Test
	void createTest(){
		Observable.create(
			(ObservableEmitter<String> emitter) -> {
				emitter.onNext("test1");
				emitter.onNext("test2");
				emitter.onNext("test3");
				emitter.onNext("test4");
				emitter.onComplete(); // 반드시 호출해야함
			})
				  .doOnNext(System.out::println)
				  .subscribe();
	}

	@Test
	void fromArrayTest(){
		Observable.fromArray(new String[]{"hi1", "hi2", "hi3"})
				  .doOnNext(System.out::println)
				  .subscribe();
	}

	@Test
	void fromIterableTest(){
		Observable.fromIterable(List.of("hi1", "hi2", "hi3")) // List.of << java 9
				  .doOnNext(System.out::println)
				  .subscribe();
	}

	@Test
	void fromCallableTest(){
		Observable.fromCallable(() -> {
			Thread.sleep(1000l);
			return "Hello Callable";
		}).doOnNext(System.out::println).subscribe();
	}

	@Test
	void fromFutureTest(){
		Observable.fromFuture(Executors.newSingleThreadExecutor().submit(()->{
			Thread.sleep(1000l);
			return "Hello Future";
		})).doOnNext(System.out::println).subscribe();
	}
}
