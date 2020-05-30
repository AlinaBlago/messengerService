package mainPackage;

import com.google.gson.Gson;
import mainPackage.CustomClasses.MurmurHash;
import mainPackage.Message.Messages;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class MessageController {

    @RequestMapping(value = "/GetUserChats" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorizationResponse Login(String login , String key){
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
}
