package com.lol.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class ChampionData {

    private Map<String, ChampionData> data;

    private String name;

    private String title;
}

