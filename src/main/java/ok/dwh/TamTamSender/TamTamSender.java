package ok.dwh.TamTamSender;

import chat.tamtam.botapi.TamTamBotAPI;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatType;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.TextFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class TamTamSender {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    TamTamBotAPI api;

    TamTamSender(String accessToken) {
        this.api = TamTamBotAPI.create(accessToken);
    }

    public void sendMessageToChat(String message, Long chatId) {
        if (!message.isEmpty() && chatId != null) {
            NewMessageBody body = new NewMessageBody(message, null, null)
                .format(TextFormat.HTML);
            try {
                api.sendMessage(body).chatId(chatId).execute();
            } catch (APIException | ClientException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    public boolean sendMessageToAllChats(String message) {
        try {
            List<Chat> chats = api.getChats().execute().getChats();
            for (Chat chat : chats) {
                if (chat.getType().equals(ChatType.CHAT)) {
                    sendMessageToChat(message, chat.getChatId());
                }
            }
            return true;
        } catch (APIException | ClientException e) {
            LOG.error(e.getMessage());
        }
        return false;
    }

}