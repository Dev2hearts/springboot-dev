import org.junit.jupiter.api.*;

public class JUnitTest {
    @BeforeAll
    public static void init(){
        System.out.println("init");
    }
    @DisplayName("1+2는 3이다.")
    @Test
    public void junitTest1() {
        int a = 1;
        int b = 2;
        int sum = 3;
        int result = a + b;
        System.out.println("1+2는 3이다.");
        Assertions.assertEquals(sum, result);
    }
    @DisplayName("1+3는 3이다.")
    @Test
    public void junitTest2(){
        int a = 1;
        int b = 3;
        int result = a + b;
        System.out.println("1+3는 3이다.");
        Assertions.assertEquals(3, result);
    }
    @BeforeEach
    public void prepare(){
        System.out.println("준비");
    }
    @AfterEach
    public void cleanup(){
        System.out.println("설겆이");
    }
    @AfterAll
    public static void end(){
        System.out.println("END");
    }
}
