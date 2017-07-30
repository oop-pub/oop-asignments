
public class Test8 implements Test {
	Mockable<Integer, String> m = new Incrementor();

	@Override
	public boolean execute() {
		Mockable<Integer, String> mock = Mockito.mock(m);
		Mockito.watch().when(mock.execute()).thenThrow(new RuntimeException("ex")).andBeDoneWithIt();

		try {
			mock.execute();
		} catch(RuntimeException e) {
			if (! "ex".equals(e.getMessage()))
				return false;
		}

		try {
			Mockito.verify(mock, Mockito.exactlyOnce()).execute();
			return true;
		} catch(MockitoException e) {
			e.printStackTrace();
		}

		return false;
	}

	
}
