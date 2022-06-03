package acme.testing.inventor.down;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorDownShowTest  extends TestHarness{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/down/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String code, final String item, final String creationMoment, final String subject, final String explanation, final String startPeriod, final String endPeriod, final String quantity,final String additionalInfo) {
		
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my down");
		super.checkListingExists();
		super.sortListing(1, "asc");
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("item", item);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("explanation", explanation);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("quantity", quantity);
		super.checkInputBoxHasValue("additionalInfo", additionalInfo);
		
		super.signOut();
	}

}