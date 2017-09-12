package util;

public enum Errors {
	INCORRECT_CREDENTIALS(0, "Wrong Email address and pasword combination."),
	INCORRECT_PASSWORD(1, "Wrong current password."),
	PROFILE_UPDATED(2, "Profile updated successfully."),
	PASSWORD_UPDATED(3, "Password changed successfully.");

	private final int code;
	private final String description;

	private Errors(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code + ": " + description;
	}
}
