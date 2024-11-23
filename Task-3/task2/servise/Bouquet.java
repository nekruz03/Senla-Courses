package servise;

import model.Flower;

import java.util.HashMap;

public class Bouquet {
    private String history="";
    private   double solvedPrise;
    HashMap <Flower,Integer> flowersHashMap = new HashMap<>();
    public void addFlower(Flower flower, int count) {
      flowersHashMap.put( flower,count );
      history = history + (flower + " " + count + " pcs \n");
    }
    public double colculatePrise(){
        for(Flower i : flowersHashMap.keySet()){
            solvedPrise = solvedPrise + i.getPrice() *  flowersHashMap.get(i);
        }
        return solvedPrise;
    }
    public String getHistory() {
        return history;
    }

}
