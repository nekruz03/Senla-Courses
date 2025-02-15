package dao.model.service;

import annotation.OwnInject;
import dao.model.Dao.ServiceType_Dao;
import dao.model.entity.ServiceType;
import viev.ConsoleView;

import java.util.List;

import static java.lang.Integer.parseInt;

public class ServiceType_Service {
    @OwnInject
    ConsoleView consoleView;
    ServiceType serviceType = null;
    @OwnInject
    ServiceType_Dao serviceTypeDao;
    public void addServiceType(){
        ServiceType serviceType = serviceTypeDao.getServiceFromConsole();
        serviceTypeDao.save(serviceType);
    }
    public void getAllServiceTypes(){
        List<ServiceType> serviceTypes = serviceTypeDao.findAll();
        for(ServiceType serviceType : serviceTypes){
            System.out.println(serviceType);
        }
    }
    public void updateServiceType(){
        int id = parseInt(consoleView.getInput("Enter id service you want to update\n"));
        ServiceType serviceType = serviceTypeDao.getServiceFromConsole();
        serviceType.setId(id);
        serviceTypeDao.update(serviceType);
    }
    public void getServiceTypeById(){
        int id = Integer.parseInt(consoleView.getInput("Enter id service you want to update\n"));
        ServiceType serviceType = serviceTypeDao.findById(id);
        System.out.println(serviceType);
    }
    public void deleteServiceTypeById(){
        serviceTypeDao.delete(serviceType);
    }
}
