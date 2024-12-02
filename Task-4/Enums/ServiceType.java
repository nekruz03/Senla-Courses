package Enums;

import java.util.ArrayList;
import java.util.List;

public enum ServiceType {
    BREAKFAST(300),
    LYNCH(599),
    DINNER(1400),
    CLEANING(1000),
    SPA(100);
    public final double prise;
    ServiceType(double prise) {
        this.prise = prise;
    }
    public double getPrise() {
        return prise;
    }
    private static final ArrayList<ServiceType> serviceTypeList = new ArrayList<>(List.of(ServiceType.values()));

    public static  ArrayList<ServiceType> getServiceTypeList() {
        return serviceTypeList;
    }
}

