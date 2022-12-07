package utils;


import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static configs.Config.EMAIL_ARRIVING_WAIT;
import static javax.mail.Folder.READ_ONLY;

@Log4j2
@Data
public final class EmailUtils {
    private Folder folder;
    private Session session;
    private Store store;
    private String login;
    private String pass;
    private EmailFolder emailFolder;
    private static final String PROPERTIES_FILE = (System.getProperty("user.dir") + File.separator +
            "src" + File.separator + "main" + File.separator + "resources" + File.separator + "email.properties");

    private static final String PROTOCOL = "imaps";
    private static final String HOST = "imap.mail.ru";

    private static final String MSG_EMAIL_CONNECTION = " --- Connected to email box  --- ";
    private static final String MSG_EMAIL_RECONNECTION = " --- Reconnected to email box --- ";
    private static final String MSG_EMAIL_REFUSED = " --- Connection refused --- ";
    private static final String MSG_EMAIL_NOT_READ = "Email is not read";
    private static final String MSG_MESSAGE_NOT_FOUND = "Message not found. Filter is : ";
    private static final String MSG_PARSE_MASSAGE_IMPOSSIBLE = "Cannot parse message ";
    public static final String MESSAGES_NO_FOUND = "Any messages don't find in SMTP server";
    public static final String TRY_AGAIN = ", try again. Count = ";
    public static final String MSG_EMAIL_NOT_RECEIVED = "Email is not received";
    public static final String LINE_BREAK = "\r\n\r\n";
    public static final String TEXT_PLAIN = "text/plain";

    public enum EmailFolder {
        INBOX("INBOX"),
        SPAM("SPAM");
        private String value;

