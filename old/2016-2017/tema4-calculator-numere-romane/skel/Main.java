import checker.Evaluator;

public final class Main {
  private static final int PARAMETERS_LENGTH = 4;

  private static final int SUB_FILE_POS = 0;
  private static final int IN_FILE_POS = 1;
  private static final int OUT_FILE_POS = 2;
  private static final int REF_FILE_POS = 3;

  private Main() {

  }

  public static void main(final String[] args) {
    try {
      if (args.length < PARAMETERS_LENGTH) {
        throw new Exception("Usage: tema4 subscribers input output reference");
      }

      Evaluator evaluator = new Evaluator(args[SUB_FILE_POS],
          args[IN_FILE_POS],
          args[OUT_FILE_POS],
          args[REF_FILE_POS]);

      /*
       * TODO Call the evaluator.run() method
       * and pass the Server singleton instance
       */
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
