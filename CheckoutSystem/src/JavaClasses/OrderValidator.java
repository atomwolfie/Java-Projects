/**
 * Validate order information
 */
public class OrderValidator {

	/**
	 * Validate the given date time
	 */
	public boolean dateTimeIsValid(String dateTime) {
		if (dateTime.length() != 19) { return false; }
		for (int i = 0; i < 4; i++){
			if (!Character.isDigit(dateTime.charAt(i))) { return false; }
		}
		for (int i = 4; i < 10; i += 3) {
			if (dateTime.charAt(i) != '-') { return false; }
			if (!Character.isDigit(dateTime.charAt(i + 1)) || !Character.isDigit(dateTime.charAt(i + 2))) { return false; }
		}
		if (dateTime.charAt(10) != ' ') { return false; }
		for (int i = 11; i < 17; i += 3) {
			if (!Character.isDigit(dateTime.charAt(i)) || !Character.isDigit(dateTime.charAt(i + 1))) { return false; }
			if (dateTime.charAt(i + 2) != ':') { return false; }
		}
		if (!Character.isDigit(dateTime.charAt(17)) || !Character.isDigit(dateTime.charAt(18))) { return false; }

		return true;
	}

	/**
	 * Validate that the order total is more than zero
	 */
	public boolean orderTotalIsValid(double orderTotal) {
		return orderTotal > 0;
	}

	/**
	 * Validate that the payment method is either Cash or Card or Reward Points
	 */
	public boolean paymentMethodIsValid(String paymentMethod) {
		return paymentMethod == "Cash" || paymentMethod == "Card" || paymentMethod == "Reward Points";
	}

}