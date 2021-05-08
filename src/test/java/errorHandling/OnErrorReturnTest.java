package errorHandling;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

class OnErrorReturnTest extends PrintThread {

	@Test
	void onErrorReturnTest(){
		String[] grades = {"70", "88", "$100", "93", "83"};

		Observable<Integer> source = Observable.fromArray(grades)
											   .map(data -> Integer.parseInt(data)) // $100에서 exception
											   .onErrorReturn(e -> { // map 함수의 Integer.parseInt에서 생길수 있는 예외를 정의해준다.
												   if (e instanceof NumberFormatException) {
													   e.printStackTrace();
												   }
												   return -1;
											   });

		source.subscribe(data -> {
			if(data < 0){
				printTime("Wrong Data found!!");
				return;
			}

			printTime("Grade is " + data);
		});
	}

	@Test
	void onErrorReturnItemTest(){
		String[] grades = {"70", "88", "$100", "93", "83"};

		Observable<Integer> source = Observable.fromArray(grades)
											   .map(data -> Integer.parseInt(data)) // $100에서 exception
											   .onErrorReturnItem(-1); // Throwable 객체를 인자로 전달하지 않기 때문에 코드가 간결해진다.
		source.subscribe(data -> {
			if(data < 0){
				printTime("Wrong Data found!!");
				return;
			}

			printTime("Grade is " + data);
		});
	}
}
