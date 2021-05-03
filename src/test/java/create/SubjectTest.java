package create;

import io.reactivex.Observable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SubjectTest {

	@DisplayName("마지막으로 발행된 데이터(onComplete전에 emit된 데이터)만 관심이 있다.")
	@Test
	void asyncSubjectPublisherTest(){
		// Publisher로 동작
		AsyncSubject<String> asyncSubject = AsyncSubject.create();

		// first subscriber
		asyncSubject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
		asyncSubject.onNext("1");
		asyncSubject.onNext("2");

		// second subscriber
		asyncSubject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
		asyncSubject.onNext("3");
		asyncSubject.onNext("4");
		asyncSubject.onComplete();
		asyncSubject.onNext("5"); // onComplete후의 emit은 무시

		// 발행을 완료하고 구독을 해도 마지막 emit된 데이터를 얻는다.
		asyncSubject.subscribe(data -> System.out.println("Subscriber #3 => " + data));

//		Subscriber #1 => 4
//		Subscriber #2 => 4
// 		Subscriber #3 => 4
	}

	@DisplayName("AsyncSubject를 구독자로 사용")
	@Test
	void asyncSubjectSubscriberTest(){
		// Publisher
		Float[] temperature = {10.1f, 13.4f, 12.5f};
		Observable<Float> source = Observable.fromArray(temperature);

		// Subscriber
		AsyncSubject<Float> subject = AsyncSubject.create();
		subject.subscribe(data->System.out.println("Subscribe #1 => " + data));

		// 등록
		source.subscribe(subject);

//		Subscribe #1 => 12.5
	}

	@DisplayName("BehaviorSubject 테스트, 초기값 존재")
	@Test
	void behaviorSubjectTest(){
		BehaviorSubject<String> subject = BehaviorSubject.createDefault("6");
		subject.subscribe(data -> System.out.println("Subscribe #1 => " + data)); // #1 이순간 6
		subject.onNext("1"); // emit to #1 "1"
		subject.onNext("3"); // emit to #1 "3"
		subject.subscribe(data -> System.out.println("Subscribe #2 => " + data)); // #2 이순간 3
		subject.onNext("5"); // emit to #1,#2 "5"
		subject.onComplete();

//		Subscribe #1 => 6
//		Subscribe #1 => 1
//		Subscribe #1 => 3
//		Subscribe #2 => 3
//		Subscribe #1 => 5
//		Subscribe #2 => 5
	}

	@DisplayName("BehaviorSubject 테스트, 초기값 X")
	@Test
	void behaviorSubjectTestWithoutDefault(){
		BehaviorSubject<String> subject = BehaviorSubject.create();
		subject.subscribe(data -> System.out.println("Subscribe #1 => " + data)); // 등록만되고 방출되는 데이터는 없다.
		subject.onNext("1"); // emit to #1 "1"
		subject.onNext("3"); // emit to #1 "3"
		subject.subscribe(data -> System.out.println("Subscribe #2 => " + data)); // #2 이순간 3
		subject.onNext("5"); // emit to #1,#2 "5"
		subject.onComplete();

//		Subscribe #1 => 1
//		Subscribe #1 => 3
//		Subscribe #2 => 3
//		Subscribe #1 => 5
//		Subscribe #2 => 5
	}

	@DisplayName("PublishSubject 테스트")
	@Test
	void publishSubjectTest(){
		PublishSubject<String> subject = PublishSubject.create();
		subject.subscribe(data->System.out.println("Subscriber #1 =>" + data)); // 아무것도 방출 안됨
		subject.onNext("1"); // emit to #1 "1"
		subject.onNext("3"); // emit to #1 "3"
		subject.subscribe(data->System.out.println("Subscriber #2 =>" + data)); // 아무것도 방출 안됨
		subject.onNext("5"); // emit to #1,#2 "5"
		subject.onComplete();

//		Subscriber #1 =>1
//		Subscriber #1 =>3
//		Subscriber #1 =>5
//		Subscriber #2 =>5
	}

	@DisplayName("ReplaySubject 테스트")
	@Test
	void replaySubjectTest(){
		ReplaySubject<String> subject = ReplaySubject.create();
		subject.subscribe(data -> System.out.println("Subscriber #1 => " + data)); // 발행된 데이터 없으니 아무것도 출력 안됨
		subject.onNext(tempReplaySubjectObject("1")); // method 내부에 in 출력후 "1" emit to #1
		subject.onNext(tempReplaySubjectObject("3")); // method 내부에 in 출력후 "3" emit to #1
		subject.onNext(tempReplaySubjectObject("1")); // method 내부에 in 출력후 "1" emit to #1 ( 캐싱은 value equals을 따지지 않고 순차적으로 )
		subject.subscribe(data -> System.out.println("Subscriber #2 => " + data)); // 구독할 당시에 캐싱된 "1", "3"을 emit, 캐싱된 데이터이기 때문에 "in"은 출력안됨
		subject.onNext(tempReplaySubjectObject("5")); // method 내부에 in 출력후 "5" emit to #1, #2
		subject.onComplete();

//		in
//		Subscriber #1 => 1
//		in
//		Subscriber #1 => 3
//		in
//		Subscriber #1 => 1
//		Subscriber #2 => 1
//		Subscriber #2 => 3
//		Subscriber #2 => 1
//		in
//		Subscriber #1 => 5
//		Subscriber #2 => 5
	}

	private String tempReplaySubjectObject(String value){
		System.out.println("in");
		return value;
	}


}
