
public class Incrementor implements Mockable<Integer, String> {

	@Override
	public Unit execute() {
		
		return null;
	}

	@Override
	public String execute(Integer a) {
		return Integer.toString(a + 1);
	}

}
