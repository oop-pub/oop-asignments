
public class Test9 implements Test {

	Mockable<Integer, String> m = new Incrementor();
	int executions = 2;

	@Override
	public boolean execute() {
		Mockable<Integer, String> mock = Mockito.mock(m);
		Mockito.watch().when(mock.execute(1)).thenReturn("").andBeDoneWithIt();
		Mockito.watch().when(mock.execute(2)).thenReturn("").thenReturn("").andBeDoneWithIt();

		for (int i = 0; i < executions; i++) {
			mock.execute(2);
		}

		try {
			Mockito.verify(mock, Mockito.times(executions)).execute(2);
		} catch (MockitoException e) {
			return false;
		}

		try {
			Mockito.verify(mock, Mockito.times(executions)).execute(1);
		} catch (MockitoException e) {
			return true;
		}

		return false;
	}
}
