package mainPackage;

import com.google.gson.Gson;
import mainPackage.CustomClasses.MurmurHash;
import mainPackage.Message.Message;
import mainPackage.Message.Messages;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@RestController
public class MessageController {

    @RequestMapping(value = "/GetUserChats" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorizationResponse getUserChats(String login , String key){
        AuthorizationResponse response = new AuthorizationResponse();

        if(Users.IsUserHaveAccess(login , key)){
            Set<String> users = Messages.GetUserChats(login);

            Gson gson = new Gson();

            String usersInString = gson.toJson(users);

            response.setResponseID(0);
            response.setResponseMessage(usersInString);

            return response;
        }
        response.setResponseID(2);
        response.setResponseMessage("error");
        return response;
    }

    @RequestMapping(value = "/isUserExists" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorizationResponse isUserExists(String senderLogin , String senderKey , String findUserLogin){
        AuthorizationResponse response = new AuthorizationResponse();

        if(Users.IsUserHaveAccess(senderLogin , senderKey)){

            if(Users.FindUser(findUserLogin)) {
                response.setResponseID(0);
                response.setResponseMessage("Exists");

                return response;
            }
        }


        response.setResponseID(1);
        response.setResponseMessage("error");
        return response;
    }

    @RequestMapping(value = "/sendMessage" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorizationResponse sendMessage(String senderLogin , String senderKey , String receiverLogin , String message){
        AuthorizationResponse response = new AuthorizationResponse();

        if(Users.IsUserHaveAccess(senderLogin , senderKey)){
            Message message1 = new Message(senderLogin , receiverLogin , message , new Date(System.currentTimeMillis()));
            Messages.addNewMessage(message1);
        }

        response.setResponseID(1);
        response.setResponseMessage("error");
        return response;
    }

    @RequestMapping(value = "/haveNewMessages" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorizationResponse haveNewMessages(String senderLogin , String senderKey){
        AuthorizationResponse response = new AuthorizationResponse();

        if(Users.IsUserHaveAccess(senderLogin , senderKey)){
            Set<String> users = Messages.haveNewMessages(senderLogin);

            Gson gson = new Gson();

            response.setResponseID(0);
            response.setResponseMessage(gson.toJson(users));
            return response;
        }

        response.setResponseID(1);
        response.setResponseMessage("error");
        return response;
    }



}
