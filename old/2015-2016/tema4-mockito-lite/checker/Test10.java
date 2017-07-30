
public class Test10 implements Test {

	Mockable<Integer, String> m = new Incrementor();

	@Override
	public boolean execute() {
		Mockable<Integer, String> mock = Mockito.mock(m);
		Mockito.watch().when(mock.execute(1)).thenReturn("").andBeDoneWithIt();
		Mockito.watch().when(mock.execute(2)).thenReturn("").andBeDoneWithIt();
		
		try {
			Mockito.verify(mock).execute(2);
		} catch (MockitoException e) {
			return true;
		}

		return false;
	}
}
