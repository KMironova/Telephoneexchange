import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class StartServer {

    public static void start(){

        //new SignalSender().start(); //Поток для отправки сигналов пользователям

        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()){
            serverSocket.bind(new InetSocketAddress(7981),20);
            while (true){
                try{
                    SocketChannel clientSocket = serverSocket.accept();
                    //Pool.submit(new SocketWorker (clientSocket));      //Обработка информации
                } catch (EOFException e){
                    System.out.println("\nСервер оборвал соединение!!!");
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
