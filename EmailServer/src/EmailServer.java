import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import static java.util.Objects.isNull;

/**
 A class that stores e-mail messages.
 */
class Mailbox
{
    private String user;
    private ArrayList<Message> messages;
    private  ArrayList<Message> sentMessages;

    public String getUser() {
        return user;
    }

    /**
     Initializes an empty mailbox.
     */
    public Mailbox(String user)
    {
        this.user = user;
        messages = new ArrayList<Message>();
        sentMessages = new ArrayList<Message>();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    /**
     Adds a new message.
     @param m the message
     */
    public void addMessage(Message m)
    {
        messages.add(m);
    }

    public void addSentMessage(Message m)
    {
        sentMessages.add(m);
    }

    /**
     Gets the ith message.
     @param i the message number to get
     @return the ith message
     */
    public Message getMessage(int i)
    {

        return messages.get(i);
    }

    public Message getSentMessage(int i)
    {

        return sentMessages.get(i);
    }

    /**
     Removes the ith message.
     @param i the message number to remove
     */
    public void removeMessage(int i)
    {
        messages.remove(i);
    }

    public void removeSentMessage(int i)
    {
        sentMessages.remove(i);
    }

}


/**
 A class that models an e-mail message.
 */
class Message
{
    private String recipient;
    private String sender;
    private String subject;
    private String messageText;
    private Date date;

    /**
     Takes the sender and recipient
     @param recipient the recipient
     @param sender the sender
     */
    public Message(String recipient, String sender, String subject)
    {
        this.recipient = recipient;
        this.sender = sender;
        this.subject = subject;
        messageText = "";
        this.date = Calendar.getInstance().getTime();
    }

    /**
     Appends a line of text to the message body.
     @param line the line to append
     */
    public void append(String line)
    {
        if (messageText.length() > 0 )
            messageText = messageText + "\n" + line;
        else
            messageText = line;
        System.out.println(" ");

    }

    /**
     Makes the message into one long string.
     */
    public String toString()
    {
        String s = "To: "+recipient+" From: "+sender+" "+subject+" "+date+" "+messageText;
        return s;
    }

    public String getMessageHeader() {
        SimpleDateFormat f = new SimpleDateFormat("\t\tEEE MM dd");
        return "Sender: "+sender + "\nSubject: "  + subject +  " " + f.format(date) + "\n" +messageText.split("\\n")[0] + "\n";
    }

    /**
     Prints the message text.
     */
    public void print()
    {
        System.out.print(this.toString());
    }

    public void send(Mailbox sender, Mailbox recipient, Message message) {
        recipient.addMessage(message);
        sender.addSentMessage(message);
    }

}
class MailboxList {

    private  static ArrayList<Mailbox> mailboxList = new ArrayList<>();
    public static Mailbox GetUserMailbox(String user) {
        for (Mailbox mailbox: mailboxList) {
            if (mailbox.getUser().equals(user))
                return mailbox;
        }
        // user doesn't exist so create this user
        Mailbox mailbox = new Mailbox(user);
        mailboxList.add(mailbox);
        return mailbox;
    }
}

public class EmailServer
{
    //  This classs is a sample test driver.  You should change the messages and users to make it your own.
    public static void main(String[] args)
    {
        //MailboxList Rob = new MailboxList();

        Mailbox sender = MailboxList.GetUserMailbox("Edwin");
        Mailbox recipient = MailboxList.GetUserMailbox("Rob");
        Message email = new Message("Rob", "Edwin", "message 3");
        email.append("This is a sample message");
        email.append("it works!");

        email.print();
        email.send(sender, recipient, email);

        email = new Message("Rob", "Edwin",  "message 2");
        email.append("This is another sample message");
        email.append("it still works.");
        email.print();
        email.send(sender, recipient, email);


        sender = MailboxList.GetUserMailbox("Rob");
        recipient = MailboxList.GetUserMailbox("Edwin");
        Message emailResponse = new Message("Edwin", "Rob", "message 1");
        emailResponse.append("last message");
        emailResponse.append("and it works");
        emailResponse.print();
        emailResponse.send(sender, recipient, emailResponse);
        System.out.println(" ");
        System.out.println("Inbox:");
        for (Message msg: sender.getMessages()) {
            System.out.println(msg.getMessageHeader());
        }
        System.out.println("Sent:");
        for (Message msg: sender.getSentMessages()) {
            System.out.println(msg.getMessageHeader());
        }

        System.out.println("Inbox:");
        for (Message msg: recipient.getMessages()) {
            System.out.println(msg.getMessageHeader());
        }
        System.out.println("Sent:");
        for (Message msg: recipient.getSentMessages()) {
            System.out.println(msg.getMessageHeader());
        }
    }
}


