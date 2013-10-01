package com.orbitz.rx;



public interface FlexObservable {
 
  public void registerObserver(FlexObserver observer);
 
  public void notifyObserver();
 
  public void unRegisterObserver(FlexObserver observer);
 
  public Object getUpdate();
 
  public void fetchHotel();
}