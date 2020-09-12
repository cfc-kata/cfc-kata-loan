package com.cfckata.loan.contract.response;

public class CreateContractResponse {
    private String contractId;

    public CreateContractResponse() {
    }

    public CreateContractResponse(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}
