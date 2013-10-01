package com.orbitz.rx;

public class ThreadingRx {

}


class Blog implements FlexObservable {
	 
	  List<Observer> observersList;
	  private boolean stateChange;
	 
	  public Blog() {
	    this.observersList = new ArrayList<Observer>();
	    stateChange = false;
	  }
	 
	  public void registerObserver(Observer observer) {
	    observersList.add(observer);
	  }
	 
	  public void unRegisterObserver(Observer observer) {
	    observersList.remove(observer);
	  }
	 
	  public void notifyObserver() {
	 
	    if (stateChange) {
	      for (Observer observer : observersList) {
	        observer.update();
	      }
	    }
	  }
	 
	  public Object getUpdate() {
	    Object changedState = null;
	    // should have logic to send the
	    // state change to querying observer
	    if (stateChange) {
	      changedState = "Observer Design Pattern";
	    }
	    return changedState;
	  }
	 
	  public void fetchHotel() {
	    stateChange = true;
	    notifyObserver();
	  }
	 
	}