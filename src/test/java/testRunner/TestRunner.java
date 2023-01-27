package testRunner;

import controller.*;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.IOException;

public class TestRunner {

    @Test(priority = 1, description = "Customer can not do Login successfully With Wrong Email")
    public void doLoginWithWrongEmail() throws IOException {
        LoginWithCustomer loginWithCustomer = new LoginWithCustomer();
        loginWithCustomer.loginApiWithWrongEmail("ahnaf@grr.la", "1234");
        String messageExpected = "User not found";
        Assert.assertEquals(loginWithCustomer.getMessage(), messageExpected);
    }

    @Test(priority = 2, description = "Customer can do Login Successfully With Valid Credentials")
    public void doLoginWithCustomer() throws IOException, ConfigurationException {
        LoginWithCustomer loginWithCustomer = new LoginWithCustomer();
        loginWithCustomer.callingLoginAPI();
        String messageExpected = "Login successfully";
        Assert.assertEquals(loginWithCustomer.getMessage(), messageExpected);
    }

    @Test(priority = 3, description = "Creating Customer Successfully")
    public void createCustomer() throws IOException, ConfigurationException {

        CreateCustomer createCustomer = new CreateCustomer();
        createCustomer.createCustomer();
        String messageExpected = "User created successfully";
        Assert.assertEquals(createCustomer.getMessage(), messageExpected);
    }

    @Test(priority = 4, description = "The Customer is Already Created")
    public void alreadyCreatedCustomer() throws IOException{

        CreateCustomer createCustomer = new CreateCustomer();
        createCustomer.alreadyCreatedCustomer();
        String messageExpected = "User already exists";
        Assert.assertEquals(createCustomer.getMessage(), messageExpected);

    }

    @Test(priority = 5, description = "Get Customer Id Successfully")
    public void getCustomerId() throws IOException {

        GetCustomerId getCustomerId = new GetCustomerId();
        getCustomerId.getCustomerId();

    }

    @Test(priority = 6, description = "Get No Invalid Customer Id")
    public void getInvalidCustomerId() throws IOException {

        GetCustomerId getCustomerId = new GetCustomerId();
        getCustomerId.getInvalidCustomerId();

    }

    @Test(priority = 7, description = "Creating Agent Successfully")
    public void createAgent() throws IOException, ConfigurationException {

        CreateAgent createAgent = new CreateAgent();
        createAgent.createAgent();
        String messageExpected = "User created successfully";
        Assert.assertEquals(createAgent.getMessage(), messageExpected);

    }

    @Test(priority = 8, description = "The Agent is Already Created")
    public void alreadyCreatedAgent() throws IOException{

        CreateAgent createAgent = new CreateAgent();
        createAgent.alreadyCreatedAgent();
        String messageExpected = "User already exists";
        Assert.assertEquals(createAgent.getMessage(), messageExpected);

    }

    @Test(priority = 9, description = "Depositing Money To Agent Successfully")
    public void depositToAgentSuccessfully() throws IOException, ConfigurationException {

        DepositToAgent depositToAgent = new DepositToAgent();
        depositToAgent.successfullDepositToAgent();
        String messageExpected = "Deposit successful";
        Assert.assertEquals(depositToAgent.getMessage(), messageExpected);

    }

    @Test(priority = 10, description = "Depositing Money To Agent Unsuccessfully With Insufficient Money")
    public void unsuccessfullyDepositToAgent() throws IOException {

        DepositToAgent depositToAgent = new DepositToAgent();
        depositToAgent.InsufficientMoneyForDepositingToAgent();
        String messageExpected = "Insufficient balance";
        Assert.assertEquals(depositToAgent.getMessage(), messageExpected);

    }

    @Test(priority = 11, description = "Checking Agent Account Balance")
    public void checkAgentMoneyBalance() throws IOException {

        CheckAgentBalance checkAgentBalance = new CheckAgentBalance();
        checkAgentBalance.checkAgentBalance();
        String messageExpected = "User balance";
        Assert.assertEquals(checkAgentBalance.getMessage(), messageExpected);
    }

