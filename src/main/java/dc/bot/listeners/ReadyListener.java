package dc.bot.listeners;

import dc.bot.controllers.ReadyController;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class ReadyListener implements EventListener {
    /*public static void main(String[] args)
            throws LoginException {
        JDA jda = JDABuilder.createDefault(args[0])
                .addEventListeners(new ReadyListener()).build();
    }*/
    private static ReadyController rc;

    public ReadyListener(ReadyController rc) {
        this.rc=rc;
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof ReadyEvent)
            System.out.println("API is ready!");
    }
}
