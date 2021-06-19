package console;

import service.ServiceService;

//вывод значение в консоль
public class ServiceOutputConsole {

    ServiceService serviceService = new ServiceService();

    public void outputService (int id) {
        System.out.println(serviceService.getALLService(id));
    }

    public void printMessage (String message) {
        System.out.println(message);
    }
}