        private EmailFolder(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum EmailPorts {
        SMTP(465),
        IMAP(993),
        POP3(995);
        private int value;

        EmailPorts(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Connects to email server with credentials provided to read from a given folder of the email application
     *
     * @param username    Email username (e.g. janedoe@email.com)
     * @param password    Email password
     * @param emailFolder Folder in email application to interact with
     */
    public EmailUtils(String username, String password, EmailFolder emailFolder) {
        this.login = username;
        this.pass = password;
        this.emailFolder = emailFolder;
        try {
            Properties prop = new Properties();
            Session session = Session.getInstance(prop);
            Store store = session.getStore(PROTOCOL);
            store.connect(HOST, username, password);
            this.store = store;
            this.folder = store.getFolder(emailFolder.getValue());
            folder.open(READ_ONLY);
            log.info(MSG_EMAIL_CONNECTION);
        } catch (MessagingException e) {
            log.info(MSG_EMAIL_NOT_READ, e);
        }
    }

    public void reconnect() {
        try {
            Properties props = System.getProperties();
            Session session = Session.getInstance(props);
            this.session = session;
            Store store = session.getStore(PROTOCOL);
            store.connect(HOST, login, pass);
            this.folder = store.getFolder(this.emailFolder.getValue());
            folder.open(READ_ONLY);
            log.info(MSG_EMAIL_RECONNECTION + this.login);
        } catch (MessagingException e) {
            log.info(MSG_EMAIL_REFUSED, e);
        }
    }

    public void openFolder(int mode) {
        try {
            this.getFolder().open(mode);
        } catch (MessagingException e) {
            log.info("Can not open folder: " + e);
        }
    }

    public void closeFolder() {
        try {
            this.folder.close(true);
        } catch (Exception e) {
            log.info("Error in attempt to close folder: " + this.folder.getName());
        }
    }

    public int getNumberOfMessages() {
        try {
            return folder.getMessageCount();
        } catch (MessagingException e) {
            log.info(MSG_EMAIL_NOT_READ, e);
            return -1;
        }
    }

    /**
     * Gets a message by its position in the folder. The earliest message is indexed at 1.
     */
    public Message getMessageByIndex(int index) throws MessagingException {
        return folder.getMessage(index);
    }

    public Message getLatestMessage() {
        try {
            return getMessageByIndex(getNumberOfMessages());
        } catch (MessagingException e) {
            log.info(MSG_EMAIL_NOT_READ, e);
            return null;
        }
    }

    public Message getLatestMessage(int index) {
        try {
            return getMessageByIndex(getNumberOfMessages() - Math.abs(index));
        } catch (MessagingException e) {
            log.info(MSG_EMAIL_NOT_READ, e);
            return null;
        }
    }

    /**
     * Gets all messages within the folder
     */
    public Message[] getAllMessages() throws MessagingException {
        return folder.getMessages();
    }

    /**
     * Returns HTML of the email's content
     */
    private static String getTextFromMessage(Message message) {
        String result = "";
        try {
            if (message.isMimeType(TEXT_PLAIN)) {
                result = message.getContent().toString();
            } else if (message.isMimeType("multipart/*")) {
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                result = getTextFromMimeMultipart(mimeMultipart);
            }
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) {
        String result = "";
        try {
            int count = 0;
            count = mimeMultipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType(TEXT_PLAIN)) {
                    result = result + "\n" + bodyPart.getContent();
                    break;
                } else if (bodyPart.isMimeType("text/html")) {
                    String html = (String) bodyPart.getContent();
                    result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
                } else if (bodyPart.getContent() instanceof MimeMultipart) {
                    result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
                }
            }
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Message findMessage(int deepLevel, String... values) {
        WaitUtil.setWait(EMAIL_ARRIVING_WAIT);
        this.reconnect();
        int deepest = Math.min(getNumberOfMessages(), deepLevel);
        List<Message> messages = new ArrayList<>();
        int countWhole = 4;
        int countLoop = 0;
        Message message = null;
        while (countWhole-- > 0 && message == null) {
            countLoop++;
            messages.clear();
            for (int countGetMessage = 0; countGetMessage < 2; countGetMessage++) {
                for (int i = 0; i < deepest; i++) {
                    messages.add(getLatestMessage(i));
                }
                if (messages.size() == 0) {
                    log.error(MESSAGES_NO_FOUND + TRY_AGAIN + countLoop);
                    WaitUtil.setWait(EMAIL_ARRIVING_WAIT);
                } else break;
            }
            if (messages.size() != 0) {
                for (Message item : messages) {
                    String body = getTextFromMessage(item);
                    boolean b = true;
                    for (String value : values) {
                        b = b && body.contains(value);
                    }
                    if (b) {
                        message = item;
                        break;
                    }
                }
                if (message == null) {
                    log.info(MSG_MESSAGE_NOT_FOUND + Arrays.asList(values) + TRY_AGAIN + (countWhole + 1));
                    WaitUtil.setWait(EMAIL_ARRIVING_WAIT);
                }
            }
        }
        return message;
    }

    public static String getSubject(Message message) {
        try {
            return MimeUtility.decodeText(message.getSubject());
        } catch (MessagingException e) {
            log.info(MSG_EMAIL_NOT_READ, e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return StringUtils.EMPTY;
    }

    public static String getLinkVerification(Message message) {
        String body = getTextFromMessage(message);
        return body.substring(body.indexOf("https"), body.indexOf(LINE_BREAK + "Once"));
    }

    public static String getUserNameVerification(Message message) {
        String body = getTextFromMessage(message);
        String name = "name: ";
        return body.substring(body.indexOf(name) + name.length(), body.indexOf(LINE_BREAK + "You'll"));
    }

    public static String getVerificationCode(Message message) {
        String body = getTextFromMessage(message);
        String code = "Code: ";
        return body.substring(body.indexOf(code) + code.length(), body.indexOf(LINE_BREAK + "\r\nIf"));
    }

    public static void isEmailReceived(Message message) {
        Assert.assertNotNull(message, MSG_EMAIL_NOT_RECEIVED);
    }
}