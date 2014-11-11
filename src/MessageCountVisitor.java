
public class MessageCountVisitor implements Visitor {
	
	int messageCount = 0;
	
	@Override
	public void visit(User user) {
		messageCount += user.getTweetCount();
	}

	@Override
	public void visit(Group group) {
		//do nothing
	}
	
	public int getCount(){
		return messageCount;
	}

}
