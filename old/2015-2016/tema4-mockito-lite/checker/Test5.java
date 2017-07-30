
public class Test5 implements Test {

	Mockable<Integer, String> m = new Incrementor();
	
	@Override
	public boolean execute() {
		Mockable<Integer, String> mock = Mockito.mock(m);
		Mockito.watch().when(mock.execute(2)).thenReturn("0").thenReturn("1").thenReturn("2").andBeDoneWithIt();
		
		return mock.execute(2).equals("0") && mock.execute(2).equals("1") && mock.execute(2).equals("2");
	}
}
