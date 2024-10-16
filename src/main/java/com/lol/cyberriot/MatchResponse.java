package com.lol.cyberriot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Period;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponse {

    private Long matchId;

    private LocalDateTime playTime;
}
