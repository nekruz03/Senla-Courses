package controller.comparator;

import model.Room;

import java.util.ArrayList;
import java.util.List;

import Enum.ServiceType;
public class Comparators {
    public static java.util.Comparator<Room> byPrise = new java.util.Comparator<Room>() {
        @Override
        public int compare(Room o1, Room o2) {
            return Double.compare(o1.getPrise(), o2.getPrise());
        }

    };
    public java.util.Comparator<Room> byCapasity = new java.util.Comparator<Room>() {
        @Override
        public int compare(Room o1, Room o2) {
            return Integer.compare(o1.getCapasity(), o2.getCapasity());
        }
    };
    public java.util.Comparator<Room> byNumberOfStars = new java.util.Comparator<Room>() {
        @Override
        public int compare(Room o1, Room o2) {
            return  Integer.compare(o1.getNumberOfStars(), o2.getNumberOfStars());
        }
    };

    public java.util.Comparator<Room> byName = new java.util.Comparator<Room>() {
        @Override
        public int compare(Room o1, Room o2) {
            return    o1.getGuestName().compareTo(o2.getGuestName());
        }
    };
    public java.util.Comparator<Room> byDateOfEviction = new java.util.Comparator<Room>() {
        @Override
        public int compare(Room o1, Room o2) {
            return    o1.getDateOfEviction().compareTo(o2.getDateOfEviction());
        }
    };
    public java.util.Comparator<ServiceType> bуServicePrise = new java.util.Comparator<ServiceType>() {

        @Override
        public int compare(ServiceType o1, ServiceType o2) {
            return Double.compare(o1.getPrise(), o2.getPrise());
        }
    };
    public static <T> List<T> sortCollection(List<T> collection, java.util.Comparator<T> comparator) {
        List<T> sorted = new ArrayList<>(collection);
        sorted.sort(comparator);
        return sorted;
    }
}
