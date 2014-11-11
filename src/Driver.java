
public class Driver {

	public static void main(String[] args) {
		Admin.getInstance().run();
		System.out.println("User Count" + Admin.getUserCount());
	}

}
