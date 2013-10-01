package com.orbitz.rx;


public class PriceBuilder implements FlexObserver {
 
  private String article;
  private FlexObservable dal;
 
  public void setObservable(FlexObservable dal) {
    this.dal = dal;
    dal.registerObserver(this);
    article = "No New Article!";
  }
  public void fillModule(){
	  dal.fetchHotel();
  }
 
  @Override
  public void update() {
    System.out.println("State change reported by Subject.");
    article = (String) dal.getUpdate();
   
  }
 
  public String getArticle() {
    return article;
  }
}