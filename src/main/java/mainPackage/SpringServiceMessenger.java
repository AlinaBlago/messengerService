package mainPackage;

import mainPackage.Message.Message;
import mainPackage.Message.Messages;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;
import java.io.IOException;

@SpringBootApplication
public class SpringServiceMessenger {
    public static  void main(String[] args) throws IOException {
        Users.Init();
        Messages.Init();
        SpringApplication.run(SpringServiceMessenger.class);
    }

    @PreDestroy
    public void onExit() throws IOException {
        Users.Save();
        Messages.Save();
    }
}
