package runner;

public class Result {
	
	private String method;
	private boolean result;

	public Result() {
		
	}

	public Result(String method, boolean result) {
		this.method = method;
		this.result = result;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
