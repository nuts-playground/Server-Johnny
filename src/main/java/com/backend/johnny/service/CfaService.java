package com.backend.johnny.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CfaService {

    private final RestTemplate restTemplate;

    public String getCookie(String userId, String userPw) {
        String url = "https://www.cardsales.or.kr/authentication";
        String cookie = "";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Origin", "https://www.cardsales.co.kr/");
        headers.add("Referer", "https://www.cardsales.co.kr/signin");

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
        System.out.println(responseHeaders);
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

    public String getCardAprv(String userId, String password, String strDate, String endDate) {
        String cookie = getCookie(userId, password);
        String aprvUrl = createAprvUrl(strDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Origin", "https://www.cardsales.co.kr/");
        headers.add("Cookie", cookie);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                aprvUrl,
                HttpMethod.GET,
                request,
                String.class
        );

        String body = response.getBody();
        System.out.println(body);

        return body;
    }

    public static String createAprvUrl(String stdDate, String endDate) {
        int std = Integer.parseInt(stdDate);
        int end = Integer.parseInt(endDate);

        StringBuffer sb = new StringBuffer();
        sb.append("https://www.cardsales.or.kr/page/api/approval/detailTermListAjax?");
        sb.append("q.mbrId=11623895&");
        sb.append("q.merGrpId=1268894544&");
        sb.append("q.stdDate=");
        sb.append(stdDate);
        sb.append("&");
        sb.append("q.endDate=");
        sb.append(endDate);
        sb.append("&");
        for (int i = std; i <= end; i++) {
            sb.append("q.stdDateArray=");
            sb.append(i);
            sb.append("&");
        }
        sb.append("q.dataPerPage=1000&");
        sb.append("currentPage=1");

        return sb.toString();
    }

    public static String createPrchUrl(LocalDate stdDate, LocalDate endDate) {
        int std = Integer.parseInt(stdDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        int end = Integer.parseInt(endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        StringBuffer sb = new StringBuffer();
        sb.append("https://www.cardsales.or.kr/page/api/purchase/termDetail?");
        sb.append("q.searchDateCode=PCA_DATE&");
        sb.append("q.oldMerGrpId=1268894544&");
        sb.append("q.oldSearchStrDate=");
        sb.append(stdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        sb.append("&");
        sb.append("q.oldSearchEndDate=");
        sb.append(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        sb.append("&");
        sb.append("checkbox-inline=on&");
        for (int i = std; i <= end; i++) {
            sb.append("q.chkArr=");
            sb.append(i);
            sb.append("&");
        }
        sb.append("q.dataPerPage=1000&");
        sb.append("currentPage=1");

        return sb.toString();
    }

    public static String createPymUrl(LocalDate stdDate, LocalDate endDate) {
        int std = Integer.parseInt(stdDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        int end = Integer.parseInt(endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        StringBuffer sb = new StringBuffer();
        sb.append("https://www.cardsales.or.kr/page/api/payment/detailTermListAjax?");
        sb.append("q.merGrpId=1268894544&");
        sb.append("q.startDate=");
        sb.append(stdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        sb.append("&");
        sb.append("q.endDate=");
        sb.append(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        sb.append("&");
        for (int i = std; i <= end; i++) {
            sb.append("q.stdDateArray=");
            sb.append(i);
            sb.append("&");
        }
        sb.append("q.dataPerPage=1000&");
        sb.append("currentPage=1");

        return sb.toString();
    }

}
