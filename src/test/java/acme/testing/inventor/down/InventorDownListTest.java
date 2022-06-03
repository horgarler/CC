package acme.testing.inventor.down;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorDownListTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/down/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex,final String code, final String subject, final String item) {
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my down");
		super.checkListingExists();
		super.sortListing(1, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, subject);
		super.checkColumnHasValue(recordIndex, 2, item);
		
		super.signOut();
	}

	
    @Test
    @Order(30)
    public void hackingTest() {
        super.checkNotLinkExists("Account");
        super.navigate("/inventor/down/list-mine");
        super.checkPanicExists();

        super.signIn("administrator", "administrator");
        super.navigate("/inventor/down/list-mine");
        super.checkPanicExists();
        super.signOut();

        super.signIn("patron1", "patron1");
        super.navigate("/inventor/down/list-mine");
        super.checkPanicExists();
        super.signOut();
    }
}