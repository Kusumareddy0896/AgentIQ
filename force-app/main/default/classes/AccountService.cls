public with sharing class AccountService {
    public AccountService() {
        System.debug('Sucessfully created AccountService class');
    }

    public static Integer addTwoNumbers(Integer a, Integer b) {
        return a + b;
    }
}