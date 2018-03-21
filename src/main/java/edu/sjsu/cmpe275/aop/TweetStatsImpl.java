package edu.sjsu.cmpe275.aop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
/**
 * 
 * @author thirt Hanisha
 * following id the implementation of tweetStats. 
 * which has data structures declared
 * and resetStatsAndSystem method defined.
 */
public class TweetStatsImpl implements TweetStats {
    /***
     * Following is a dummy implementation.
     * You are expected to provide an actual implementation based on the requirements.
     */
	public static HashMap<String, Integer> tweet = new HashMap<String, Integer>();
	public static HashMap<String,HashSet<String>> follow=new HashMap<String,HashSet<String>>();
	public static HashMap<String,HashSet<String>> block=new HashMap<String,HashSet<String>>();
	
	public static int longestTweetAttempted = 0;
	public static int longestTweetExecuted = 0;
	public static String mostFollowedUser= null;
	public static String mostProductiveUser = null;
	public static String mostBlockedFollower= null;
	public static boolean networkFailure = false;
    
	/**
	 * Reset all the four measurements. For purpose of this lab, it also resets
	 * the following and blocking records as if the system is starting fresh for 
	 * any purpose related to the metrics below.
	 */
	
	@Override
	public void resetStatsAndSystem() {
		// TODO Auto-generated method stub
		 longestTweetAttempted =0;
	     mostFollowedUser=null;
	    mostProductiveUser = null;
	     mostBlockedFollower= null;
	     longestTweetExecuted = 0;
	     
	     tweet.clear();
	     block.clear();
	     follow.clear();
		
	}
	
	/**
	 * 
	 * @return the length of longest message a user attempted to tweet since the
	 *         beginning or last reset. If no messages were successfully
	 *         tweeted, return 0.
	 */
    
	@Override
	public int getLengthOfLongestTweetAttempted() {
		// TODO Auto-generated method stub
		return longestTweetAttempted;
	}
	/**	 
	 * @return the user who is being followed by the biggest number of
	 *         different users since the beginning or last reset. If there is a
	 *         tie, return the 1st of such users based on alphabetical order.
	 *         If any follower has been blocked by the followee, then this follower
	 *         does NOT count for this stat. If no users are followed by anybody, 
	 *         return null.
	 */
	@Override
	public String getMostFollowedUser() {
		// TODO Auto-generated method stub
		return mostFollowedUser;
	}
	
	/**
	 * @return The most productive user is determined by the total length of all the
	 * messages successfully tweeted since the beginning or last reset. If there
	 * is a tie, return the 1st of such users based on alphabetical order. If no
	 * users successfully tweeted, return null.
	 * 
	 */
	
	@Override
	public String getMostProductiveUser() {
		// TODO Auto-generated method stub
		return mostProductiveUser;
	}
	
	/**
	 * 
	 * @return the user who has been blocked by the biggest number of
	 *         different users since the beginning or last reset. If there is a
	 *         tie, return the 1st of such users based on alphabetical order.
	 *         If no follower has been successfully blocked by anyone, return null.
	 */
	
	@Override
	public String getMostBlockedFollower() {
		// TODO Auto-generated method stub
		return mostBlockedFollower;
	}
}



