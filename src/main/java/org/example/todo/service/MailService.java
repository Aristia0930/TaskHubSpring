package org.example.todo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.todo.exception.customexception.SendMailException;
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
    private String senderEmail;
    private String verificationCode;

    private void createVerificationCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int idx = random.nextInt(3);
            switch (idx) {
                case 0 -> key.append((char) (random.nextInt(26) + 97));
                case 1 -> key.append((char) (random.nextInt(26) + 65));
                case 2 -> key.append(random.nextInt(10));
            }
        }

        this.verificationCode = key.toString();
    }

    private MimeMessage createMail(String recipientEmail) {
        createVerificationCode();

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, recipientEmail);
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
            """, verificationCode);

            message.setText(htmlContent, "UTF-8", "html");
        } catch (MessagingException e) {
            throw new RuntimeException("메일 생성 중 오류 발생", e);
        }

        return message;
    }

    public void sendMail(String recipientEmail) {
        MimeMessage message = createMail(recipientEmail);

        try{
            javaMailSender.send(message);
            //세션으로 저장하기

        }catch (Exception e){
            throw new SendMailException("메일 전송중 오류 발생",e);
        }

    }
    public String getVerificationCode() {
        return verificationCode;
    }


    }

