package edu.sjsu.cmpe275.aop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of the submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        TweetService tweeter = (TweetService) ctx.getBean("tweetService");
        TweetStats stats = (TweetStats) ctx.getBean("tweetStats");

        try {   
        	tweeter.follow("alex", "bob");
            tweeter.follow("suhas", "bob");
            tweeter.block("alex", "bob");
            tweeter.block("suhas", "bob");
            
        	/*tweeter.follow("alex", "bob");
            tweeter.follow("suhas", "amey");
            tweeter.follow("carl", "bob");
            tweeter.block("bob", "alex");
        	
        	tweeter.tweet("alex", "first tweet");
            tweeter.follow("bob", "alex");
            tweeter.follow("cat", "alex");
            tweeter.follow("alex", "bob1");
            tweeter.follow("h", "bob1");
            tweeter.follow("k", "bob1");
            tweeter.block("j", "bob");
            tweeter.block("k", "bob2");
            tweeter.block("l", "bob2");
            tweeter.block("m", "bob");
            tweeter.tweet("bob", "first tweetywssd");
            tweeter.tweet("alex", "first tweetywssd");
            tweeter.tweet("hjy", "first tweety");
            tweeter.tweet("mohan", "All men must die! All men must serve!");
            tweeter.tweet("alex", "first tweetywssd");
            tweeter.tweet("alex", "first tweetywssd");*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Most productive user: " + stats.getMostProductiveUser());
        System.out.println("Most popular user: " + stats.getMostFollowedUser());
        System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweetAttempted());
        System.out.println("Most unpopular follower " + stats.getMostBlockedFollower());
        ctx.close();
    }
}
