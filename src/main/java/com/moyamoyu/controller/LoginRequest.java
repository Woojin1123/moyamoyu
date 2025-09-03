package com.moyamoyu.controller;

public record LoginRequest(
        String email,
        String password
) { }
