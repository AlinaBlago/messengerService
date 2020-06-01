package mainPackage.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mainPackage.User;


import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Predicate;

public class Messages {
    public static ArrayList<Message> messages;
    public static ArrayList<Message> unreadMessages;

    public static void Init() throws IOException {

        unreadMessages = new ArrayList<Message>();

        File file= new File ("/Users/vladislav/Desktop/javaMessagesJson.txt");
        FileWriter fw;
        if (file.exists())
        {
            fw = new FileWriter(file,true);//if file exists append to file. Works fine.
        }
        else
        {
            file.createNewFile();
            fw = new FileWriter(file);
        }

        Gson gson = new Gson();

        InputStream is = new FileInputStream("/Users/vladislav/Desktop/javaMessagesJson.txt");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null)
        {
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        String fileAsString = sb.toString();

        if(fileAsString.length() > 3) {
            Type itemsListType = new TypeToken<ArrayList<Message>>() {}.getType();
            messages = gson.fromJson(fileAsString, itemsListType);
        }else{
            messages = new ArrayList<Message>();
        }



    }

    public static void Save() throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(messages);
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/vladislav/Desktop/javaMessagesJson.txt"));
        writer.write(str);
        writer.close();
    }

    public static Set<String> GetUserChats(String login){
        Set<String> usersToReturn = new HashSet<>();

        for(Message msg1 : messages){
            if(msg1.getSender().equals(login) || msg1.getReceiver().equals(login)){
                usersToReturn.add(msg1.getReceiver());
                usersToReturn.add(msg1.getSender());
            }
        }

        for(Message msg : unreadMessages){
            if(msg.getSender().equals(login) || msg.getReceiver().equals(login)){
                usersToReturn.add(msg.getSender());
                usersToReturn.add(msg.getReceiver());
            }
        }

        return usersToReturn;
    }

    public static void addNewMessage(Message message){
        unreadMessages.add(message);
    }

    public static Set<String> haveNewMessages(String receiverLogin){
        Set<String> usersToReturn = new HashSet<>();

        if(unreadMessages.isEmpty()) return usersToReturn;

        ArrayList<Message> messagesToRelocate = new ArrayList<>();

        for(Message msg1 : unreadMessages){
            if(msg1.getReceiver().equals(receiverLogin)){
                usersToReturn.add(msg1.getReceiver());
                usersToReturn.add(msg1.getSender());
                messagesToRelocate.add(msg1);
            }
        }

        if(messagesToRelocate.size() > 0){
            unreadMessages.removeAll(messagesToRelocate);
            messages.addAll(messagesToRelocate);
        }

        return usersToReturn;
    }

    public static ArrayList<Message> getChat(String senderLogin , String receiverLogin){
        ArrayList<Message> messagesToReturn = new ArrayList<Message>();

        for(Message msg : messages){
            if(msg.getSender().equals(senderLogin) && msg.getReceiver().equals(receiverLogin) ||
            msg.getReceiver().equals(senderLogin) && msg.getSender().equals(receiverLogin)){
                messagesToReturn.add(msg);
            }
        }

        for(Message msg : unreadMessages){
            if(msg.getSender().equals(senderLogin) && msg.getReceiver().equals(receiverLogin) ||
                    msg.getReceiver().equals(senderLogin) && msg.getSender().equals(receiverLogin)){
                messagesToReturn.add(msg);
            }
        }

        return messagesToReturn;
    }

}
