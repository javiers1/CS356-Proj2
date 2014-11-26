import java.util.*;

/**
 * User class. This class implements the Observer, Visitor, and Composite pattern
 * Users observe other users
 * Visitors are accepted to access User field data
 * Users are the Leaf nodes of the Composite pattern named NodeComponent
 * @author Javi
 *
 */
public class User extends Observable implements Observer, NodeComponent, Visitable{
	private String userName;
	private int tweetCount;
	private long creationTime;
	private long lastUpdatedTime;
	public long getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	private List<String> followers;
	private List<String> following;
	private List<String> myTweets;
	private List<String> newsFeed;
	private UserUI myUI;
	
	public User(String userName){
		this.userName = userName;
		this.tweetCount = 0;
		this.followers = new ArrayList<String>();
		this.following = new ArrayList<String>();
		this.newsFeed = new ArrayList<String>();
		this.myTweets = new ArrayList<String>();
		this.myUI = null;
		creationTime = System.currentTimeMillis();
		lastUpdatedTime = creationTime;
	}
	
	public long getCreationTime() {
		return creationTime;
	}

	public void setUI(UserUI userUI){
		this.myUI = userUI;
	}
	
	public void follow(String user){
		User newUser = (User) Admin.getInstance().getUser(user);
		if(newUser == null){
			//user not found, error printed
		}
		else{
			following.add(newUser.getName());
			newUser.addFollower(this.userName);
			newUser.addObserver(this);
		}
	}
	
	/**
	 * Add tweet to User tweets and NewsFeed. Also notify observers.
	 * First Notification - Observers add the tweet to their newsfeed
	 * Second Notification - Obeserver GUI (UserUI) is notified to refresh
	 * display of newsfeed
	 * @param tweetMsg
	 */
	public void tweet(String tweetMsg){
		myTweets.add(tweetMsg);
		tweetCount++;
		newsFeed.add(userName + ": " + (String) tweetMsg);
		lastUpdatedTime = System.currentTimeMillis();
		setChanged();
		notifyObservers(tweetMsg);
		clearChanged();
		setChanged();
		notifyObservers(getNewsFeed());
		clearChanged();
	}
	
	public void addFollower(String uName){
		followers.add(uName);		
	}
	
	public List<String> getFollowing(){
		return following;
	}

	public String getName(){
		return userName;
	}
	
	public List<String>	getNewsFeed(){
		return newsFeed;
	}
	
	public List<String> getMyTweets() {
		return myTweets;
	}
	
	public int getTweetCount(){
		return tweetCount;
	}
	
	/**
	 * If a User sends a Tweet, all the Users followers will update their newsfeed
	 * Implementation of Observer pattern
	 */
	public void update(Observable o,Object arg){
		if(arg instanceof String){
			newsFeed.add(((User) o).getName() + ": " + (String) arg);
		}
	}
	
	/**
	 * Implementation of Composite Pattern. Returns the User.
	 */
	public User getUser(String user) {
		if(userName.equals(user)){
			return this;
		}else{
			return null;
		}
	}
	
	public String toString(){
		return userName;
	}
	
	public List<String> getFollowingWithTimes(){
		List<String> result = new ArrayList<String>();
		User temp;
		for(String s: following){
			temp = (User) Admin.getInstance().getUser(s);
			result.add(temp.getLastUpdatedTime() + " - " + s);
		}
		return result;
	}
	
	/**
	 * Implementation of Visitor pattern
	 */
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Used so that the UserUI associated with this User will observe the 
	 * activity of Users that are being followed. Used to refresh newsFeed
	 * any time new tweets are posted
	 */
	public void addNewObservers() {
		for(String user: following){
			User temp = (User) Admin.getInstance().getUser(user);
			temp.addObserver(myUI);
		}
	}
}
