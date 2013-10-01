package com.orbitz.rx;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.Observable.OnSubscribeFunc;
import rx.observables.BlockingObservable;
import rx.subscriptions.Subscriptions;

public class TestRx {
	
	public static void main(String a[]){
		final TestRx rx = new TestRx();
		Observable<List> observable = Observable.create(new OnSubscribeFunc<List>() {

            @Override
            public Subscription onSubscribe(Observer<? super List> Observer) {
                Observer.onNext(rx.returnList("2"));
                //Observer.onNext("two");
                //Observer.onNext("three");
                Observer.onCompleted();
                return Subscriptions.empty();
            }

        });
		
		Observer observer = new ObserverTest();
		observable.subscribe(observer);
		observable.subscribe(new ObserverTest2());
		//observable.single();
		
	}

	synchronized List returnList(String str){
		String time = System.currentTimeMillis()+"";
		//System.out.println(str);
		List list = new ArrayList();
		list.add(time);
		return list;
	}
}

class ObserverTest2 implements Observer<List>{

	@Override
	public void onCompleted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Throwable e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNext(List args) {
		System.out.println("from 2"+args);
		
	}
	
}

class ObserverTest implements Observer<List>{

	@Override
	public void onCompleted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Throwable e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNext(List args) {
		System.out.println("from 1"+args);
		
	}
	
}
