
public class UserCountVisitor implements Visitor {
	int count = 0;
	
	public void visit(User user) {
		count++;
	}

	public void visit(Group group) {
		//do nothing
	}
	
	public int getCount(){
		return count;
	}
	
}
