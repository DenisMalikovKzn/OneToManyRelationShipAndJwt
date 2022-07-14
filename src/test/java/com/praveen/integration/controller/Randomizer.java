package com.praveen.integration.controller;

public class Randomizer {
    public static int generate(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
