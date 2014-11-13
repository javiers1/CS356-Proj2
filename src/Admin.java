import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/*
 * Singleton Administrator Control Class
 * 
 * to do, add a isGroup and isUser method for GUI
 */

public class Admin {
	
	private static Admin instance = null;
	private static TreeModel tree;
	private static List<NodeComponent> nodes;
	private static Group root = null;
	 
	
	private Admin(){
		//nodes = new HashMap<String,NodeComponent>();
		root = new Group("root");
		nodes = new ArrayList<NodeComponent>();
	}

	protected void run(){
		//add to group, also add to nodes
		addGroup("root", "cs356");
		addUser("cs356","steve");
		addGroup("root","cs445");
		addGroup("cs445","students");
		addUser("cs445","javi");
		addGroup("students","Astudents");
		addUser("Astudents","john");
		addUser("Astudents","cecy");
		
		User temp = root.getUser("javi");
		User temp3 = root.getUser("cecy");
		Group temp2 = root.getGroup("Astudents");
		System.out.println("Found: " + temp);		
		System.out.println("found the group: " + temp2);
		
		System.out.println(root);

		//called from UI User, wrap this to take a name and match it
		follow("javi","john");
		follow("javi","steve");
		follow("javi","cecy");
		follow("cecy","javi");
				
		//called from UI User interface
		tweet("john","TWITTER SUCKS");
		tweet("john","A LOT");
		tweet("javi","I like Turtles");
		tweet("steve","steve is a beast");
		tweet("cecy","I love javi");
		tweet("javi","I LOVE CESS");
		
		
		System.out.println("javis newsfeed: " + temp.getNewsFeed());
		System.out.println("cecys newsfeed: " + temp3.getNewsFeed());
		System.out.println("Group Count: " + getGroupCount());
		System.out.println("User Count: " + getUserCount());
		System.out.println("Tweet Count: " + getMessageCount());
		System.out.println("Good Word Count: " + getGoodWordPercentage());
		
		AdminGUI.main(null);
	}
	
	protected static Admin getInstance(){
		if(instance == null){
			instance = new Admin();
		}
			return instance;
	}
	
	protected DefaultTreeModel getTreeModel(){
		DefaultTreeModel aTree = getTreeModel(root);
		return aTree;
	}
	
	protected DefaultTreeModel getTreeModel(NodeComponent startNode){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		recursiveBuildTree(root, ((Group) startNode).getChildren());
		DefaultTreeModel tree = new DefaultTreeModel(root);
		return tree;
	}
	
	
	//need to build a DefaultTreeModel
	protected void recursiveBuildTree(DefaultMutableTreeNode parent, List<NodeComponent> children){
		for(NodeComponent aNode: children){
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(((NodeComponent) aNode).getName());
			parent.add(node);
			if(aNode instanceof Group){
				recursiveBuildTree(node, ((Group) aNode).getChildren());
			}
		}	
	}
	
	
	protected int getGroupCount(){
		GroupCountVisitor countVisitor = new GroupCountVisitor();
		for(Visitable node: nodes){
			node.accept(countVisitor);
		}
		return countVisitor.getCount();
		
	}
	
	protected int getUserCount(){
		UserCountVisitor countVisitor = new UserCountVisitor();
		for(Visitable node: nodes){
			node.accept(countVisitor);
		}
		return countVisitor.getCount();
		
	}
	
	protected int getMessageCount(){
		MessageCountVisitor countVisitor = new MessageCountVisitor();
		for(Visitable node: nodes){
			node.accept(countVisitor);
		}
		return countVisitor.getCount();
		
	}
	
	protected double getGoodWordPercentage(){
		GoodWordCountVisitor countVisitor = new GoodWordCountVisitor(Arrays.asList("like", "love", "good","nice","awesome","beast"));
		for(Visitable node: nodes){
			node.accept(countVisitor);
		}
		
		System.out.println("PERCENTGE: " + countVisitor.getGoodWordPercentage());
		return countVisitor.getGoodWordPercentage();
	}
	
	protected boolean isGroup(String node){
		boolean result = false;
		if(root.getGroup(node) instanceof Group){
			result = true;
		}
		return result;
	}
	
	protected boolean isUser(String node){
		boolean result = false;
		if(root.getUser(node) instanceof User){
			result = true;
		}
		return result;
	}
	
	//USED BY ADMIN GUI
	protected void addUser(String group,String name){
		Group tmp = root.getGroup(group);
		if(tmp != null){
			User aUser = new User(name);
			tmp.addComponent(aUser);
			nodes.add(aUser);
		}
	}
	
	//USED BY ADMIN GUI
	protected void addGroup(String group,String groupName){
		Group tmp = root.getGroup(group);
		if(tmp != null){
			Group aGroup = new Group(groupName);
			tmp.addComponent(aGroup);
			nodes.add(aGroup);
		}
	}	
	
	//used by user class to check Admin list of users
	protected NodeComponent getUser(String name){
		NodeComponent user = root.getUser(name);
		if(user != null){
			return user;
		}
		return null;
	}
	
	//THIS IS TEMPORARY, FOLLOWING IS DONE BY USER GUI
	public void follow(String n1, String n2){
		User user = root.getUser(n1);
		user.follow(n2);
	}
	
	//THIS IS TEMPORARY, TWEETING IS DONE BY USER GUI
	public void tweet(String user, String message){
		User temp = root.getUser(user);
		temp.tweet(message);
	}
	
	public void print(){
	}

	
}
