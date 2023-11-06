import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSenderGUI {
    private JFrame frame;
    private JTextField toField;
    private JTextField subjectField;
    private JTextArea messageArea;

    public EmailSenderGUI() {
        frame = new JFrame("メール送信アプリ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel toLabel = new JLabel("宛先:");
        toField = new JTextField(20);
        JLabel subjectLabel = new JLabel("件名:");
        subjectField = new JTextField(20);
        JLabel messageLabel = new JLabel("本文:");
        messageArea = new JTextArea(10, 20);
        JButton sendButton = new JButton("送信");

        panel.add(toLabel);
        panel.add(toField);
        panel.add(subjectLabel);
        panel.add(subjectField);
        panel.add(messageLabel);
        panel.add(new JScrollPane(messageArea));
        panel.add(sendButton);

        frame.add(panel);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendEmail();
            }
        });

        frame.setVisible(true);
    }

    private void sendEmail() {
        final String username = "your_email@gmail.com"; // 送信元のメールアドレス
        final String password = "your_password"; // 送信元のメールアカウントのパスワード

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTPサーバーのホスト名
        props.put("mail.smtp.port", "587"); // SMTPサーバーのポート

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toField.getText()));
            message.setSubject(subjectField.getText());
            message.setText(messageArea.getText());

            Transport.send(message);

            JOptionPane.showMessageDialog(frame, "メールを送信しました。");
        } catch (MessagingException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "メールの送信中にエラーが発生しました。");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EmailSenderGUI();
            }
        });
    }
}

