package org.example.todo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
@RequiredArgsConstructor
@Service
public class MailService {


    private final JavaMailSender javaMailSender;
    @Value("${MAIL_USERNAME}")
    private String senderEmail; // .env에서 이메일 읽기
    private static String number;
    public static void createNum(){
        Random random=new Random();
        StringBuffer key=new StringBuffer();

        for(int i=0; i<8;i++){
            int idx=random.nextInt(3);

            switch (idx) {
                case 0 :
                    // 0일 때, a~z 까지 랜덤 생성 후 key에 추가
                    key.append((char) (random.nextInt(26) + 97));
                    break;
                case 1:
                    // 1일 때, A~Z 까지 랜덤 생성 후 key에 추가
                    key.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    // 2일 때, 0~9 까지 랜덤 생성 후 key에 추가
                    key.append(random.nextInt(9));
                    break;
            }
        }

        number=key.toString();
    }



    public MimeMessage CreateMail(String email) {
        createNum();

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("이메일 인증 코드");

            String htmlContent = String.format("""
    <html>
    <body>
        <div style="font-family: Arial, sans-serif; padding: 20px;">
            <div style="background-color: #4CAF50; color: white; padding: 10px; text-align: center;">
                <h2>이메일 인증</h2>
            </div>
            <div style="background-color: #f4f4f4; padding: 20px; border-radius: 5px;">
                <p>안녕하세요,</p>
                <p>귀하의 이메일 인증 코드는 다음과 같습니다:</p>
                <p style="font-size: 24px; font-weight: bold; color: #4CAF50; letter-spacing: 5px;">%s</p>
                <p>이 코드를 입력하여 이메일 인증을 완료해 주세요.</p>
            </div>
            <div style="margin-top: 20px; font-size: 12px; color: #777; text-align: center;">
                <p>본 메일은 발신 전용입니다. 문의사항이 있으시면 고객센터로 연락해 주세요.</p>
                <p>&copy; 2025 Your Company Name. All rights reserved.</p>
            </div>
        </div>
    </body>
    </html>
""", number);


            message.setText(htmlContent, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }



    public String sendMail(String mail) {
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);

        return number;
    }



}
