
public class Test2 implements Test {

	Mockable<Integer, String> m = new Incrementor();
	
	@Override
	public boolean execute() {
		Mockable<Integer, String> mock = Mockito.mock(m);
		Mockito.watch().when(mock.execute()).thenThrow(new NullPointerException("test")).andBeDoneWithIt();
		
		try {
			mock.execute();
		} catch(NullPointerException e) {
			return true;
		} catch(Exception e) {
			return false;
		}

		return false;
	}
}
