import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Class to manage purchases
 */
public class Purchases {

	private int prodId;
	private int orderId;
	private int quantity;
	private String productName;
	private double prodPrice;
	private double discountPrice;
	private double purchaseTotal;
	private boolean isValidProduct;
	private boolean isValidOrder;
	private boolean isReturn;
	private PurchasesValidator validator;
	private ArrayList<String> items;

	/**
	 * Returns product ID
	 */
	public int getProdId() {
		return this.prodId;
	}

	/**
	 * Set product ID
	 */
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	/**
	 * Returns order ID
	 */
	public int getOrderId() {
		return this.orderId;
	}

	/**
	 * Set order ID
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		if (this.validator.quantityIsValid(quantity)) {
			this.quantity = quantity;
		}
	}

	public void incrementQuantity(int quantity) {
		this.quantity += quantity;
		this.purchaseTotal += this.prodPrice * quantity;
	}

	public void decrementQuantity(int quantity) {
		this.quantity -= quantity;
		this.purchaseTotal -= this.prodPrice * quantity;
	}
	public double getDiscountPrice(){return discountPrice;}

	
	public double getProdPrice() { 
		if(getDiscountPrice() == 0.0){
		return prodPrice;
		} 
		else{
			return getDiscountPrice();
		}
		
	}
	
	public String getProductName() { return productName; }

	public double getPurchaseTotal() {
		return this.purchaseTotal;
	}

	public void setPurchaseTotal(double purchaseTotal) {
		if (this.validator.purchaseTotalIsValid(purchaseTotal)) {
			this.purchaseTotal = purchaseTotal;
		}
	}

	public boolean isValidProduct() { return isValidProduct; }

	public boolean isValidOrder() {	return isValidOrder; }

	public void updatePurchases() {
		//quantity
		DBConnection.dbUpdateRecord("purchases","quantity=" + this.quantity,"productid=" + this.prodId + " AND orderid=" + this.orderId);
		//total
		DBConnection.dbUpdateRecord("purchases","purchasetotal=" + this.purchaseTotal,"productid=" + this.prodId + " AND orderid=" + this.orderId);
	}

	public void writeToDatabase(boolean isReturn) {
		DecimalFormat dec = new DecimalFormat("#.00");
		DBConnection.dbInsertInto("purchases", "\"" + this.prodId + "\",\"" + this.orderId + "\",\""
				+ this.quantity + "\",\"" + dec.format(this.purchaseTotal) + "\"");

		Product prod = new Product(this.prodId);
		if (isReturn) {
			System.out.println("Increment");
			prod.incrementStockBy(-1 * this.quantity);
		}
		else {
			System.out.println("Decrement");
			prod.decrementStockBy(this.quantity);
		}
	}

	public Purchases(String name, int quantity, boolean isReturn) {
		this.isReturn = isReturn;
		this.validator = new PurchasesValidator();
		items = new ArrayList();

		ResultSet myRsProducts = DBConnection.dbSelectAllFromTableWhere("products", "productname=\"" + name + "\"");

		this.orderId = -1;
		try {
			if (myRsProducts.next()) {
				this.prodId = myRsProducts.getInt("productid");
				if (this.validator.quantityIsValid(quantity, this.isReturn)) {
					this.quantity = quantity;
				} else {
					this.isValidProduct = false;
				}
				this.prodPrice = myRsProducts.getFloat("productprice");
				System.out.println("regular price: " + this.prodPrice);
				this.discountPrice = myRsProducts.getDouble("discountPrice");
				if(this.discountPrice > 0.0){
					this.prodPrice = this.discountPrice;
				}
				System.out.println("discount price: " + this.discountPrice);
				this.purchaseTotal = this.prodPrice * this.quantity;
				this.productName = myRsProducts.getString("productname");
				this.isValidProduct = true;
			} else {
				this.isValidProduct = false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Purchases(String name, int quantity) {
		this.validator = new PurchasesValidator();
		items = new ArrayList();

		ResultSet myRsProducts = DBConnection.dbSelectAllFromTableWhere("products", "productname=\"" + name + "\"");

		this.orderId = -1;
		try {
			if (myRsProducts.next()) {
				this.prodId = myRsProducts.getInt("productid");
				if (this.validator.quantityIsValid(quantity)) {
					this.quantity = quantity;
				} else {
					this.isValidProduct = false;
				}
				this.prodPrice = myRsProducts.getFloat("productprice");
				this.discountPrice = myRsProducts.getDouble("discountPrice");
				System.out.println("discount price: " + this.discountPrice);
				if(this.discountPrice > 0.0){
					this.prodPrice = this.discountPrice;
				}
				this.purchaseTotal = this.prodPrice * this.quantity;
				this.productName = myRsProducts.getString("productname");
				this.isValidProduct = true;
			} else {
				this.isValidProduct = false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Purchases(int prodId, int quantity, boolean isReturn) {
		this.isReturn = isReturn;
		this.validator = new PurchasesValidator();
		items = new ArrayList();

		ResultSet myRsProducts = DBConnection.dbSelectAllFromTableWhere("products", "productid=\"" + prodId + "\"");

		try {
			this.orderId = -1;
			if(myRsProducts.next()){
				this.prodId = prodId;
				if (this.validator.quantityIsValid(quantity, this.isReturn)) {
					this.quantity = quantity;
				}
				else {
					this.isValidProduct = false;
				}
				this.prodPrice = myRsProducts.getFloat("productprice");
				this.discountPrice = myRsProducts.getDouble("discountPrice");
				System.out.println("discount price: " + this.discountPrice);
				if(this.discountPrice > 0.0){
					this.prodPrice = this.discountPrice;
				}
				this.purchaseTotal = this.prodPrice * this.quantity;
				this.productName = myRsProducts.getString("productname");
				this.isValidProduct = true;
			}
			else {
				this.isValidProduct = false;
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public Purchases(int prodId, int quantity) {
		this.validator = new PurchasesValidator();
		items = new ArrayList();

		ResultSet myRsProducts = DBConnection.dbSelectAllFromTableWhere("products", "productid=\"" + prodId + "\"");

		try {
			this.orderId = -1;
			if(myRsProducts.next()){
				this.prodId = prodId;
				if (this.validator.quantityIsValid(quantity)) {
					this.quantity = quantity;
				}
				else {
					this.isValidProduct = false;
				}
				this.prodPrice = myRsProducts.getFloat("productprice");
				this.discountPrice = myRsProducts.getDouble("discountPrice");
				System.out.println("discount price: " + this.discountPrice);
				if(this.discountPrice > 0.0){
					this.prodPrice = this.discountPrice;
				}
				this.purchaseTotal = this.prodPrice * this.quantity;
				this.productName = myRsProducts.getString("productname");
				this.isValidProduct = true;
			}
			else {
				this.isValidProduct = false;
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public Purchases(int prodId, int orderId, int quantity) {

		this.validator = new PurchasesValidator();
		items = new ArrayList();


		ResultSet myRsProducts = DBConnection.dbSelectAllFromTableWhere("products", "productid=\"" + prodId + "\"");

		try {
			if(myRsProducts.next()){
				this.prodId = prodId;
				if (this.validator.quantityIsValid(quantity, this.isReturn)) {
					this.quantity = quantity;
				}
				else {
					this.isValidProduct = false;
				}
				this.prodPrice = myRsProducts.getFloat("productprice");
				this.discountPrice = myRsProducts.getDouble("discountPrice");
				System.out.println("discount price: " + this.discountPrice);
				
				if(this.discountPrice > 0.0){
					this.prodPrice = this.discountPrice;
				}
				this.purchaseTotal = this.quantity*this.prodPrice;
				this.productName = myRsProducts.getString("productname");
				this.isValidProduct = true;
			}

			ResultSet myRsOrders = DBConnection.dbSelectAllFromTableWhere("orders", "orderid=\"" + orderId + "\"");

			if(myRsOrders.next()){
				this.orderId = orderId;
				this.isValidOrder = true;
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public Purchases(int prodId, int orderId, int quantity, boolean isReturn) {
		this.isReturn = isReturn;
		this.validator = new PurchasesValidator();
		items = new ArrayList();


		ResultSet myRsProducts = DBConnection.dbSelectAllFromTableWhere("products", "productid=\"" + prodId + "\"");

		try {
			if(myRsProducts.next()){
				this.prodId = prodId;
				if (this.validator.quantityIsValid(quantity)) {
					this.quantity = quantity;
				}
				else {
					this.isValidProduct = false;
				}
				this.prodPrice = myRsProducts.getFloat("productprice");
				this.discountPrice = myRsProducts.getDouble("discountPrice");
				System.out.println("discount price: " + this.discountPrice);

				if(this.discountPrice > 0.0){
					this.prodPrice = this.discountPrice;
				}
				this.purchaseTotal = this.quantity*this.prodPrice;
				this.productName = myRsProducts.getString("productname");
				this.isValidProduct = true;
			}

			ResultSet myRsOrders = DBConnection.dbSelectAllFromTableWhere("orders", "orderid=\"" + orderId + "\"");

			if(myRsOrders.next()){
				this.orderId = orderId;
				this.isValidOrder = true;
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}