/**
 * Created by ml996 on 1/31/17.
 */
import java.util.*;
public class Main {
    public static class Message{
        public final String number;
        public final String text;
        public final long time;
        public Message(String n, String t){
            number = n;
            text = t;
            time = Calendar.getInstance().get(Calendar.MILLISECOND);
        }
    }
    public static class MessageList{
        ArrayList<Message> m = new ArrayList<Message>();
        public MessageList(){}
        public void add(Message me){

        }
        public void remove(int position){

        }
        public void erase(){

        }
        public void move(int position, MessageList ml){

        }
        public void display(){

        }
    }
    public static void main(String[] args){

    }
}
