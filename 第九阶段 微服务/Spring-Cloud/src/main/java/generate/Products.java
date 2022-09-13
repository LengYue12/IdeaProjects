package generate;

import java.io.Serializable;
import lombok.Data;

/**
 * products
 * @author 
 */
@Data
public class Products implements Serializable {
    private Integer pid;

    private String name;

    private Double price;

    private String pDesc;

    /**
     * 商品状态 1 上架 ,0 下架
     */
    private Integer status;

    private Integer goodsStock;

    private Integer goodsType;

    private static final long serialVersionUID = 1L;
}