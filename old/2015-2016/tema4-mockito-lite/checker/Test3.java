
public class Test3 implements Test {

	Mockable<Integer, String> m = new Mockable<Integer, String>() {
		public Unit execute() {
			throw new RuntimeException("should not be run");
		};
		
		public String execute(Integer a) {
			throw new RuntimeException("should not be run");
		};
	};
	
	@Override
	public boolean execute() {
		Mockable<Integer, String> mock = Mockito.mock(m);
		Mockito.watch().when(mock.execute(2)).thenReturn("0").andBeDoneWithIt();
		
		mock.execute(2);
		
		return true;
	}
}
