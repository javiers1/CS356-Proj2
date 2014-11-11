
public class GroupCountVisitor implements Visitor {
	
	int count = 0;
	
	public void visit(User user) {
		//do nothing
	}

	public void visit(Group group) {
		count++;
	}
	
	public int getCount(){
		return count;
	}

}
