package com.boc.webqr.service.impl;

import com.boc.webqr.bean.KeyValueBean;
import com.boc.webqr.bean.MerchantBean;
import com.boc.webqr.repository.ServiceRepository;
import com.boc.webqr.service.GenerateQRString;
import com.boc.webqr.util.varlist.CommonVarList;
import com.mastercard.mpqr.pushpayment.model.AdditionalData;
import com.mastercard.mpqr.pushpayment.model.MAIData;
import com.mastercard.mpqr.pushpayment.model.PushPaymentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
@Scope("prototype")
public class GenerateQRCode implements GenerateQRString {

    @Autowired
    ServiceRepository serviceRepository;

    public String generateQRString(MerchantBean merchantBean) {
        String qrString = "";
        try {
            List<KeyValueBean> userParamValues = serviceRepository.getQRUserParamValues();

            // User params
            String networkType = "", aqBank = "", subAqBank = "", acquirerINN = "", forwardingIIN = "";
            // Lqr nad upy mid tid
            String LQRMID = "", CUPMID = "", mid_16 = "", tid_4 = "";

            for (KeyValueBean kv : userParamValues) {
                if (kv.getKey().equals(CommonVarList.USERPARAM_LANKA_QR_NETWORKTYPE)) {
                    networkType = kv.getValue();
                } else if (kv.getKey().equals(CommonVarList.USERPARAM_LANKA_QR_AQ_BANK)) {
                    aqBank = kv.getValue();
                } else if (kv.getKey().equals(CommonVarList.USERPARAM_LANKA_QR_SUBAQ_BANK)) {
                    subAqBank = kv.getValue();
                } else if (kv.getKey().equals(CommonVarList.USERPARAM_UNION_PAY_ACQUIRER_IIN)) {
                    acquirerINN = kv.getValue();
                } else if (kv.getKey().equals(CommonVarList.USERPARAM_UNION_PAY_FORWARDING_IIN)) {
                    forwardingIIN = kv.getValue();
                }
            }

            // Lanka QR MID creation
            //======================================================
            // mandatory fields
            LQRMID = LQRMID.concat(networkType).concat(aqBank).concat(subAqBank);

            // china union pay
            CUPMID = CUPMID.concat(acquirerINN).concat(forwardingIIN);

            //MID to 16 digits. add 0 to front
            BigInteger gen_mid = new BigInteger(merchantBean.getMid());
            mid_16 = String.format("%016d", gen_mid);
            LQRMID = LQRMID.concat(mid_16);

            // union pay mid
            CUPMID = CUPMID.concat(String.format("%015d", gen_mid));

            //Tid to 4 - last 4 digits
            tid_4 = merchantBean.getTid().substring(4, 8);
            LQRMID = LQRMID.concat(tid_4);

            System.out.println("LQRMID - " + LQRMID);
            System.out.println("CUPMID - " + CUPMID);

            //======================================================
            PushPaymentData data = new PushPaymentData();
            data = this.createMerchantDataRequest(LQRMID, CUPMID, merchantBean);

            qrString = data.generatePushPaymentString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return qrString;
    }


    private PushPaymentData createMerchantDataRequest(String LQRMID, String CUPMID, MerchantBean merchant) throws Exception {
        PushPaymentData pushPaymentData = new PushPaymentData();
        try {
            //TAG 00 : PAYLOAD FORMAT INDICATOR
            pushPaymentData.setPayloadFormatIndicator(CommonVarList.LANKA_QR_PAYLOAD_FORMAT_INDICATOR);
            // Point of initiation method
            pushPaymentData.setValue(CommonVarList.LANKA_QR_POINT_OF_INITIATION_TAG_NUM, CommonVarList.LANKA_QR_POINT_OF_INITIATION_TAG_VAL);

            // Union Pay
            pushPaymentData.setMerchantIdentifierUnionPay15(CUPMID);

            // Lanka QR
            String rootTag = CommonVarList.LANKA_QR_ROOT_TAG; // Lanka QR tag (26,27)
            MAIData maiData = new MAIData(rootTag);
            maiData.setAID(LQRMID);
            // pushPaymentData.setValue(CommonVarList.LANKA_QR_ROOT_TAG, maiData);
            pushPaymentData.setDynamicMAIDTag(maiData);

            // Merchant category code
            pushPaymentData.setMerchantCategoryCode(merchant.getMcc());

            // Transaction currency code
            pushPaymentData.setTransactionCurrencyCode(CommonVarList.LANKA_QR_CURRENCY_CODE);

            // Amount
            pushPaymentData.setTransactionAmount(merchant.getAmount());

            //TAG 58 : COUNTRY CODE
            pushPaymentData.setCountryCode(CommonVarList.LANKA_QR_COUNTRYCODE);
            //TAG 59 : MERCHANT NAME - L 25
            if (merchant.getLegalName().length() <= 25) {
                pushPaymentData.setMerchantName(merchant.getLegalName().toUpperCase());
            } else {
                pushPaymentData.setMerchantName(merchant.getLegalName().substring(0, 25).toUpperCase());
            }

            //TAG 60 : MERCHANT CITY
            if (merchant.getCity().length() <= 15) {
                pushPaymentData.setMerchantCity(merchant.getCity().toUpperCase());
            } else {
                pushPaymentData.setMerchantCity(merchant.getCity().substring(0, 15).toUpperCase());
            }

            // Additional data
            AdditionalData addData = new AdditionalData();

            //TAG 62 -> 01 : Bill number ID
            addData.setBillNumber("00000000000000000000");
            //TAG 62 -> 05 : REFERENCE ID
            addData.setReferenceId(merchant.getReferenceLabel());

            //TAG 62 -> 07 : TERMINAL ID
            addData.setTerminalId(merchant.getTid());

            pushPaymentData.setAdditionalData(addData);

        } catch (Exception e) {
            throw e;
        }

        return pushPaymentData;
    }
}