    @Test(priority = 12, description = "Checking Agent Balance With Wrong Agent Number")
    public void checkInvalidAgentMoneyBalance() throws IOException {

        CheckAgentBalance checkAgentBalance = new CheckAgentBalance();
        checkAgentBalance.checkInvalidAgentBalance();

    }

    @Test(priority = 13, description = "Depositing Money To Customer Successfully")
    public void depositToCustomerSuccessfully() throws IOException{

        DepositToCustomer depositToCustomer = new DepositToCustomer();
        depositToCustomer.successfullDepositToCustomer();
        String messageExpected = "Deposit successful";
        Assert.assertEquals(depositToCustomer.getMessage(), messageExpected);

    }

    @Test(priority = 14, description = "Depositing Money To Agent Unsuccessfully With Invalid Customer Number")
    public void depositToInvalidCustomer() throws IOException {

        DepositToCustomer depositToCustomer = new DepositToCustomer();
        depositToCustomer.depositToInvalidCustomer();
        String messageExpected = "Account does not exist";
        Assert.assertEquals(depositToCustomer.getMessage(), messageExpected);

    }

    @Test(priority = 15, description = "Checking Customer Account Balance")
    public void checkCustomerMoneyBalance() throws IOException {

        CheckCustomerBalance checkCustomerBalance = new CheckCustomerBalance();
        checkCustomerBalance.checkCustomerBalance();
        String messageExpected = "User balance";
        Assert.assertEquals(checkCustomerBalance.getMessage(), messageExpected);
    }

    @Test(priority = 16, description = "Checking Customer Balance With Wrong Customer Number")
    public void checkInvalidCustomerMoneyBalance() throws IOException {

        CheckCustomerBalance checkCustomerBalance = new CheckCustomerBalance();
        checkCustomerBalance.checkInvalidCustomerBalance();

    }

    @Test(priority = 17, description = "Customer Can Withdraw Money Successfully")
    public void moneyWithdrawByCustomer() throws IOException {

        MoneyWithdrawByCustomer moneyWithdrawByCustomer = new MoneyWithdrawByCustomer();
        moneyWithdrawByCustomer.moneyWithdrawByCustomer();
        String messageExpected = "Withdraw successful";
        Assert.assertEquals(moneyWithdrawByCustomer.getMessage(), messageExpected);
    }

    @Test(priority = 18, description = "Customer Can not Withdraw Money with Invalid Agent Number")
    public void moneyWithdrawByCustomerInvalidAgent() throws IOException{

        MoneyWithdrawByCustomer moneyWithdrawByCustomer = new MoneyWithdrawByCustomer();
        moneyWithdrawByCustomer.moneyWithdrawByCustomerWithInvalidAgentNumber();
        String messageExpected = "Account does not exist";
        Assert.assertEquals(moneyWithdrawByCustomer.getMessage(), messageExpected);

    }

    @Test(priority = 19, description = "Customer Can Send Money to Another Customer Successfully")
    public void sendMoneyByNewlyCreatedCustomerSuccessfully() throws IOException {

        SendMoneyByNewlyCreatedCustomer sendMoneyByNewlyCreatedCustomer = new SendMoneyByNewlyCreatedCustomer();
        sendMoneyByNewlyCreatedCustomer.sendMoneyByNewlyCreatedCustomer();

        String messageExpected = "Send money successful";
        Assert.assertEquals(sendMoneyByNewlyCreatedCustomer.getMessage(), messageExpected);

    }


    @Test(priority = 20, description = "Customer Can not Send Money to Another Customer Without Amount of Money")
    public void sendMoneyByCustomerWithoutMoneyAmount() throws IOException {

        SendMoneyByNewlyCreatedCustomer sendMoneyByNewlyCreatedCustomer = new SendMoneyByNewlyCreatedCustomer();
        sendMoneyByNewlyCreatedCustomer.sendMoneyByCustomerWithoutMoneyAmount();

        String messageExpected = "token";
        String messageActual = sendMoneyByNewlyCreatedCustomer.getMessage();
        Assert.assertTrue(messageActual.contains(messageExpected));
    }
}
