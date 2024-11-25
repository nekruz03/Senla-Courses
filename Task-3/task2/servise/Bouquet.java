package servise;
import model.Flower;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Bouquet {
    private String history="";
    private   double solvedPrise;
    List<Flower> flowerArrayList = new ArrayList<>();
    List<Integer> countArraylist = new ArrayList<>();
    Flower flower ;
    public void addFlower(Flower flower, int count) {
        flowerArrayList.add(flower);
        countArraylist.add(count);
         history = history + (flower + " " + count + " pcs \n");
    }
    public double colculatePrise(){
        for (int i = 0; i < flowerArrayList.size(); i++) {
            flower = flowerArrayList.get(i);
            solvedPrise = solvedPrise + flower.getPrice() * countArraylist.get(i);
        }
       return solvedPrise;
    }
    public String getHistory() {
        return history;
    }

}
