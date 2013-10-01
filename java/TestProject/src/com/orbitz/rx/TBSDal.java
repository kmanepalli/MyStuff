package com.orbitz.rx;


import java.util.ArrayList;
import java.util.List;
 
public class TBSDal implements FlexObservable {
 
  List<FlexObserver> observersList;
  private boolean stateChange;
 
  public TBSDal() {
    this.observersList = new ArrayList<FlexObserver>();
    stateChange = false;
  }
 
  public void registerObserver(FlexObserver observer) {
    observersList.add(observer);
  }
 
  public void unRegisterObserver(FlexObserver observer) {
    observersList.remove(observer);
  }
 
  public void notifyObserver() {
 
    if (stateChange) {
      for (FlexObserver observer : observersList) {
        observer.update();
      }
    }
  }
 
  public Object getUpdate() {
    Object changedState = null;
    // should have logic to send the
    // state change to querying observer
    if (stateChange) {
      changedState = "Observer Design Pattern"+System.currentTimeMillis();
    }
    return changedState;
  }
 
  public void fetchHotel() {
	  if(!stateChange){
		  TBSLapsangCall lapsangCall = new TBSLapsangCall();
		  lapsangCall.getHotelData();
	  }else{
		  return;
	  }
    stateChange = true;
    notifyObserver();
  }
 
}