import java.util.*;
/*
 * Composite Group of Users and/or Groups
 */

public class Group implements Visitable, NodeComponent {
	private String groupID;
	private List<NodeComponent> children;
	
	public Group(String name){
		children = new ArrayList<NodeComponent>();
		groupID = name;
	}
	
	public void addComponent(NodeComponent comp){
		children.add(comp);
	}
	
	public String getName(){
		return groupID;
	}

	public List<NodeComponent> getChildren(){
		return children;
	}
	
	public void print(){
		System.out.println("Group Name is :" + groupID);
	}
	
	public Group getGroup(String group){
		if(groupID.equals(group)){
			return this;
		}else{
			for(NodeComponent tmp: children){
				if(tmp instanceof Group){
					if(((Group) tmp).getGroup(group) != null){
						return ((Group) tmp).getGroup(group);
					}
				}
			}
		}
		return null;
	}
	
	public User getUser(String user) {
		User result = null;
		for(NodeComponent tmp: children){
			if(tmp instanceof User){
				if(tmp.getUser(user) != null){
					result = tmp.getUser(user);
				}
			}else if(tmp instanceof Group){
				if(tmp.getUser(user) != null){
					result = tmp.getUser(user);
				}
			}
		}
		return result;
	}
	
	public String toString(){
		String result;
		result = groupID + ": " + children.toString();
		return result;
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
