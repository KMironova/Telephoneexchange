import configs.Configs;
import configs.MessageСonstants;
import console.ServiceOutputConsole;
import service.ServiceService;

import java.util.Scanner;

public class Main {

    static ServiceService serviceService = new ServiceService();
    static ServiceOutputConsole console = new ServiceOutputConsole();

    public static void main(String[] args) {

        while (true) {
            int message = firstAction();
            if (message == MessageСonstants.REGISTRATION_FAIL) {
                console.printMessage("Ошибка регистрации");
            } else if (message == MessageСonstants.REGISTRATION_SUCSESS) {
                console.printMessage("Вы успешно зарегестрировались.");
            } else if (message == MessageСonstants.LOGIN_FAIL) {
                console.printMessage("Ошибка входа в систему");
            } else if (message == MessageСonstants.LOGIN_SUCSESS) {
                console.printMessage("Добро пожаловать (" + Configs.login + ") в систему Телефонной станции");
                while (true) {
                    nextActionOne();
                }
            } else if (message == MessageСonstants.FIRST_ACTION_ERROR){
                console.printMessage("Некоректный ввод");
            }

        }
    }

    private static int firstAction () {
        console.printMessage("Добро пожаловать в систему Телефонной станции." + "\n" + "(1) Зарегистрироваться;" + "\n"
                                                                                     + "(2) Войти в систему");

        Scanner scanner = new Scanner(System.in);
        int nextAction = scanner.nextInt();

        if (nextAction == 1) {
            console.printMessage("Введите логин для регистрации:");
            String login = scanner.next();
            console.printMessage("Введите пароль для регистрации:");
            String password = scanner.next();

            return registration(login,password) ? MessageСonstants.REGISTRATION_SUCSESS :
                                                  MessageСonstants.REGISTRATION_FAIL;
        } else if (nextAction == 2) {
            console.printMessage("Введите логин:");
            String login = scanner.next();
            console.printMessage("Введите пароль:");
            String password = scanner.next();

            return logIn(login,password) ? MessageСonstants.LOGIN_SUCSESS :
                                           MessageСonstants.LOGIN_FAIL;
        } else {
           return MessageСonstants.FIRST_ACTION_ERROR;
        }
    }

    private static void nextActionOne() {
        console.printMessage("Выберете действие:" +"\n" + "(1) просмотреть свои услуги;" + "\n"
                                                        + "(2) добавить услугу;" + "\n"
                                                        + "(3) удалить услугу;");
        Scanner scanner = new Scanner(System.in);
        int nextAction = scanner.nextInt();

        if (nextAction == 1) {
            console.printMessage("Ваши подключенные услуги:");
            serviceService.getAllSubServices();
        } else if (nextAction == 2) {
            console.printMessage("Выберите одну из доступных услуг для добавления:");
            serviceService.getALLService();
            int choosingService = scanner.nextInt();
            addServiceToSub(choosingService);
        } else if (nextAction == 3) {
            console.printMessage("Выберите услугу для удаления:");
            serviceService.getAllSubServices();
            int serviceForDelete = scanner.nextInt();
            serviceService.deleteSubService(serviceForDelete);
        } else {
            console.printMessage("error");
        }
    }

    private static boolean logIn (String login,String password) {
        return serviceService.checkSub(login,password);
    }

    private static boolean registration (String login,String password) {
        return false;
    }

    private static void addServiceToSub(int id) {
        serviceService.addServiceToSub(id);
    }
}