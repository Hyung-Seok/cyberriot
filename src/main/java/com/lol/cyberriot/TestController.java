package com.lol.cyberriot;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map.Entry;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lol.dto.ChampionData;

@RestController
@RequiredArgsConstructor
@RequestMapping("/champion")
public class TestController {

    private final ObjectMapper objectMapper;
    // RestTemplate 객체 생성
    RestTemplate restTemplate = new RestTemplate();

    // 모든 챔피언 정보 가져오기
    @GetMapping("/championData")
    public String championData() throws JsonProcessingException {
        String championdata = "https://ddragon.leagueoflegends.com/cdn/14.20.1/data/ko_KR/champion.json";

        // JSON 데이터 가져오기
        String jsonResponse = restTemplate.getForObject(championdata, String.class);

        // JSON 데이터 파싱
        ChampionData championData = objectMapper.readValue(jsonResponse, ChampionData.class);
//        System.out.println(result); // JSON 데이터 출력
        StringBuilder result = new StringBuilder();

        // 필요한 값 추출 (챔피언 이름)
        for (Entry<String, ChampionData> entry : championData.getData().entrySet()) {
            ChampionData champion = entry.getValue();
            result.append("name : ").append(champion.getName()).append("<br>").append("title : ").append(champion.getTitle()).append("<br><br>");
        }

        return result.toString();
    }

    // 특정 챔피언 정보 가져오기
    @GetMapping("/championData/{championName}")
    public ResponseEntity<?> getChampionInfo(@PathVariable String championName) throws JsonProcessingException {
        String championInfoLinkFormat = "https://ddragon.leagueoflegends.com/cdn/14.20.1/data/ko_KR/champion/%s.json";
        String championInfoLink = String.format(championInfoLinkFormat, championName);
        // JSON 데이터 가져오기
        String jsonResponse = restTemplate.getForObject(championInfoLink, String.class);

        ChampionData championData = objectMapper.readValue(jsonResponse, ChampionData.class);

        return ResponseEntity.ok(championData);
    }

    // 특정 챔피언 이미지 가져오기
    @GetMapping("/championImg/{imgName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imgName) throws JsonProcessingException {
        String chimg = "https://ddragon.leagueoflegends.com/cdn/14.20.1/img/champion/%s";
        String imgUrl = String.format(chimg, imgName);
//
//        try{
//            byte[] imgBytes = restTemplate.getForObject(imgUrl, byte[].class);
//            if(imgBytes != null){
//                ByteArrayResource resource = new ByteArrayResource(imgBytes);
//                return ResponseEntity.ok()
//                        .header(.)
//            }
//        }
        byte[] imgBytes = restTemplate.getForObject(imgUrl, byte[].class);
        ByteArrayResource resource = new ByteArrayResource(imgBytes);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                //파일 다운로드
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imgName + "\"")
                .contentLength(imgBytes.length)
                .body(resource);
    }


    @GetMapping("/match/{matchId}")
    public MatchResponse getMatchByMatchId(@PathVariable Long matchId) {
        MatchResponse matchResponse = new MatchResponse(1L, LocalDateTime.now());
        return matchResponse;
    }
}
