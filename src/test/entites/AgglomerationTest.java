package test.entites;
import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AgglomerationTest {
	
	@ParameterizedTest
	@CsvSource({"-1","0","1"})
	public void TestContructeurMoinsDeuxVilles(int nbVilles) {
		assertThrows(IllegalArgumentException.class, () -> { new main.entites.Agglomeration(nbVilles) ; }) ;
	}
}
