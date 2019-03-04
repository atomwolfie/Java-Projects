/**
 * Validates Products
 */
public class ProductValidator {

	public boolean typeIsValid(String prodType) {
		// Can specify later what are valid 'Types'
		return true;
	}

	public boolean providerIsValid(String provider) {
		// Can specify later what are valid 'Providers'
		return true;
	}

	/**
	 * Check if product price is valid (negative prices are not valid)
	 */
	public boolean prodPriceIsValid(double prodPrice) {
		return prodPrice > 0;
	}

	/**
	 * Check if product ID is valid (negative ID is not valid)
	 */
	public boolean prodIdIsValid(double prodId) {
		return prodId > 0;
	}
}