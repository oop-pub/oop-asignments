
public class Test6 implements Test{
	
	Mockable<Integer, String> m = new Incrementor();
	String noParam = "NO_PARAM";
	String param = "PARAM";
	
	@Override
	public boolean execute() {
		Mockable<Integer, String> mock = Mockito.mock(m);
		Mockito.watch().when(mock.execute(2)).thenThrow(new RuntimeException(noParam)).andBeDoneWithIt();
		Mockito.watch().when(mock.execute()).thenThrow(new RuntimeException(param)).andBeDoneWithIt();

		try {
			mock.execute(2);
		} catch (RuntimeException e) {
			if (!noParam.equals(e.getMessage()))
				return false;
		}
		
		try {
			mock.execute();
		} catch (RuntimeException e) {
			if (!param.equals(e.getMessage()))
				return false;
		}
		
		return true;
	}
}
