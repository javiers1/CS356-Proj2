import java.text.DecimalFormat;
import java.util.*;

/**
 * Implements the Visitor pattern. Visits Users and calculates percentages
 * GoodWords can be passed as a list at instantiation
 * @author Javi
 *
 */
public class GoodWordCountVisitor implements Visitor {
	List<String> goodWords;
	int goodWordCount;
	int totalWordCount;
	
	public GoodWordCountVisitor(List<String> gw){
		this.goodWords = gw;
		goodWordCount = 0;
		totalWordCount = 0;
	}
	
	/**
	 * For every User, retrieve their Tweets and process each tweet
	 */
	public void visit(User user) {
		List<String> tweetsTemp = user.getMyTweets();
		for(String s: tweetsTemp){
			processTweet(s);
		}
	}

	public void visit(Group group) {
		//do nothing
	}
	
	/**
	 * Match words in the Tweet with designated good words
	 * @param String - tweet 
	 */
	public void processTweet(String tweet){
		String [] words = tweet.split(" ");
		for(String s: words){
			totalWordCount++;
			s = s.toLowerCase();
			for(String gw: goodWords){
				if(s.equals(gw)){
					goodWordCount++;
				}
			}
		}
	}
	
	public int getGoodWordCount(){
		return goodWordCount;
	}
	
	/**
	 * Calculates a percentage as a double of the Good Words found in all Visited
	 * User's Tweets
	 * @return
	 */
	public double getGoodWordPercentage(){
		double result = (double) goodWordCount/totalWordCount;
		result *= 100.0;
		DecimalFormat df = new DecimalFormat("#.000"); 
		String temp = df.format(result);
		return Double.valueOf(temp);
	}
	
}
