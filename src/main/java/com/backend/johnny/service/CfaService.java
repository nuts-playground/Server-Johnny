package com.backend.johnny.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        headers.add("Origin", "https://www.cardsales.or.kr/");
        headers.add("Referer", "https://www.cardsales.or.kr/signin");

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

    public String getMbrId(String cookie) throws JsonProcessingException {
        String mbrId = null;

        String url = "https://www.cardsales.or.kr/page/api/member/join/searchMember";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Origin", "https://www.cardsales.or.kr/");
        headers.add("Cookie", cookie);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
        );

        String body = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(body);
        if (jsonNode.has("mbrId")) {
            mbrId = jsonNode.get("mbrId").asText();
        }

        return mbrId;
    }

    public List<String> getMerGrpId(String cookie) throws JsonProcessingException {
        List<String> merGrpIdList = new ArrayList<>();

        String url = "https://www.cardsales.or.kr/page/api/commonCode/merGrpCode";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Origin", "https://www.cardsales.or.kr/");
        headers.add("Cookie", cookie);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
        );

        String body = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(body);
        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                if (node.has("code")) {
                    merGrpIdList.add(node.get("code").asText());
                }
            }
        }

        return merGrpIdList;
    }

    public String getCardAprv(String userId, String password, String strDate, String endDate) throws JsonProcessingException {
        String cookie = getCookie(userId, password);
        String mbrId = getMbrId(cookie);
        List<String> merGrpIdList = getMerGrpId(cookie);

        String result = "";
        for (String merGrpId : merGrpIdList) {
            String aprvUrl = createAprvUrl(strDate, endDate, mbrId, merGrpId);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Origin", "https://www.cardsales.or.kr/");
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
            result += body;
        }

        return result;
    }

    public static String createAprvUrl(String stdDate, String endDate, String mbrId, String merGrpId) {
        int std = Integer.parseInt(stdDate);
        int end = Integer.parseInt(endDate);

        StringBuffer sb = new StringBuffer();
        sb.append("https://www.cardsales.or.kr/page/api/approval/detailTermListAjax?");
        sb.append("q.mbrId=");
        sb.append(mbrId);
        sb.append("&");
        sb.append("q.merGrpId=");
        sb.append(merGrpId);
        sb.append("&");
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

    public static String createPrchUrl(LocalDate stdDate, LocalDate endDate, String merGrpId) {
        int std = Integer.parseInt(stdDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        int end = Integer.parseInt(endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        StringBuffer sb = new StringBuffer();
        sb.append("https://www.cardsales.or.kr/page/api/purchase/termDetail?");
        sb.append("q.searchDateCode=PCA_DATE&");
        sb.append("q.oldMerGrpId=");
        sb.append(merGrpId);
        sb.append("&");
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

    public static String createPymUrl(LocalDate stdDate, LocalDate endDate, String merGrpId) {
        int std = Integer.parseInt(stdDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        int end = Integer.parseInt(endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        StringBuffer sb = new StringBuffer();
        sb.append("https://www.cardsales.or.kr/page/api/payment/detailTermListAjax?");
        sb.append("q.merGrpId=");
        sb.append(merGrpId);
        sb.append("&");
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
