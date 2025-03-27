package org.danhcao.basic_online_shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

/**
 * EmailSender cung cấp một tiện ích chung để gửi email trong ứng dụng.
 * Lớp này cũng cấu hình taskExecutor cho các tác vụ bất đồng bộ.
 */
@Service
@Configuration
@EnableAsync
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Gửi một email đơn giản bất đồng bộ.
     *
     * @param toEmail Địa chỉ email của người nhận.
     * @param subject Chủ đề của email.
     * @param body    Nội dung của email.
     */
    @Async("taskExecutor")
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    /**
     * Cấu hình ThreadPoolTaskExecutor để xử lý các tác vụ bất đồng bộ.
     *
     * @return Executor để sử dụng với @Async.
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Số luồng tối thiểu
        executor.setMaxPoolSize(10); // Số luồng tối đa
        executor.setQueueCapacity(100); // Dung lượng hàng đợi
        executor.setThreadNamePrefix("AsyncThread-"); // Tiền tố tên luồng
        executor.initialize();
        return executor;
    }
}