package Comparators;

import Enums.ServiceType;
import Model.Room;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Comparators {
   public static Comparator<Room> byPrise = new Comparator<Room>() {
        @Override
        public int compare(Room o1, Room o2) {
            return Double.compare(o1.getPrise(), o2.getPrise());
        }

    };
   public Comparator<Room> byCapasity = new Comparator<Room>() {
       @Override
       public int compare(Room o1, Room o2) {
           return Integer.compare(o1.getCapasity(), o2.getCapasity());
       }
   };
   public Comparator<Room> byNumberOfStars = new Comparator<Room>() {
       @Override
       public int compare(Room o1, Room o2) {
           return  Integer.compare(o1.getNumberOfStars(), o2.getNumberOfStars());
       }
   };

   public  Comparator<Room> byName = new Comparator<Room>() {
       @Override
       public int compare(Room o1, Room o2) {
        return    o1.getGuestName().compareTo(o2.getGuestName());
       }
   };
   public  Comparator<Room> byDateOfEviction = new Comparator<Room>() {
       @Override
       public int compare(Room o1, Room o2) {
           return    o1.getDateOfEviction().compareTo(o2.getDateOfEviction());
       }
   };
   public Comparator<ServiceType> bуServicePrise = new Comparator<ServiceType>() {

       @Override
       public int compare(ServiceType o1, ServiceType o2) {
           return Double.compare(o1.getPrise(), o2.getPrise());
       }
   };
   public static <T> List<T>sortCollection(List<T> collection, Comparator<T> comparator) {
      List<T> sorted = new ArrayList<>(collection);
      sorted.sort(comparator);
      return sorted;
   }
}
