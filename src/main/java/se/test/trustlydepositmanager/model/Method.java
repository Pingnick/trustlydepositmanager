package se.test.trustlydepositmanager.model;

public enum Method {
    DEPOSIT("Deposit"),
    REFUND("Refund"),
    CREDIT("credit"),
    DEBIT("debit"),
    PENDING("pending"),
    CANCEL("cancel"),
    ACCOUNT("account"),
    CHARGE("Charge"),
    CANCEL_CHARGE("CancelCharge"),
    WITHDRAWAL("Withdraw"),
    WITHDRAWAL_APPROVE("ApproveWithdrawal"),
    WITHDRAWAL_DENY("DenyWithdrawal"),
    ACCOUNT_PAYOUT("AccountPayout"),
    ACCOUNT_LEDGER("AccountLedger"),
    VIEW_AUTOMATIC_SETTLEMENT_DETAIL_CSV("ViewAutomaticSettlementDetailsCSV"),
    ACCOUNT_SELECT("SelectAccount"),
    BALANCE("Balance"),
    WITHDRAWALS_GET("GetWithdrawals");



    public final String label;

    private Method(String label){
        this.label = label;
    }

    public static Method valueOfLabel(String label) {
        for (Method e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
