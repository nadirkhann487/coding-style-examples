package demo;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import twitter4j.*;

import twitter4j.*;

import twitter4j.auth.AccessToken;

import twitter4j.auth.AccessToken;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class twitterClient {

	private static final String BASE_URI ="https://campus.cs.le.ac.uk/tyche/CO7214Rest3/rest/soa/";
	
	public static void main(String[] args) throws IOException, TwitterException {

		
        //Type Your Twitter Consumer Key

       String consumerKey = "PKWnphcTVrAA4nKkAK8xYNFH0";

        //Type Your Twitter Consumer Secret

       String consumerSecret = "MA6wVM71rJ8ELsL31vxNIm3UIeFScsI1mOp8TwoNaKUS1a4XR9";

        //Type Your Twitter Access Token

       String accessToken = "970985012484067328-iSvxmZfHRPSPLqtyiGRW1eLkq0gOfYG";

        //Type Your Twitter Access Token Secret

       String accessTokenSecret = "1ZIC7Wc5MtL0ezwJJhaMekyQ8k8RrShM5lSdj6TVuky9I";

       String passCode = "nk2571";
        //Create thread-safe factory

       TwitterFactory twitterFactory = new TwitterFactory();

       //Create new Twitter instance

       Twitter twitter = twitterFactory.getInstance();

      //setup OAuth Consumer Credentials

       twitter.setOAuthConsumer(consumerKey, consumerSecret);

        //setup OAuth Access Token

       twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

        
        String UserName=(getUserName(passCode));// get username from the function getUserName which is taking username from the provided  service
        System.out.println("getUserName(): "+UserName);  // displaying username
        
        User user = twitter.showUser(UserName);    // get Specific user as User object using username 
        System.out.println("Screen Name: "+user.getName());// display screen name
        
        int followersCount = user.getFriendsCount(); // get followedcount from the user object using getFriendsCount
        System.out.println("submitNumberFollowed() "+followersCount);//display followed count        
        
       List<Status> status1 =twitter.getUserTimeline(UserName);// list containing all user's timeline statuses

        int count=0;
        int retweets=0;

        for (Status tweets :status1){// loop to process each tweet one by one
        	if(tweets.isRetweet())// check if the tweet is a retweet
        	{
        	
        		Date date = tweets.getCreatedAt();// getting tweet's creation date
        		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();// converting date to local dta so that its month and year can be processed
        		int month = localDate.getMonthValue();// getting month
        		int year = localDate.getYear();// getting year

        		if(month==1 && year==2018)// check if the tweet is tweeted  within january 2018
        		{
        			retweets++;// retweets count incemented
        		}
        	}

        }
        
        System.out.println("submitNumberOfRetweets() "+retweets+"");// displaying retweets
        
        int maxTweets=0;
        String mostActiveUser = "";

        IDs followerIDs = twitter.getFriendsIDs(UserName, -1);// get twitter account all friends ids
        long[] ids = followerIDs.getIDs();// converting ids into a long array
        for (long id : ids) {// processing ids array elements one by one
            User user1 = twitter.showUser(id);// getting friend as User object using his/her id
            
            count=count+user1.getStatusesCount();// counting statuses count
            
            if(maxTweets<user1.getStatusesCount())// comparing users statuses count
            {
         	   maxTweets=user1.getStatusesCount();// saving umaximum tweets
         	   mostActiveUser = user1.getScreenName();// getting user with the maximum number of tweets
            }
         	       
        }
        System.out.println("submitNumberOfTweetsReceived() "+count+"");// displaying tweets count
        System.out.println("submitMostActiveFollowed "+mostActiveUser+"");// displaying mostactive user


        System.out.println("Testing ................");
        
        System.out.println(getFollowers(passCode,UserName,followersCount));
        System.out.println(getTweets(passCode,UserName,Integer.toString(count)));
        System.out.println(getReTweets(passCode,UserName,retweets));
        System.out.println(getMostActive(passCode,UserName,mostActiveUser));

 }
	
	//this function is passing the passcode, username and friends to the service which in return gives either pass if the result matches otherwise false
	public static String getFollowers(String passCode,String userName,int friends)
	{
		Client client = ClientBuilder.newClient();

        WebTarget target =client.target(BASE_URI+MessageFormat.format("submitNumberFollowed/{0}/{1}/{2}", new Object[] {passCode,userName,friends}));

        return target.request(MediaType.APPLICATION_JSON).

        		get(String.class);

	}
	//this function is passing the passcode, username and tweets to the service which in return gives either pass if the result matches otherwise false
	public static String getTweets(String passCode,String userName,String tweets)
	{
		Client client = ClientBuilder.newClient();

        WebTarget target =client.target(BASE_URI+MessageFormat.format("submitNumberOfTweetsReceived/{0}/{1}/{2}", new Object[] {passCode,userName,tweets}));

        return target.request(MediaType.APPLICATION_JSON).

        		get(String.class);

	}
	//this function is passing the passcode, username and retweets to the service which in return gives either pass if the result matches otherwise false
	public static String getReTweets(String passCode,String userName,int retweets)
	{
		Client client = ClientBuilder.newClient();

        WebTarget target =client.target(BASE_URI+MessageFormat.format("submitNumberOfRetweets/{0}/{1}/{2}", new Object[] {passCode,userName,retweets}));

        return target.request(MediaType.APPLICATION_JSON).

        		get(String.class);

	}
	//this function is passing the passcode and in return service gives username which is used to perform different operations
	public static String getUserName(String passCode)
	{
		Client client = ClientBuilder.newClient();

        WebTarget target =client.target(BASE_URI+MessageFormat.format("getUserName/{0}", new Object[] {passCode}));

        return target.request(MediaType.APPLICATION_JSON).

        		get(String.class);

	}
	//this function is passing the passcode, username and activeuser to the service which in return gives either pass if the result matches otherwise false
	public static String getMostActive(String passCode,String userName,String activeUser)
	{
		Client client = ClientBuilder.newClient();

        WebTarget target =client.target(BASE_URI+MessageFormat.format("submitMostActiveFollowed/{0}/{1}/{2}", new Object[] {passCode,userName,activeUser}));

        return target.request(MediaType.APPLICATION_JSON).

        		get(String.class);

	}


public static String getHello() {     

 Client client = ClientBuilder.newClient();

  WebTarget target = client.target(BASE_URI+MessageFormat.format("hello",new Object[] {}));

  return target.request(MediaType.TEXT_PLAIN).get(String.class);

      }   
}
