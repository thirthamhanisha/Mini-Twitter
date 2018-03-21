package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.TweetStatsImpl;

import org.aspectj.lang.annotation.Around;
/**
 * 
 * @author thirt Hanisha
 * following is the implementation for the retry aspect. used around and joinPoint.proceed to retry thrice and 
 * 
 *return the exception at the end of the third try.
 */
@Aspect
@Order(1)
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */
	@Autowired TweetStatsImpl stats;
	@Around("execution(public void edu.sjsu.cmpe275.aop.TweetService.*(..))")
	public void dummyAdvice(ProceedingJoinPoint joinPoint) {
		System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		Object result = null;
		try {
			result = joinPoint.proceed();
			System.out.printf("Finished the executuion of the metohd %s with result %s\n", joinPoint.getSignature().getName(), result);
		} catch (Throwable e) {
			e.printStackTrace();
			
			System.out.printf("Aborted the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		}
	}
	
	
	@Around("execution(* edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))")
	public void tweetRetryAround(ProceedingJoinPoint joinPoint) throws Throwable,IOException {

		
		System.out.println("inside RETRY ASPECT for Tweet!");
		
		try {
			joinPoint.proceed();
		} catch (IOException t1) {
			System.out.println("There has been a network failure while tweeting, retrying: 1st attempt");
			try {
				joinPoint.proceed();
			} catch (IOException t2) {
				System.out.println("There has been a network failure while tweeting, retrying: 2nd attempt");
				try {
					joinPoint.proceed();
				} catch (IOException t3) {
					System.out.println("here has been a network failure while tweeting, retrying: 3rd attempt");
					try {
						joinPoint.proceed();
					} catch (IOException t4) {
						System.out.println("Tweet could not be posted, after three attempts");
						TweetStatsImpl.networkFailure = true;
					}				
					
				}
			}
		} 
	}
	
	@Around("execution(* edu.sjsu.cmpe275.aop.TweetServiceImpl.follow(..))")
	public void followRetryAround(ProceedingJoinPoint joinPoint) throws Throwable {

		
		System.out.println("Inside retry aspect for Follow");
		
		try {
			joinPoint.proceed();
		} catch (IOException t1) {
			System.out.println("There has been a network failure while following: 1st attempt");
			try {
				joinPoint.proceed();
			} catch (IOException t2) {
				System.out.println("There has been a network failure while following: 2nd attempt");
				try {
					joinPoint.proceed();
				} catch (IOException t3) {
					System.out.println("There has been a network failure while following: 3rd attempt");
					try {
						joinPoint.proceed();
					} catch (IOException t4) {
						
						System.out.println("Following method failed");
						TweetStatsImpl.networkFailure = true;
					}
					
				}
			}
		}
	}
	
	@Around("execution(* edu.sjsu.cmpe275.aop.TweetServiceImpl.block(..))")
	public void blockRetryAround(ProceedingJoinPoint joinPoint) throws Throwable {

				System.out.println("Inside retry aspect for Block Method");
		
		try {
			joinPoint.proceed();
		} catch (IOException t1) {
			System.out.println("There has been a network failure while blocking: 1st attempt");
			try {
				joinPoint.proceed();
			} catch (IOException t2) {
				System.out.println("There has been a network failure while blocking: 2nd attempt");
				try {
					joinPoint.proceed();
				} catch (IOException t3) {
					System.out.println("There has been a network failure while blocking: 3rd attempt");
					try {
						joinPoint.proceed();
					} catch (IOException t4) {
						System.out.println("Blocking could be done because of network failure");
						TweetStatsImpl.networkFailure = true;
					}
					
				}
			}
		}
		
	}

}
