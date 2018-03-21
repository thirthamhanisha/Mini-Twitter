package edu.sjsu.cmpe275.aop;

import java.io.IOException;
import java.util.Random;

public class TweetServiceImpl implements TweetService {

    /***
     * Following is a tweet implementation.
     * You can tweak the implementation to suit your need, but this file is NOT part of the submission.
     */

	@Override
    public void tweet(String user, String message) throws IllegalArgumentException, IOException {
		
		if(message.length()>140) {
			throw new IllegalArgumentException("tweet more than 140");
			
		}
		int randomVal = 0;
		Random random = new Random();
		randomVal = random.nextInt(3) + 1;
		
		if (randomVal == 1) {
			throw new IOException("network failure tweeting");
		}
		else
		{
    	System.out.printf("User %s tweeted message: %s\n", user, message);
        }
	}

	@Override
    public void follow(String follower, String followee) throws IOException {
		
		
		int randomVal = 0;
		Random random = new Random();
		randomVal = random.nextInt(3) + 1;
		
		if (randomVal == 1) {
			throw new IOException("network failure following");
		}
		
		if (follower == null || follower.isEmpty() || followee == null || followee.isEmpty() || follower == followee ) {
			throw new IllegalArgumentException("follower or followee is empty");
			
		}
		
		else
		{
			System.out.printf("User %s followed user %s \n", follower, followee);
        }
       	
    }

	@Override
	public void block(String user, String follower) throws IOException {
		int randomVal = 0;
		Random random = new Random();
		randomVal = random.nextInt(3) + 1;
		
		if (randomVal == 1) {
			throw new IOException("network failure blocking");
		}
		
		if (follower == null || follower.isEmpty() || user == null || user.isEmpty() || follower == user ) {
			throw new IllegalArgumentException("follower or user is empty");
			
		}
		
		else
		{
       	System.out.printf("User %s blocked user %s \n", user, follower);
		}
	}

}
