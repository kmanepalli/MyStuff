package com.orbitz.rx;


public interface FlexObserver{
 
  public void update();
 
  public void setObservable(FlexObservable subject);
}