package com.snackify.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

  @Autowired private JavaMailSender mailSender;

  // ✅ Generate random 6-digit OTP
  public String generateOtp() {
    Random random = new Random();
    int otp = 100000 + random.nextInt(900000); // 6-digit
    return String.valueOf(otp);
  }

  // ✅ Send OTP email
  public void sendOtpEmail(String toEmail, String otp) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo(toEmail);
    helper.setSubject("Your Snackify OTP Code");
    helper.setText("Your OTP is: " + otp + "\nIt is valid for 5 minutes.", true);

    mailSender.send(message);
  }
}
