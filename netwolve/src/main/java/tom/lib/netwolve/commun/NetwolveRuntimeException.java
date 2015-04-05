package tom.lib.netwolve.commun;

public class NetwolveRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -5074055638643611820L;


	public NetwolveRuntimeException() {
	}

	public NetwolveRuntimeException(String paramString) {
		super(paramString);
	}

	public NetwolveRuntimeException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}

	public NetwolveRuntimeException(Throwable paramThrowable) {
		super(paramThrowable);
	}

	protected NetwolveRuntimeException(String paramString, Throwable paramThrowable,
			boolean paramBoolean1, boolean paramBoolean2) {
		super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
	}
}
