package com.backend.johnny.service;

import com.backend.johnny.dto.cfa.CfaRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CfaService {

    private final RestTemplate restTemplate;

    public String getCookie(String userId, String userPw) {
        String url = "https://www.cardsales.or.kr/authentication";
        String cookie = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("j_username", userId);
        body.add("j_password", userPw);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

        HttpHeaders responseHeaders = response.getHeaders();
        List<String> cookieList = responseHeaders.get("Set-Cookie");
        if (cookieList != null && !cookieList.isEmpty()) {
            String[] split = cookieList.get(0).split(";");

            for (String part : split) {
                part = part.trim();
                if (part.startsWith("JSESSIONID=")) {
                    cookie += part;
                    break;
                }
            }
        }

        return cookie;
    }

}
