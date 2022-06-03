package acme.testing.inventor.down;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorDownUpdateTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/down/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String subject, final String explanation,
		final String startPeriod, final String endPeriod, final String quantity, final String additionalInfo) {
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my down");
		
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("explanation", explanation);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("quantity", quantity);
		super.fillInputBoxIn("additionalInfo", additionalInfo);
		
		super.checkSubmitExists("Update");
		super.clickOnSubmit("Update");
		super.checkListingExists();
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("explanation", explanation);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("quantity", quantity);
		super.checkInputBoxHasValue("additionalInfo", additionalInfo);
		
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/down/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String subject, final String explanation,
		final String startPeriod, final String endPeriod, final String quantity, final String additionalInfo) {
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my down");
		
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("explanation", explanation);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("quantity", quantity);
		super.fillInputBoxIn("additionalInfo", additionalInfo);
		
		super.checkSubmitExists("Update");
		super.clickOnSubmit("Update");
	
		super.checkNotPanicExists();
		super.checkErrorsExist();

		super.signOut();
	}
	
	@Test
	@Order(30)
	public void hackingTest() {
		//		a) update a down with a role other than "Inventor";
	}
}
