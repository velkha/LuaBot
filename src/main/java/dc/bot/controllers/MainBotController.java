package dc.bot.controllers;

import dc.bot.listeners.ChannelListener;
import dc.bot.listeners.MessageListener;
import dc.bot.listeners.ReadyListener;
import dc.bot.utilities.Utilities;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class MainBotController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainBotController.class);
    public static void main(String[] args) {
        botInit();
    }
    public static void botInit() {

        try {
            MainBotController.startBot();

        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    public static void startBot() throws LoginException {
        LOGGER.info("Comenzando creacion del bot");
        Utilities utils = new Utilities();
        JDA bot = JDABuilder.createDefault(utils.getResource("lua.token", "luaConfig.properties")).setActivity(Activity.playing(utils.getResource("lua.action", "luaConfig.properties"))).build();
        MessageController mc = new MessageController();
        ChannelController cc = new ChannelController();
        ReadyController rc = new ReadyController();
        bot.addEventListener(new MessageListener(mc));
        bot.addEventListener(new ReadyListener(rc));
        bot.addEventListener(new ChannelListener(cc));
        LOGGER.info("Bot iniciado\n"+"Directorio: "+System.getProperty("user.dir")+"\nHome: "+System.getProperty("user.home"));
    }

}
