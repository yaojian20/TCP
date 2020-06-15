package MulicastDemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @author yaojian
 * @date 2020/6/15 21:23
 */
public class MulticastServer {

    public static void main(String[] args) throws UnknownHostException {
        //地址组 224.0.0.0-239.255.255.255
        InetAddress group = InetAddress.getByName("224.5.6.7");

            try {


                MulticastSocket server = new MulticastSocket();
                for(int i=0;i<10;i++){
                    String message = "hello：" + i;
                    byte[] bytes = message.getBytes();
                    server.send(new DatagramPacket(bytes,bytes.length,group,8888));
                    //3s一次
                    TimeUnit.SECONDS.sleep(3);
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


}
