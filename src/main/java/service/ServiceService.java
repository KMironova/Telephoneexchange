package service;

import configs.MessageСonstants;
import console.ServiceOutputConsole;
import dao.ServiceDao;
import domain.Service;
import configs.Configs;

import java.util.ArrayList;
import java.util.List;

public class ServiceService {

    ServiceDao serviceDao = new ServiceDao();
    ServiceOutputConsole console = new ServiceOutputConsole();

    public void getALLService()  {
        List <Service> allServices = new ArrayList<>();
        List <Object> listServices = serviceDao.getAllServices();

        for (int i = 0; i<listServices.size(); i+=3) {
            allServices.add(new Service((String)listServices.get(i+1),(int)listServices.get(i+2),(int)listServices.get(i)));
        }
        console.printAllService(allServices);
    }

    public boolean checkSub (String login, String password) {
        if (serviceDao.check(login, password) != 0) {
            Configs.login = login;
            return true ;
        } else return false;
    }

    public boolean addNewSub (String login, String password) {
        int message = serviceDao.addNewSub(login,password);

        if (message == MessageСonstants.SUCSESS) {
            return true;
        } else if (message == MessageСonstants.SUB_ALREADY_EXIST) {
            return false;
        } else return false;
    }

    public void addServiceToSub (int idService) {
        int message = serviceDao.addServiceToSub(Configs.login,idService);
        if (message == MessageСonstants.SERVICE_NOT_EXIST) {
            console.printMessage("Выбран не существующий услуга");
        } else if (message == MessageСonstants.SUB_ALREADY_HAVE_THIS_SERVICE) {
            console.printMessage("У вас уже подключена данная услуга!");
        } else if (message == MessageСonstants.SUCSESS) {
            console.printMessage("Услуга добавлена успешно");
        } else {
            console.printMessage("error");
        }
    }

    public void deleteSubService (int serviceToDelete) {
        int message = serviceDao.deleteSubService(serviceToDelete,Configs.login);

        if (message == MessageСonstants.SUCSESS) {
            console.printMessage("Услуга успешно удалена!");
        } else if (message == MessageСonstants.SERVICE_NOT_EXIST) {
            console.printMessage("Выбран не существующий услуга");
        } else if (message == MessageСonstants.SUB_HAVE_NOT_THIS_SERVICE) {
            console.printMessage("У вас нет такой подключенной услуги");
        } else console.printMessage("error");
    }

    public void getAllSubServices () {
        List <Object> listSubServices = serviceDao.getAllSubServices();
        List <Service> allServices = new ArrayList<>();

        for (int i = 0; i<listSubServices.size(); i+=8) {
            allServices.add(new Service((String)listSubServices.get(i+1),(int)listSubServices.get(i+2),(int)listSubServices.get(i)));
        }
        console.printAllService(allServices);
    }
}
