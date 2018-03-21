package edu.sjsu.cmpe275.aop;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TweetTest {

    /***
     * These are dummy test cases. You may add test cases based on your own need.
     */
	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
    TweetService tweeter = (TweetService) ctx.getBean("tweetService");
    TweetStats stats = (TweetStats) ctx.getBean("tweetStats");

	//@Autowired ApplicationContext ctx;
	@BeforeClass
	public static void beforeClass() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
	    TweetService tweeter = (TweetService) ctx.getBean("tweetService");
	    TweetStats stats = (TweetStats) ctx.getBean("tweetStats");
	}

	
	@After
    public void afterTest() {
    	stats.resetStatsAndSystem();
    }
	
	/**
     * test for length of longest tweet
     * test for most productive user
     */
	@Test
    public void testOne() {
    	try {
            tweeter.tweet("alex", "first tweet");
            tweeter.tweet("bob", "this is the longest tweet with length 40");
            tweeter.tweet("alex", "with this alex is most productive user");
            assertEquals(40, stats.getLengthOfLongestTweetAttempted());
            assertEquals("alex", stats.getMostProductiveUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test most followed user
     * test length of tweet 0 for no tweets
     */
    @Test 
    public void testTwo() { 
    	try {
            tweeter.follow("alex", "bob");
            tweeter.follow("suhas", "amey");
            tweeter.follow("carl", "bob");
            tweeter.block("bob", "alex");
            assertEquals(0, stats.getLengthOfLongestTweetAttempted());
            assertEquals("amey", stats.getMostFollowedUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test most blocked user
     * null for most followed user
     */
    @Test 
    public void testThree() { 
    	try {
            tweeter.block("alex", "bob");
            tweeter.block("suhas", "amey");
            tweeter.block("carl", "amey");
            tweeter.block("bob", "alex");
            tweeter.block("suhas", "alex");
            assertEquals(0, stats.getLengthOfLongestTweetAttempted());
            assertEquals("alex", stats.getMostBlockedFollower());
            assertEquals(null, stats.getMostFollowedUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test null for most followed user
     * test null for most blocked user
     * 0 for longest tweet attempted
     */
    @Test 
    public void testFour() { 
    	try {
            assertEquals(null, stats.getMostBlockedFollower());
            assertEquals(null, stats.getMostFollowedUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test for longest tweet attempted but failed
     * test for tweet more than 140 characters
     * only successful tweets are counted towards deciding the most productive user
     */
    @Test 
    public void testFive() { 
    	try {
    		tweeter.tweet("alex", "this is a tweet more than 140 characters this is a tweet more than 140 characters this is a tweet more than 140 characters this is a tweet more");
    		tweeter.tweet("alex", "second tweet");
    		tweeter.tweet("bob", "first tweet bigger");
            assertEquals(143, stats.getLengthOfLongestTweetAttempted());
            assertEquals("bob", stats.getMostProductiveUser());
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test removing both users from each others list
     * Most Blocked User: Suhas
     * Most Followed User: null
     * 
     */
    @Test 
    public void testSix() { 
    	try {
            tweeter.follow("suhas", "amey");
            tweeter.follow("amey", "suhas");
            tweeter.block("amey", "suhas");
            
            assertEquals("suhas", stats.getMostBlockedFollower());
            assertEquals(null, stats.getMostFollowedUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test checking next highest followed user
     * Most Followed User: Suhas
     * 
     */
    @Test 
    public void testSeven() { 
    	try {
            tweeter.follow("a", "amey");
            tweeter.follow("b", "amey");
           
            tweeter.follow("x", "suhas");
            tweeter.follow("y", "suhas");
            
            tweeter.follow("f", "barry");
            
            tweeter.follow("h", "cory");
            
            tweeter.block("amey", "a");
            
            assertEquals("suhas", stats.getMostFollowedUser());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
	public void testEight() {
		try {
			tweeter.tweet("foo", "barbar");
			tweeter.tweet("alice", "uthappa and idli dosa dena");

			assertEquals("alice", stats.getMostProductiveUser());
			assertEquals(26, stats.getLengthOfLongestTweetAttempted());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testNine() {
		try {
			tweeter.follow("alice", "bob");
			tweeter.follow("bob", "alice");

			assertEquals("alice", stats.getMostFollowedUser());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


/*package edu.sjsu.cmpe275.aop;

import org.junit.Test;

public class TweetTest {

    *//***
     * These are dummy test cases. You may add test cases based on your own need.
     *//*

    @Test
    public void testOne() { }

    @Test
    public void testCaseN() { }
}*/

