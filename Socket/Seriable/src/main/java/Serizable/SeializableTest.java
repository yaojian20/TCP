package Serizable;

import java.io.*;

/**
 * @author yaojian
 * @date 2020/6/16 21:45
 */
public class SeializableTest {

    public static void main(String[] args) {

        Serializable();
        Person.address = "anhui";

        DeSerializable();
    }

    public static void Serializable(){
        Person person = new Person();
        person.setAge(25);
        person.setName("shuaige");
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File("person")));
            outputStream.writeObject(person);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void DeSerializable(){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File("person")));
            Person person = (Person) inputStream.readObject();
            System.out.println(person.toString());
            System.out.println(person.address);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
