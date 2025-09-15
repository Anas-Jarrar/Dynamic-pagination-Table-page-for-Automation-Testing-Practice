package Pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;

public class MainPage extends BasePage {

    @FindBy(xpath = "//input[contains(@type,'search')]")
    private WebElement searchField;

    @FindBy(id = "demo")
    private WebElement tableBody;


    @FindBy(css = ".form-select.form-select-sm")
    private WebElement entrieSelect;

    @FindBy(className = "dataTables_info")
    private WebElement infoMessage;

    @FindBy(id="example_next")
    private WebElement nextButton;

    @FindBy(id="example_previous")
    private WebElement previousButton;

    @FindBy(xpath = "//th[text()='Student Name']")
    private WebElement studentNameHeader;


    public MainPage(WebDriver driver , WebDriverWait wait) {
        super(driver, wait);
    }

    public String searchFieldVerfiy(String studentName) {
        WebElement search = getWait().until(ExpectedConditions.visibilityOf(searchField));
        search.clear();
        search.sendKeys(studentName);
        WebElement TableBody = getWait().until(ExpectedConditions.visibilityOf(tableBody));
        List<WebElement> elements = TableBody.findElements(By.tagName("td"));
        String firstRowText = elements.get(0).getText();
        return firstRowText;
    }

    public List<String> getColumnValues (){
        WebElement TableBody = getWait().until(ExpectedConditions.visibilityOf(tableBody));
        List<WebElement> tableRows = TableBody.findElements(By.cssSelector(".sorting_1"));
        List<String> tableData = new ArrayList<>();
        for (WebElement data : tableRows) {
            tableData.add(data.getText());
        }
        return tableData;
    }

    public void selectEntryNums(String entrienum){
        WebElement dropdown=getWait().until(ExpectedConditions.elementToBeClickable(entrieSelect));
        Select select=new Select(dropdown);
        select.selectByValue(entrienum);
    }
    public int getExpectedNumbPages(){
        String EntrieValue ="3";
        selectEntryNums(EntrieValue);
        WebElement infomessage=getWait().until(ExpectedConditions.visibilityOf(infoMessage));
        String message=infomessage.getText();
        int AllEntries=Integer.parseInt(message.split(" ")[5]);
        int divRsult=(int)Math.ceil(AllEntries/Float.parseFloat(EntrieValue));
        return divRsult;
    }
    public int getActualNumofPages() {
        List<WebElement> numOfpages = getDriver().findElements(By.cssSelector(".paginate_button.page-item"));
        int NumOfPages = numOfpages.size() - 2;
        return NumOfPages;
    }
    public String getNumOfTableRows() {
        WebElement TableBody = getWait().until(ExpectedConditions.visibilityOf(tableBody));
        List<WebElement> numOfPages = TableBody.findElements(By.tagName("tr"));
        return String.valueOf(numOfPages.size());
    }

    public List<String> capturePageData() {
        WebElement TableBody = getWait().until(ExpectedConditions.visibilityOf(tableBody));
        List<WebElement> tableRows = TableBody.findElements(By.tagName("tr"));
        List<String> rowData = new ArrayList<>();
        for (WebElement row : tableRows) {
            rowData.add(row.getText());
        }
        return rowData;
    }

    public  void clickNextButton() {
        nextButton.click();
    }
    public void clickPreviousButton() {
        previousButton.click();
    }
    public boolean isNextDisabled() {
        return nextButton.getAttribute("class").contains("disabled");
    }
    public boolean isPreviousDisabled() {
        return previousButton.getAttribute("class").contains("disabled");
    }

    public void clickStudentNameHeader() {
        studentNameHeader.click();
    }

    public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,700)");
    }
    public void setSearchField(String searchText) {
        WebElement search = getWait().until(ExpectedConditions.visibilityOf(searchField));
        search.clear();
        search.sendKeys(searchText);
    }


}





