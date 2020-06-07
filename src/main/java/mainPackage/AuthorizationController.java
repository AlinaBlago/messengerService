package mainPackage;

import mainPackage.CustomClasses.MurmurHash;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {
    @RequestMapping(value = "/SignUp" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorizationResponse SignUp(String name , String login , String password){
        AuthorizationResponse response = new AuthorizationResponse();

        if(Users.FindUser(login) == false){
            Users.AddUser(new User(name , login , password));
            response.setResponseID(0);
            response.setResponseMessage("Successful");
            return response;
        }
        response.setResponseID(2);
        response.setResponseMessage("Warning");

        return response;
    }

    @RequestMapping(value = "/Login" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorizationResponse Login(String login , String password){
        AuthorizationResponse response = new AuthorizationResponse();

        User foundedUser = Users.FindUserToAuth(login , password);
        if(foundedUser.getName().length() == 0 && foundedUser.getLogin().length() == 0 && foundedUser.getPassword().length() == 0){
            response.setResponseID(3);
            response.setResponseMessage("Wrong password or login");
            return response;
        }
        response.setResponseID(0);
        int userKey = MurmurHash.hash32((login + System.currentTimeMillis()));
        Users.keys.put(login , userKey);
        response.setResponseMessage(Integer.toString(userKey));
        return response;
    }


}

