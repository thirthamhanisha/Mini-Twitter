package edu.sjsu.cmpe275.aop.aspect;

import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.TweetStatsImpl;

@Aspect
@Order(0)
/**
 * 
 * @author thirt Hanisha
 * This is to implement a mini twitter application
 * 
 *  */
public class StatsAspect {
    /***
     * Following is tweet implementation of this aspect.
     * i am usuing around advice to to check if the tweet is greater than 140 before execution
     * and after execution i add it to the tweet hashmap and return the longesttweetattempted and the most productive user
     */

	@Autowired TweetStatsImpl stats;
	
	@Around("execution(public void edu.sjsu.cmpe275.aop.TweetService.tweet(..))")
	public void tweetAfterReturningAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
		if(!TweetStatsImpl.networkFailure)
		{		

			System.out.println("before of tweet of "+joinPoint.getSignature()+" "+joinPoint.getSignature().getName());
			String user = joinPoint.getArgs()[0].toString();
			String message = joinPoint.getArgs()[1].toString();
			
			if(message.length() > TweetStatsImpl.longestTweetAttempted)
				TweetStatsImpl.longestTweetAttempted = message.length();
			
			if(message.length() > 140 )
			{
				System.out.println("Tweet cannot be more than 140 characters.");
			}
			else
			{
				joinPoint.proceed();
		
		System.out.printf("After returning the exectuion of the tweet method %s\n", joinPoint.getSignature().getName());
		//stats.resetStatsAndSystem();
		/*String user = joinPoint.getArgs()[0].toString();
		String message = joinPoint.getArgs()[1].toString();
		*/
		System.out.println(TweetStatsImpl.tweet.size());
		System.out.println(TweetStatsImpl.tweet.size());
		
		if(TweetStatsImpl.tweet.size() == 0) {
			TweetStatsImpl.mostProductiveUser = user;
			TweetStatsImpl.longestTweetExecuted = message.length();
			TweetStatsImpl.tweet.put(user, message.length());
		}
		else 
		{
			if(message.length() > TweetStatsImpl.longestTweetExecuted) {
				TweetStatsImpl.longestTweetExecuted = message.length();
			}
			if(TweetStatsImpl.tweet.containsKey(user)) {
				
				int totallength = TweetStatsImpl.tweet.get(user) + message.length();
				TweetStatsImpl.tweet.put(user, totallength);
			}
			else {
				TweetStatsImpl.tweet.put(user, message.length());
			}
				System.out.println(TweetStatsImpl.tweet.get(TweetStatsImpl.mostProductiveUser));
				System.out.println(TweetStatsImpl.tweet.get(user));
		    if(TweetStatsImpl.tweet.get(TweetStatsImpl.mostProductiveUser) != null) {
		    	System.out.println("Idiot"+TweetStatsImpl.tweet.get(user) + " " + TweetStatsImpl.tweet.get(TweetStatsImpl.mostProductiveUser));
			if(TweetStatsImpl.tweet.get(user) > TweetStatsImpl.tweet.get(TweetStatsImpl.mostProductiveUser)) {
				
				TweetStatsImpl.mostProductiveUser = user;
			}
			
			else if(TweetStatsImpl.tweet.get(user) == TweetStatsImpl.tweet.get(TweetStatsImpl.mostProductiveUser)) {	
				if(user.compareTo(TweetStatsImpl.mostProductiveUser) <= 0) {
					TweetStatsImpl.mostProductiveUser = user;
				}
			}
				}
				else {
					TweetStatsImpl.mostProductiveUser = user;
				}
		}
			}
		}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		TweetStatsImpl.networkFailure = false;
				
	}
	
	//checks if the followee is already following the follower or 
	/*@Before("execution(public void edu.sjsu.cmpe275.aop.TweetService.follow(..))")
	public void followBeforeAdvice(JoinPoint joinPoint) {
		
	}*/
	
	 /***
     * Following is follow implementation of this aspect.
     * i am usuing around advice to to check if the the followee and follower are the same or follower is blocked by the followee
     * before executing the follow method
     * and after execution it add it to the follow hashmap and 
     * return the mostfollowed user.
     */
	
	@Around("execution(public void edu.sjsu.cmpe275.aop.TweetService.follow(..))")
	public void followAfterReturningAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
		if(!TweetStatsImpl.networkFailure)
		{
			System.out.printf("Before the execution of the method %s\n", joinPoint.getSignature().getName());
			String follower = joinPoint.getArgs()[0].toString();
			String followee = joinPoint.getArgs()[1].toString();
			
			if(TweetStatsImpl.block.containsKey(follower) && TweetStatsImpl.block.get(follower).contains(followee))
			{
				System.out.printf("%s is blocked by %s", follower, followee);
			}
			else if(follower.equals(followee))
			{
				System.out.printf("%s cannot follow himself/herself", follower);
			}
			else if(TweetStatsImpl.follow.containsKey(followee) && TweetStatsImpl.follow.get(followee).contains(follower)) {
				
				System.out.printf("%s is already following %s", follower, followee);
			}
		
			else {
				joinPoint.proceed();
			
		System.out.printf("After Returning the execution of the method %s\n", joinPoint.getSignature().getName());
		
		/*String follower = joinPoint.getArgs()[0].toString();
		String followee = joinPoint.getArgs()[1].toString();*/
		
		HashSet<String> followerSet;
		if(TweetStatsImpl.follow.size() == 0) {
			TweetStatsImpl.mostFollowedUser = followee;
			followerSet =new HashSet<String>();
			followerSet.add(follower);
			TweetStatsImpl.follow.put(followee, followerSet);
		}
		
		else 
		{
			if(TweetStatsImpl.follow.containsKey(followee)) {
				followerSet = TweetStatsImpl.follow.get(followee);
				followerSet.add(follower);
				TweetStatsImpl.follow.put(followee, followerSet);
				
			}
			else {
				followerSet =new HashSet<String>();
				
				followerSet.add(follower);
				TweetStatsImpl.follow.put(followee, followerSet);
			}
			if(TweetStatsImpl.mostFollowedUser!=null && TweetStatsImpl.follow.get(TweetStatsImpl.mostFollowedUser)!=null)
			{
			if(TweetStatsImpl.follow.get(followee).size() > TweetStatsImpl.follow.get(TweetStatsImpl.mostFollowedUser).size()) {
				TweetStatsImpl.mostFollowedUser = followee;
			}
			else if(TweetStatsImpl.follow.get(followee).size() == TweetStatsImpl.follow.get(TweetStatsImpl.mostFollowedUser).size()) {	
				if(followee.compareTo(TweetStatsImpl.mostFollowedUser) <= 0) {
					TweetStatsImpl.mostFollowedUser = followee;
				}
			}
		}
			else
			{
				TweetStatsImpl.mostFollowedUser = followee;
			}
		}
			}
			
	}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		TweetStatsImpl.networkFailure = false;
	}
	
	
	/*@Before("execution(public void edu.sjsu.cmpe275.aop.TweetService.block(..))")
	public void blockBeforeAdvice(JoinPoint joinPoint) {
		System.out.printf("Before the execution of the method %s\n", joinPoint.getSignature().getName());
	}*/
	 /***
     * Following is block implementation of this aspect.
     * i am usuing around advice to to check if the the followee and follower are the same or follower is already blocked by the followee
     * before executing the block method
     * and after execution it add it to the block hashmap and 
     * return the mostBlockedfollwer user.
     * it is also checked if the the follower is follwing the followee, if yes, both of them are removed from each others foolow list.
     */
	@Around("execution(public void edu.sjsu.cmpe275.aop.TweetService.block(..))")
	public void blockAfterReturningAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
		if(!TweetStatsImpl.networkFailure)
		{
		System.out.printf("Before the execution of the method %s\n", joinPoint.getSignature().getName());
		String followee = joinPoint.getArgs()[0].toString();
		String follower = joinPoint.getArgs()[1].toString();
		
		System.out.println("Followeee: " + followee);
		System.out.println("Follower: " + follower);

		if (TweetStatsImpl.block.containsKey(follower)
				&& TweetStatsImpl.block.get(follower).contains(followee)) {
			/*System.out.printf("%s is already blocked by %s", follower, followee);*/
			System.out.println("User " + followee + " has already blocked user " + follower + " \n");
			return;
		}
		else if (followee.equals(follower)) {
			System.out.printf("%s cannot block himself/herself", followee);
		}
		else {
			
			joinPoint.proceed();		
		System.out.printf("After Returning the execution of the method %s\n", joinPoint.getSignature().getName());
		
	    		
		HashSet<String> blockSet;
		if(TweetStatsImpl.block.size() == 0) {
			TweetStatsImpl.mostBlockedFollower = follower;
			blockSet =new HashSet<String>();
			blockSet.add(followee);
			TweetStatsImpl.block.put(follower, blockSet);
		}
		

			if(TweetStatsImpl.block.containsKey(follower) /*&& !TweetStatsImpl.block.get(follower).contains(followee)*/) {
				blockSet = TweetStatsImpl.block.get(follower);
				blockSet.add(followee);
				TweetStatsImpl.block.put(follower, blockSet);
			}
			else {
				blockSet =new HashSet<String>();
				
				blockSet.add(followee);
				TweetStatsImpl.block.put(follower, blockSet);
				System.out.printf("5");
			}
			System.out.println(TweetStatsImpl.block.get(TweetStatsImpl.mostBlockedFollower));
			System.out.println(TweetStatsImpl.block.get(follower));
			if(TweetStatsImpl.block.get(TweetStatsImpl.mostBlockedFollower) != null) {
		    
			if(TweetStatsImpl.block.get(follower).size() > TweetStatsImpl.block.get(TweetStatsImpl.mostBlockedFollower).size()) {
				TweetStatsImpl.mostBlockedFollower = follower;
			}
			else if(TweetStatsImpl.block.get(follower).size() == TweetStatsImpl.block.get(TweetStatsImpl.mostBlockedFollower).size()) {	
				if(follower.compareTo(TweetStatsImpl.mostBlockedFollower) <= 0) {
					TweetStatsImpl.mostBlockedFollower = follower;
				}
			}
			}
			else {
				TweetStatsImpl.mostBlockedFollower = follower;
			}
			//System.out.println("before upating follow in block" + TweetStatsImpl.follow.get(followee).size() );
			System.out.printf("9");
			
			if (TweetStatsImpl.follow.containsKey(followee)) {		
				//System.out.println("before upating follow in block6666" + TweetStatsImpl.follow.size() );
				if (TweetStatsImpl.follow.get(followee).contains(follower)) {
				//	System.out.println("before upating follow in block777" + TweetStatsImpl.follow.size() );
					TweetStatsImpl.follow.get(followee).remove(follower);
					//TweetStatsImpl.follow.put(followee, TweetStatsImpl.follow.get(followee));
					//System.out.println("before upating follow in block888" + TweetStatsImpl.follow.get(followee).size() );
				}

			}
			if (TweetStatsImpl.follow.containsKey(follower) && TweetStatsImpl.follow.get(follower).contains(followee)) {
			//	System.out.println("before upating follow in block777" + TweetStatsImpl.follow.size() );
				TweetStatsImpl.follow.get(follower).remove(followee);
				//TweetStatsImpl.follow.put(followee, TweetStatsImpl.follow.get(followee));
				//System.out.println("before upating follow in block888" + TweetStatsImpl.follow.get(followee).size() );
			}
			System.out.printf("yahoo3");
			String localMostFollowedUser = null;
			int mostfollowedcount = 0;
			for(String c: TweetStatsImpl.follow.keySet()) {
				System.out.printf("yahoo5");
				int localMostFollowed = TweetStatsImpl.follow.get(c).size();
				if(localMostFollowed > mostfollowedcount) {
					localMostFollowedUser = c;
					mostfollowedcount = localMostFollowed;
				}
				
				else if(localMostFollowed == mostfollowedcount && localMostFollowedUser!=null && c.compareTo(localMostFollowedUser)<= 0 ) {
					localMostFollowedUser = c;
					mostfollowedcount = localMostFollowed;
				}
				System.out.printf("yahoo6");
			}
			System.out.printf("yahoo4");
			TweetStatsImpl.mostFollowedUser = localMostFollowedUser;
			
			System.out.println("111 " + mostfollowedcount);
			if(mostfollowedcount == 0)
			{
				TweetStatsImpl.mostFollowedUser = null;
			}
			/*if(TweetStatsImpl.follow.get(followee).size() > TweetStatsImpl.follow.get(TweetStatsImpl.mostFollowedUser).size()) {
				TweetStatsImpl.mostFollowedUser = followee;
			}
			else if(TweetStatsImpl.follow.get(followee).size() == TweetStatsImpl.follow.get(TweetStatsImpl.mostFollowedUser).size()) {	
				if(followee.compareTo(TweetStatsImpl.mostFollowedUser) <= 0) {
					TweetStatsImpl.mostFollowedUser = followee;
				}
			}*/
		}
		}
		System.out.println("Dumbass " + TweetStatsImpl.mostBlockedFollower);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		//System.out.println("after upating follow in block" + TweetStatsImpl.follow.size() );
		TweetStatsImpl.networkFailure = false;
	
	}
	
	@After("execution(public void edu.sjsu.cmpe275.aop.TweetService.tweet(..))")
	public void dummyAfterAdvice(JoinPoint joinPoint) {
		System.out.printf("After the execution of the metohd %s\n", joinPoint.getSignature().getName());
		//stats.resetStats();
		
	}
	
	
	
	
	
}
