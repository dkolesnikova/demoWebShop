package guru.qa;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class DemoWebShopTests {
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://demowebshop.tricentis.com";
    }
    @Test
    void addToCartTest () {
        String cookieValue = "C3B23684816523C3E10934D04BB7269BD98869D9B42CE39E42379171707" +
                "1721A102B8200F60C64202EFF08A8BB31019B281869DCC0A013C1BDDB8A2C040B083CB7EE18743398AB5F22BC4F575" +
                "96FA47732C6ED3EF4505FAC4C44E1324BC72D5D6C434630381EAAD9EF1A5CC8316CAA105B3A34D3709FBA22C343FD" +
                "CB8F4546262AC16884DD69B73F0ECE3F16DFCBAE2A;",
                body = "product_attribute_72_5_18=65" +
                        "&product_attribute_72_6_19=54" +
                        "&product_attribute_72_3_20=57" +
                        "&product_attribute_72_8_30=94" +
                        "&addtocart_72.EnteredQuantity=3";
        given()
//                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("NOPCOMMERCE.AUTH", cookieValue)
                .body(body)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }
    @Test
    void addToCartAnonymityTest() {
        String body = "product_attribute_72_5_18=65" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&product_attribute_72_8_30=94" +
                "&addtocart_72.EnteredQuantity=3";
        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(body)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(3)"));
    }
    @Test
    void  addGiftCardToCertTest(){
        String cookieValue = "A98006D0403D7109D9F0464FE30D570FA0BC54DC26740E4C0FEE68CFD8861CAA44BD265B9BF34DB2D4E24"+
                "B44AF029853C2844C85ABA86B5D8E20C901D381C2C3B0AE3AF5A9E2B47493FDCEE722785AF98C586949F1CC02DD25A64BF"+
                "66189B3090B37DEF0CD338FB3D093B093FD57BD54C9709F24D02AF6A24B37787078B12BB2619EBE9577DD095B30964D076E7"+
                "C56FE5008740022C8AF3763DF576595420895;";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("NOPCOMMERCE.AUTH",cookieValue)
                .body("giftcard_1.RecipientName=ivan&giftcard_1.RecipientEmail=ivanov-9934%40mail.ru&giftcard_1" +
                        ".SenderName=ivan&giftcard_1.SenderEmail=ivanov-9934%40mail.ru&giftcard_1.Message=hb+qa" +
                        "+guru&addtocart_1.EnteredQuantity=1")
                .when()
                .post("/addproducttocart/details/1/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }
}
