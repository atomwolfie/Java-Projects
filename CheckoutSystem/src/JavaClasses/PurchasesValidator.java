/**
 * Validate purchases
 */
public class PurchasesValidator {

	/**
	 * Check that quantity is valid (greater than 0)
	 */
	public boolean quantityIsValid(int quantity) {
		return quantity > 0;
	}

	/**
	 * Check that quantity is valid (depending on if it's a return or not)
	 */
	public boolean quantityIsValid(int quantity, boolean isReturn) {
		if (isReturn) {
			return quantity < 0;
		}
		return quantity > 0;
	}

	/**
	 * Validate that the purchase total is greater than zero
	 */
	public boolean purchaseTotalIsValid(double purchaseTotal) { return purchaseTotal > 0; }


}