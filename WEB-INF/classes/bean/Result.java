package bean;

public class Result {

	private boolean success;
	private String msg;
	private Object obj;


	public Result() {

	}

	public Result(boolean success, String msg) {
		this.setSuccess(success);
		this.setMsg(msg);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Result [success=" + success + ", msg=" + msg + "]";
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
