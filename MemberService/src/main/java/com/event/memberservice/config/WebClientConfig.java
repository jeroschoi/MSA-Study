package com.event.memberservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient 인스턴스를 애플리케이션 전역에서 재사용할 수 있도록 Bean으로 등록하는 설정 클래스입니다.
 */
@Configuration
public class WebClientConfig {

    // application.yml(or.properties) 파일에 설정된 메시지 서비스의 URL을 가져옵니다.
    @Value("${app.api.message-service-url}")
    private String messageServiceUrl;

    /**
     * 메시지 서비스와 통신하기 위한 WebClient Bean을 생성합니다.
     * 이렇게 Bean으로 등록해두면, 애플리케이션이 시작될 때 딱 한 번만 생성되고,
     * 필요한 곳 어디서든 주입받아 재사용할 수 있습니다.
     * @return 미리 설정된 WebClient 인스턴스
     */
    @Bean
    public WebClient messageWebClient() {
        return WebClient.builder()
                .baseUrl(messageServiceUrl) // 모든 요청의 기본 URL을 설정합니다.
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // 모든 요청에 기본적으로 포함될 헤더를 설정합니다.
                .build();
    }
}