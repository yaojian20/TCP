package socketDemo;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author yaojian
 * @date 2020/6/14 23:12
 */
public class SocketClient {

    public static void main(String[] args) {

        Socket socket = null;

        try {

            socket = new Socket("localhost", 8888);

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("hello, everybody");
            printWriter.close();
            socket.close();
        } catch (Exception e){

        }

    }
}
