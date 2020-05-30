package mainPackage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Users {
    public static ArrayList<User> usersList;
    public static Map<String, Integer> keys = new HashMap<String, Integer>();

    public static void Init() throws IOException {

        File file= new File ("/Users/vladislav/Desktop/javaJSON.txt");
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

        InputStream is = new FileInputStream("/Users/vladislav/Desktop/javaJSON.txt");
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
            Type itemsListType = new TypeToken<ArrayList<User>>() {}.getType();
            usersList = gson.fromJson(fileAsString, itemsListType);
        }else{
            usersList = new ArrayList<User>();
        }

        if(usersList.size() == 0){
            User.setNextID(0);
        }else{
            int currentID = usersList.get(0).getID();

            for(User currentUser : usersList){
                if(currentID < currentUser.getID()){
                    currentID = currentUser.getID();
                }
            }
        }

    }

    public static void Save() throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(usersList);
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/vladislav/Desktop/javaJSON.txt"));
        writer.write(str);
        writer.close();
    }

    public static boolean FindUser(String login){
        for(User currentUser : usersList){
            if(currentUser.getLogin().equals(login)){
                return true;
            }
        }
        return false;
    }

    public static User FindUserToAuth(String login , String password){
        for(User currentUser : usersList){
            if(currentUser.getLogin().equals(login) && currentUser.getPassword().equals(password)){
                return currentUser;
            }
        }

        return new User("","","");
    }

    public static void AddUser(User user){
        user.setID(User.getNextID());
        usersList.add(user);
    }

    public static boolean IsUserHaveAccess(String login , String key){
        if(keys.get(login) != null){
            if(keys.get(login) == Integer.parseInt(key)){
                return true;
            }
        }
        return false;
    }


}
