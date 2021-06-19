package service;

import dao.ServiceDao;
import domain.Service;

public class ServiceService {

    ServiceDao serviceDao = new ServiceDao();

    public Service getALLService(int id)  {
        return serviceDao.get(id);
    }

    public boolean checkSub (String login, String password) {
        return serviceDao.check(login, password) != 0;
    }
}
