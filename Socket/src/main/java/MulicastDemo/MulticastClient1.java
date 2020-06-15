package MulicastDemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * @author yaojian
 * @date 2020/6/15 21:36
 */
public class MulticastClient1 {

    public static void main(String[] args) throws UnknownHostException {

        //地址组 224.0.0.0-239.255.255.255
        InetAddress group = InetAddress.getByName("224.5.6.7");
            try {

                MulticastSocket client = new MulticastSocket(8888);

                client.joinGroup(group);
                byte[] bytes = new byte[256];
                while (true){

                    DatagramPacket message = new DatagramPacket(bytes,bytes.length);
                    client.receive(message);
                    String data = new String(message.getData());
                    System.out.println("client 1：" + data);
                }



            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
