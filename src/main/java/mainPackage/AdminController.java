package mainPackage;

import com.google.gson.Gson;
import mainPackage.Message.Messages;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class AdminController {

    @RequestMapping(value = "/LoadUsersForAdmin" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorizationResponse LoadUsersForAdmin(String login , String key){
        AuthorizationResponse response = new AuthorizationResponse();

        Gson gson = new Gson();

        Set<String> users = Messages.GetUserChats(login);

        Users.getAllUsers().forEach(item -> {
            users.add(item.getLogin());
        });

        response.setResponseID(0);
        response.setResponseMessage(gson.toJson(users));
        return response;
    }

    @RequestMapping(value = "/deleteUser" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorizationResponse deleteUser(String login , String key , String userToDeleteLogin){
        AuthorizationResponse response = new AuthorizationResponse();

        if(Users.IsUserHaveAccess(login , key)){
            Users.deleteUser(userToDeleteLogin);
            response.setResponseID(0);
            response.setResponseMessage("Successful");
            return response;
        }

        response.setResponseID(2);
        response.setResponseMessage("Error");
        return response;
    }

    @RequestMapping(value = "/banUser" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorizationResponse banUser(String login , String key , String userToBanLogin){
        AuthorizationResponse response = new AuthorizationResponse();

        if(Users.IsUserHaveAccess(login , key)){

            Users.banUser(userToBanLogin);

            response.setResponseID(0);
            response.setResponseMessage("Successful");
            return response;
        }

        response.setResponseID(2);
        response.setResponseMessage("Error");
        return response;
    }

    @RequestMapping(value = "/unbanUser" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorizationResponse unbanUser(String login , String key , String userToUnbanLogin){
        AuthorizationResponse response = new AuthorizationResponse();

        if(Users.IsUserHaveAccess(login , key)){

            Users.unbanUser(userToUnbanLogin);

            response.setResponseID(0);
            response.setResponseMessage("Successful");
            return response;
        }

        response.setResponseID(2);
        response.setResponseMessage("Error");
        return response;
    }
}
