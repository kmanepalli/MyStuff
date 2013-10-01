package com.orbitz.rx;



public class ExampleBuilder {
  public static void main(String args[]) {
    TBSDal tbsDal = new TBSDal();
    PriceBuilder priceBuilder = new PriceBuilder();
    RoomRateBuilder rNrBuilder = new RoomRateBuilder();
    priceBuilder.setObservable(tbsDal);
    rNrBuilder.setObservable(tbsDal);
   
    priceBuilder.fillModule();
    rNrBuilder.fillModule();
   System.out.println(priceBuilder.getArticle());  
   System.out.println(rNrBuilder.getArticle());
  }
 
}