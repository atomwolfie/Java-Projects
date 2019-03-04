import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Manage product information
 */
public class Product {

	private String prodName;
	private String type;
	private String provider;
	private double price;
	private double discount;
	private int prodId;
	private int stock;
	private boolean isValidProd;

	/**
	 * Returns stock
	 */
	public int getStock() { return stock; }

	/**
	 * Set stock
	 */
	public void setStock(int stock) {
		if (DBConnection.dbUpdateRecord("products","inStock=" + stock,"productid=" + this.prodId)) {
			this.stock = stock;
		}
	}

	/**
	 * Decrement stock by amount given
	 */
	public void decrementStockBy(int decr) {
		if (DBConnection.dbUpdateRecord("products","inStock=" + (this.stock - decr),"productid=" + this.prodId)) {
			this.stock -= decr;
		}
	}
	public void incrementStockBy(int incr) {
		if (DBConnection.dbUpdateRecord("products","inStock=" + (this.stock + incr),"productid=" + this.prodId)) {
			this.stock += incr;
		}
	}

	public String getProdName() {
		return this.prodName;
	}

	public void setProdName(String prodName) {
		if (DBConnection.dbUpdateRecord("products","productname=" + prodName,"productid=" + this.prodId)) {
			this.prodName = prodName;
		}
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		if (DBConnection.dbUpdateRecord("products","type=" + type,"productid=" + this.prodId)) {
			this.type = type;
		}
	}

	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		if (DBConnection.dbUpdateRecord("products","provider=" + provider,"productid=" + this.prodId)) {
			this.provider = provider;
		}
	}

	public double getPrice() {
			
		return this.price;
	}

	public void setPrice(double price) {
		ProductValidator valid = new ProductValidator();
		if (!valid.prodPriceIsValid(price)) {
			return;
		}
		if (DBConnection.dbUpdateRecord("products","productprice=" + price,"productid=" + this.prodId)) {
			this.price = price;
		}
	}

	public int getProdId() {
		return this.prodId;
	}

	public void setProdId(int prodId) {
		ProductValidator valid = new ProductValidator();
		if (!valid.prodIdIsValid(prodId)) {
			return;
		}
		if (DBConnection.dbUpdateRecord("products","productid=" + prodId,"productid=" + this.prodId)) {
			this.prodId = prodId;
		}
	}

	public boolean isValidProd() { return isValidProd; }

	public void setValidProd(boolean validProd) { isValidProd = validProd; }

	public Product(int id) {
		ResultSet rs = DBConnection.dbSelectAllFromTableWhere("products","productid=" + id);
		try {
			this.isValidProd = false;
			if (rs.next()) {
				this.prodId = rs.getInt("productid");
				this.prodName = rs.getString("productname");
				this.price = rs.getDouble("productprice");
				this.provider = rs.getString("provider");
				this.type = rs.getString("type");
				this.stock = rs.getInt("inStock");
				this.isValidProd = true;
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}