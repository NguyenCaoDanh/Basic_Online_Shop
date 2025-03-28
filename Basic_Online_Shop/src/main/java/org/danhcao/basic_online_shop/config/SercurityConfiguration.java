package org.danhcao.basic_online_shop.config;


import org.danhcao.basic_online_shop.config.jwt.JwtFilter;
import org.danhcao.basic_online_shop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SercurityConfiguration {


    @Autowired
    @Lazy // Tránh vấn đề vòng lặp phụ thuộc khi sử dụng `JwtFilter` và `AccountService`
    private JwtFilter jwtFilter;
    @Autowired
    @Lazy
    private AccountService accountService;

    /**
     * Cấu hình SecurityFilterChain cho Spring Security.
     *
     * @param httpSecurity Cấu hình HttpSecurity để tùy chỉnh bảo mật.
     * @return SecurityFilterChain đã cấu hình.
     * @throws Exception Lỗi cấu hình bảo mật.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Vô hiệu hóa CSRF (sử dụng token để bảo mật thay thế)
                .authorizeHttpRequests(auth -> auth
                                // Cho phép truy cập không cần xác thực vào các URL dành cho anonymous
                                .requestMatchers(ApiUrl.URL_ANONYMOUS).permitAll()
                                // Yêu cầu vai trò USER để truy cập các URL dành cho user
                                .requestMatchers(ApiUrl.URL_USER).hasRole("USER")
                                // Yêu cầu vai trò ADMIN để truy cập các URL dành cho admin
                                .requestMatchers(ApiUrl.URL_ADMIN).hasRole("ADMIN")
                                .anyRequest().authenticated()  // Các request khác yêu cầu xác thực
                        // Các request khác cần xác thực (đăng nhập)
                )
                .logout(LogoutConfigurer::permitAll) // Cho phép tất cả truy cập API đăng xuất
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sử dụng JWT, không lưu trạng thái session
                .authenticationProvider(authenticationProvider()) // Cấu hình Provider để xác thực
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Thêm JwtFilter trước UsernamePasswordAuthenticationFilter
                .build();
    }

    /**
     * Cấu hình Provider để xác thực với `AccountService`.
     *
     * @return AuthenticationProvider được cấu hình.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(accountService); // Dịch vụ xử lý logic xác thực
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // Mã hóa mật khẩu
        return authenticationProvider;
    }

    /**
     * Cấu hình mã hóa mật khẩu.
     *
     * @return PasswordEncoder sử dụng thuật toán BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Cung cấp AuthenticationManager từ cấu hình hiện tại.
     *
     * @param configuration Cấu hình xác thực.
     * @return AuthenticationManager để xác thực người dùng.
     * @throws Exception Lỗi cấu hình.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Cấu hình CORS (Cross-Origin Resource Sharing) để cho phép React frontend truy cập API.
     *
     * @return CorsFilter được cấu hình.
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000"); // Cho phép truy cập từ React frontend
        corsConfiguration.addAllowedHeader("*"); // Cho phép tất cả các header
        corsConfiguration.addAllowedMethod("*"); // Cho phép tất cả các phương thức (GET, POST, PUT, DELETE, ...)
        corsConfiguration.setAllowCredentials(true); // Cho phép gửi cookie và thông tin xác thực

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }

    /**
     * Cấu hình truy cập tài nguyên tĩnh.
     * Dùng cho các file hình ảnh lưu trữ trong thư mục `static/images`.
     */
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/images/**") // Đường dẫn API để truy cập ảnh
                    .addResourceLocations("classpath:/static/images/"); // Nơi lưu trữ file ảnh trong dự án
        }
    }

}
