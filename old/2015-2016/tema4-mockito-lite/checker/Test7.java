
public class Test7 implements Test {
	Mockable<Integer, String> m = new Incrementor();

	@Override
	public boolean execute() {
		Mockable<Integer, String> mock = Mockito.mock(m);
		Mockito.watch().when(mock.execute(1)).thenReturn("").andBeDoneWithIt();
		Mockito.watch().when(mock.execute(2)).thenReturn("").andBeDoneWithIt();

		mock.execute(2);

		try {
			Mockito.verify(mock, Mockito.atLeastOnce()).execute(2);
			mock.execute(1);
			Mockito.verify(mock, Mockito.atLeastOnce()).execute(1);
			return true;
		} catch (MockitoException e) {
			return false;
		}
	}
}
