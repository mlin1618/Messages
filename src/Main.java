/**
 * Created by ml996 on 1/31/17.
 */
import java.util.*;
import java.security.*;
import java.text.*;

public class Main {
    public static class User{
        public final String number;
        public ArrayList<MessageList> messages = new ArrayList<MessageList>();
        public User(String n){
            number = n;
        }
        public void createNewList(String name,User two){
            MessageList newML = new MessageList(name,this, two);
            this.messages.add(newML);
            MessageList newML2 = new MessageList(name,two,this);
            newML2.key = newML.key;
            two.messages.add(newML2);
        }
        public boolean sendMessage(int num, Message me, User two){
            this.messages.get(num).addMessage(me);
            int index = -1;
            for(int i = 0; i < two.messages.size(); i++){
                if(two.messages.get(i).key == this.messages.get(num).key){
                    index = i;
                    break;
                }
            }
            if(index == -1)
                return false;
            two.messages.get(index).addMessage(me);
            return true;
        }
    }
    public static class Message{
        public final String number;
        public final String text;
        public final long time;
        public Message(String n, String t){
            number = n;
            text = t;
            time = System.currentTimeMillis();
        }
    }
    public static class MessageList{
        public ArrayList<Message> m = new ArrayList<Message>();
        public String name;
        public User me;
        public User you;
        public int key;
        public MessageList(String n, User one, User two) {
            name = n;
            me = one;
            you = two;
            Random r = new Random();
            key = r.nextInt();
        }
        public void addMessage(Message me){
            if(m.size() == 0) {
                m.add(me);
            }
            else{
                for(int i = 0; i < m.size(); i++){
                    if(me.time >= m.get(i).time){
                        m.add(i, me);
                        return;
                    }
                }
                m.add(m.size(), me);
            }
        }
        public void remove(int position){
            m.remove(position);
        }
        public void erase(){
            m.clear();
        }
        public void moveMessage(int position, MessageList ml){
            ml.addMessage(m.get(position));
            m.remove(position);
        }
        public void display(){
            System.out.println("\n"+name+": ");
            for(int i = m.size()-1; i >= 0; i--){
                SimpleDateFormat s = new SimpleDateFormat("MMM dd, yyyy; HH:mm");
                Date d = new Date(m.get(i).time);
                System.out.println("{" + (m.size()-i) + "}" +"["+s.format(d) + "] <" + m.get(i).number + "> " + m.get(i).text);
            }
        }
    }
    public static void main(String[] args){
        TreeMap<String,User> users = new TreeMap<String,User>();
        System.out.println("Welcome to Messages!");
        loop1:while(true) {
            System.out.println("Enter your phone number.");
            Scanner scan = new Scanner(System.in);
            String phoneNum = scan.nextLine();
            if(!users.containsKey(phoneNum)){
                users.put(phoneNum, new User(phoneNum));
            }
            loop2:while(true) {
                System.out.println("\nWhat would you like to do? \n (1) Create new Conversation with someone (2) Access a Conversation (3) Use new phone number (4) Exit");
                String num1 = scan.nextLine();
                if (num1.equals("1")) {
                    System.out.println("Enter a name for this Conversation");
                    String mlName = scan.nextLine();
                    System.out.println("Enter the phone number you wish to add");
                    String phoneNum2 = scan.nextLine();
                    if(!users.containsKey(phoneNum2)){
                        users.put(phoneNum2, new User(phoneNum2) );
                    }
                    users.get(phoneNum).createNewList(mlName,users.get(phoneNum2));
                } else if (num1.equals("2")) {
                    System.out.println("Here are your Conversations: ");
                    for(int i = 0; i < users.get(phoneNum).messages.size(); i++){
                        System.out.println("[" + i + "]" + users.get(phoneNum).messages.get(i).name + "("+users.get(phoneNum).messages.get(i).you.number +")");
                    }
                    System.out.println("Enter the number (in the bracket) of the Conversation you want to access.");
                    int num2 = scan.nextInt();
                    scan.nextLine();
                    loop3:while(true) {
                        System.out.println("\nWhat would you like to do? (1) Display Conversation (2) Send Message (3) Remove Message (4) Clear Conversation (5) Move Message to other Conversation (6) Leave Conversation");
                        String num3 = scan.nextLine();
                        if (num3.equals("1")) {
                            users.get(phoneNum).messages.get(num2).display();
                        } else if (num3.equals("2")) {
                            System.out.println("Enter the message you wish to send");
                            String mText = scan.nextLine();
                            Message sendM = new Message(users.get(phoneNum).number, mText);
                            users.get(phoneNum).sendMessage(num2, sendM, users.get(phoneNum).messages.get(num2).you);
                        } else if (num3.equals("3")) {
                            System.out.println("Enter the # of the message (the # in curly braces on the left) you wish to delete. Note: the other person can still view the message.");
                            int numMessageDel = scan.nextInt();
                            scan.nextLine();
                            users.get(phoneNum).messages.get(num2).remove(users.get(phoneNum).messages.get(num2).m.size() -numMessageDel);
                        } else if (num3.equals("4")) {
                            users.get(phoneNum).messages.get(num2).erase();
                            System.out.println("Conversation cleared. (Note: the other person can still view the conversation.");
                        } else if (num3.equals("5")) {
                            System.out.println("Enter the # of the message (the # in curly braces on the left) you wish to move.");
                            int numMessageMove = scan.nextInt();
                            System.out.println("Enter the # of the Conversation you wish to move this message to.");
                            int numConvoMove = scan.nextInt();
                            users.get(phoneNum).messages.get(num2).moveMessage(users.get(phoneNum).messages.get(num2).m.size() -numMessageMove,users.get(phoneNum).messages.get(numConvoMove));
                            System.out.println("Message successfully moved.");
                            scan.nextLine();
                        } else if(num3.equals("6")){
                            break loop3;
                        }
                    }

                } else if (num1.equals("3")) {
                    break loop2;
                } else {
                    break loop1;
                }
            }
        }
    }
}
