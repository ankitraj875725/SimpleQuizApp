package com.example.simplequizapp;

public class TopicItem {
    String topicName;
    int topicImage;

    public TopicItem(String topicName, int topicImage) {
        this.topicName = topicName;
        this.topicImage = topicImage;
    }

    public String getTopicName() {
        return topicName;
    }

    public int getTopicImage() {
        return topicImage;
    }
}
