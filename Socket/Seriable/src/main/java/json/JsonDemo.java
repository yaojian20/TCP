package json;

import Serizable.Person;
import com.alibaba.fastjson.JSON;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author yaojian
 * @date 2020/6/18 20:19
 */
public class JsonDemo {



    public static void main(String[] args) {

    }


    private void  excuteWtihJson() throws IOException {
        Person person = new Person();
        person.setName("xiaoming");
        person.setAge(23);

        ObjectMapper mapper = new ObjectMapper();

        byte[] writeBytes = null;


        long start = System.currentTimeMillis();
        for(int i=0; i<100; i++){
            writeBytes = mapper.writeValueAsBytes(person);
        }
        long end = System.currentTimeMillis();
        System.out.println("序列化时间为：" + (end-start) + "ms" + writeBytes.length);

        Person person1 = mapper.readValue(writeBytes, Person.class);

        System.out.println(person == person1);

    }


    //alibaba fastjson
    private void  excuteWtihFastJson() throws IOException {
        Person person = new Person();
        person.setName("xiaoming");
        person.setAge(23);

        String personString = null;

        long start = System.currentTimeMillis();
        for(int i=0; i<100; i++){
            personString = JSON.toJSONString(person);
        }
        long end = System.currentTimeMillis();
        System.out.println("序列化时间为：" + (end-start) + "ms" + personString.getBytes().length);

        Person person1 = JSON.parseObject(personString,Person.class);

        System.out.println(person == person1);

    }

    //google protobuf
    //序列化慢，字节数少,网络传输快,性能好
    private void  excuteWtihProtobuf() throws IOException {
        Student person = new Student();
        person.setName("xiaoming");
        person.setAge(23);

        byte[] writeBytes = null;
        Codec<Student> personCodes = ProtobufProxy.create(Student.class);

        long start = System.currentTimeMillis();
        for(int i=0; i<100; i++){
            writeBytes = personCodes.encode(person);
        }
        long end = System.currentTimeMillis();
        System.out.println("序列化时间为：" + (end-start) + "ms" + writeBytes.length);

        Student person1 = personCodes.decode(writeBytes);

        System.out.println(person == person1);

    }


    //dubbo里的hessian
    //字节数大,网络传输快，序列化快
    private void  excuteWtihHessian() throws IOException {
        Person person = new Person();
        person.setName("xiaoming");
        person.setAge(23);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        HessianOutput hessianOutput = new HessianOutput(os);
        long start = System.currentTimeMillis();
        for(int i=0; i<100; i++){
            hessianOutput.writeObject(person);
            if (i==0){
                System.out.println(os.toByteArray().length);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("序列化时间为：" + (end-start) + "ms");
        HessianInput hessianInput = new HessianInput(new ByteArrayInputStream(os.toByteArray()));

        Person person1 = (Person) hessianInput.readObject();

        System.out.println(person == person1);

    }

}
