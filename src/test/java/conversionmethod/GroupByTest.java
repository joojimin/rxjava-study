package conversionmethod;

import io.reactivex.Observable;
import io.reactivex.observables.GroupedObservable;
import org.junit.jupiter.api.Test;

public class GroupByTest {

	@Test
	void groupByTest(){
		String[] objs = {"6", "4", "2-T", "2", "6-T", "4-T"};

		Observable<GroupedObservable<String, String>> source =
			Observable.fromArray(objs).groupBy(this::getShape);

		source.subscribe(obj -> obj.filter(val -> obj.getKey().equals("BALL"))
							   .subscribe(val -> System.out.println("GROUP:" + obj.getKey() + "\t Value:" + val)));
	}

	private String getShape(String obj){
		if(obj == null || obj.equals("")) return "NO-SHAPE";
		if(obj.endsWith("-H")) return "HEXAGON";
		if(obj.endsWith("-O")) return "OCTAGON";
		if(obj.endsWith("-R")) return "RECTANGLE";
		if(obj.endsWith("-T")) return "TRIANGLE";
		if(obj.endsWith("<>")) return "DIAMOND";
		return "BALL";
	}
}
