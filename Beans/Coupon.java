package Beans;

import java.util.Date;

/**
 * coupon class
 */
public class Coupon {
    private int id;
    private int companyID;
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    /**
     * c'tor get:
     *
     * @param companyID   of the company Who created the coupon
     * @param category    of coupon
     * @param title       of coupon
     * @param description of coupon
     * @param startDate   of coupon
     * @param endDate     of coupon
     * @param amount      of coupon
     * @param price       of coupon
     * @param image       of coupon
     *                    build instance of coupon:
     *                    (id will set by SQL)
     */
    public Coupon(int companyID, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * @return coupon id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id receive id and set id of coupon
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return CompanyID
     */
    public int getCompanyID() {
        return companyID;
    }

    /**
     * @param companyID receive companyID and set companyID of coupon
     */
    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    /**
     * @return Category
     */
    public Category getCatagory() {
        return category;
    }

    /**
     * @param category receive category and set category of coupon
     */
    public void setCatagory(Category category) {
        this.category = category;
    }

    /**
     * @return Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title receive title and set title of coupon
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description receive description and set description of coupon
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return start Date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate receive start Date and set start Date of coupon
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return end Date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate receive end Date and set end Date of coupon
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount receive amount and set amount of coupon
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price receive price and set price of coupon
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image receive image and set image of coupon
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return all values coupon by String
     */
    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyID=" + companyID +
                ", catagory=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
