package servise;
import Model.Flower;
import java.util.ArrayList;
import java.util.List;
public class Bouquet {
    private String history="";
    private   double solvedPrise;
    List<Flower> flowerArrayList = new ArrayList<>();
    List<Integer> countArraylist = new ArrayList<>();
    public void addFlower(Flower flower, int count) {
        flowerArrayList.add(flower);
        countArraylist.add(count);
         history = history + (flower + " " + count + " pcs \n");
    }
    public double colculatePrise(){
        for (int i = 0; i < flowerArrayList.size(); i++) {
            solvedPrise = solvedPrise +flowerArrayList.get(i).getPrice() * countArraylist.get(i);
        }
       return solvedPrise;
    }
    public String getHistory() {
        return history;
    }

}
