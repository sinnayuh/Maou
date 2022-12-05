package wtf.gun.maou;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public final class Bot {
    public Bot() throws InterruptedException {
        // Input bot token
        JDA jda = JDABuilder.createDefault("TOKEN")
                // Set activity of bot
                .setActivity(Activity.watching("VALORANT"))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableCache(CacheFlag.FORUM_TAGS)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                // Initialize commands
                .addEventListeners(new Nuke(), new Emoji(), new Channel(), new Role(), new Member(), new Create())
                .build()
                .awaitReady();
    }
    

}
