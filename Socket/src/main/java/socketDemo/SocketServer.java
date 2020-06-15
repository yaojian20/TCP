package socketDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.Socket;

/**
 * @author yaojian
 * @date 2020/6/14 23:03
 */
public class SocketServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);

            //接收请求,阻塞
            Socket socket = serverSocket.accept();

            //BufferedReader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Server端接收到：" + bufferedReader.readLine());

            bufferedReader.close();


        } catch (Exception e){

        } finally {

            if (serverSocket != null){
                serverSocket.close();
            }

        }
    }
}
