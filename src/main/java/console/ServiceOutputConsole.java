package console;

import domain.Service;
import service.ServiceService;

import java.util.List;

//вывод значение в консоль
public class ServiceOutputConsole {

    public void printAllService (List <Service> listServices) {
        for (Service service : listServices) {
            System.out.println("(" + service.getId() + ")" + service.toString() + "\n----------------------------\n");
        }
    }

    public void printMessage (String message) {
        System.out.println(message);
    }
}
