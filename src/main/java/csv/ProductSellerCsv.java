package csv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSellerCsv {

    public static final String PRODUCT_SELLER_ID = "id";
    public static final String SELLER_PRODUCT_ID = "sellerProductId";
    public static final String PRODUCT_NAME = "name";
    public static final String DISPLAY_NAME = "displayName";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String TERMS_AND_CONDITION = "termsAndCondition";
    public static final String IMAGE_URL = "imageUrl";
    public static final String IMAGE_URLS = "imageUrls";
    public static final String PRODUCT_URL = "productUrl";
    public static final String PRODUCT_SKU = "productSku";
    public static final String AVAILABLE_STOCK = "availableStock";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_RANK = "rank";

    public static final String BRAND_ID = "brandId";
    public static final String CATEGORY_ID = "categoryId";
    public static final String SELLER_ID = "sellerId";
    public static final String CLIENT_IDS = "clientIds";
    public static final String UPDATED_ON = "updatedOn";
    public static final String IS_ACTIVE = "active";
    public static final String IS_DELETED = "deleted";
    public static final String PRODUCT_TYPE = "productType";
    public static final String SOURCE_TYPE = "sourceType";

    @CsvBindByName
    private String id;
    @CsvBindByName
    private String productId;
    @CsvBindByName(required = true, column = "name")
    private String name;
    @CsvBindByName(required = true, column = "displayName")
    private String displayName;
    @CsvBindByName(column = "description")
    private String description;
    @CsvBindByName(column = "termsAndCondition")
    private String termsAndCondition;
    @CsvBindByName(column = "productType")
    private ProductType productType;
    @CsvBindByName(column = "sourceType")
    private SourceType sourceType;
    @CsvBindByName(required = true, column = "productUrl")
    private String productUrl;
    @CsvBindByName(required = true, column = "sellerProductId")
    private String sellerProductId;
    @CsvBindByName(column = "price")
    private BigDecimal price;
    @CsvBindByName
    private BigDecimal clientPrice;
    @CsvBindByName(column = "rank")
    private long rank;
    @CsvBindByName(column = "active")
    private Boolean active;
    @CsvBindByName(column = "brandId")
    private String brandId;
    @CsvBindByName(column = "categoryId")
    private String categoryId;

    @CsvBindByName(required = true, column = "sellerId")
    private String sellerId;
    @CsvBindAndSplitByName(column = "clientIds", elementType = String.class, splitOn = "-")
    private List<String> clientIds;
    @CsvBindAndSplitByName(column = "imageUrls", required = true, elementType = String.class, splitOn = "-")
    private List<String> imageUrls;
}