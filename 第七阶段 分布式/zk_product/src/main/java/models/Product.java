package models;

import java.io.Serializable;

/**
 * @author lengy
 * @date 2022/7/16 22:12
 * @description 商品表
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 4061590391400877748L;
    private Integer id;
    private String product_name;
    private Integer stock;
    private Integer VERSION;

    public Product() {
    }

    public Product(Integer id, String product_name, Integer stock, Integer VERSION) {
        this.id = id;
        this.product_name = product_name;
        this.stock = stock;
        this.VERSION = VERSION;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getVERSION() {
        return VERSION;
    }

    public void setVERSION(Integer VERSION) {
        this.VERSION = VERSION;
    }
}
