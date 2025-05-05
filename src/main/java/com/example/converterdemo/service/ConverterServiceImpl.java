package com.example.converterdemo.service;

import com.example.converterdemo.mx.*;
import com.example.converterdemo.mx.AccountIdentification4Choice;
import com.example.converterdemo.mx.ActiveCurrencyAndAmount;
import com.example.converterdemo.mx.ActiveOrHistoricCurrencyAndAmount;
import com.example.converterdemo.mx.CashAccount40;
import com.example.converterdemo.mx.ChargeBearerType1Code;
import com.example.converterdemo.mx.GenericAccountIdentification1;
import com.example.converterdemo.mx.LocalInstrument2Choice;
import com.example.converterdemo.mx.PaymentTypeInformation28;
import com.example.converterdemo.mx.SettlementMethod1Code;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.model.mx.MxPacs00800111;
import com.prowidesoftware.swift.model.mx.dic.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class ConverterServiceImpl {

    public String convertMTtoMXUsingGeneratedClass(String request) throws JAXBException {
        MT103 mt = new MT103(request);
        Document document = new Document();
        CreditTransferTransaction70 ctrTrans = new CreditTransferTransaction70();

        // header
        GroupHeader131 groupHeader = new GroupHeader131();

        // :20
        groupHeader.setMsgId(mt.getField20().getValue());
        groupHeader.setNbOfTxs("1");

        SettlementInstruction15 settlementInstruction = new SettlementInstruction15();
        settlementInstruction.setSttlmMtd(SettlementMethod1Code.CLRG);
        groupHeader.setSttlmInf(settlementInstruction);

        // :23B
        LocalInstrument2Choice lclInstrm = new LocalInstrument2Choice();
        lclInstrm.setPrtry(mt.getField23B().getValue());

        PaymentTypeInformation28 pmtTpInf = new PaymentTypeInformation28();
        pmtTpInf.setLclInstrm(lclInstrm);
        groupHeader.setPmtTpInf(pmtTpInf);

        // :32A
        ctrTrans.setIntrBkSttlmDt(getGregorianCalendar(mt.getField32A().getDateAsCalendar().getTime()));
        ActiveCurrencyAndAmount activeCurrencyAndAmount = new ActiveCurrencyAndAmount();
        activeCurrencyAndAmount.setCcy(mt.getField32A().getCurrency());
        activeCurrencyAndAmount.setValue(mt.getField32A().getAmountBigDecimal());
        ctrTrans.setIntrBkSttlmAmt(activeCurrencyAndAmount);

        // :33B
        ActiveOrHistoricCurrencyAndAmount activeOrHistoricCurrencyAndAmount = new ActiveOrHistoricCurrencyAndAmount();
        activeOrHistoricCurrencyAndAmount.setCcy(mt.getField33B().getCurrency());
        activeOrHistoricCurrencyAndAmount.setValue(mt.getField33B().getAmountAsBigDecimal());
        ctrTrans.setInstdAmt(activeOrHistoricCurrencyAndAmount);

        // Debtor
        PartyIdentification272 debtor = new PartyIdentification272();
        debtor.setNm(mt.getField50K().getNameAndAddressLine1());

        PostalAddress27 postalAddressDbtr = new PostalAddress27();
        postalAddressDbtr.setStrtNm(mt.getField50K().getNameAndAddressLine2());

        String dbtrCity = mt.getField50K().getNameAndAddressLine3();
        postalAddressDbtr.setTwnNm(dbtrCity.split(",")[0].trim());
        postalAddressDbtr.setCtry(dbtrCity.split(",")[1].trim());
        debtor.setPstlAdr(postalAddressDbtr);
        ctrTrans.setDbtr(debtor);

        // Debtor acct
        CashAccount40 cashAccountDbtr = new CashAccount40();
        AccountIdentification4Choice acctIdDbtr = new AccountIdentification4Choice();
        GenericAccountIdentification1 gaiDbtr = new GenericAccountIdentification1();
        gaiDbtr.setId(mt.getField50K().getAccount());
        acctIdDbtr.setOthr(gaiDbtr);
        cashAccountDbtr.setId(acctIdDbtr);
        ctrTrans.setDbtrAcct(cashAccountDbtr);

        // Creditor
        PartyIdentification272 creditor = new PartyIdentification272();
        debtor.setNm(mt.getField59().getNameAndAddressLine1());

        PostalAddress27 postalAddressCdtr = new PostalAddress27();
        postalAddressCdtr.setStrtNm(mt.getField59().getNameAndAddressLine2());

        String cdtrCity = mt.getField59().getNameAndAddressLine3();
        postalAddressCdtr.setTwnNm(cdtrCity.split(",")[0].trim());
        postalAddressCdtr.setCtry(cdtrCity.split(",")[1].trim());
        creditor.setPstlAdr(postalAddressCdtr);
        ctrTrans.setCdtr(creditor);

        // Creditor acct
        CashAccount40 cashAccountCdtr = new CashAccount40();
        AccountIdentification4Choice acctIdCdtr = new AccountIdentification4Choice();
        GenericAccountIdentification1 gaiCdtr = new GenericAccountIdentification1();
        gaiCdtr.setId(mt.getField59().getAccount());
        acctIdCdtr.setOthr(gaiCdtr);
        cashAccountCdtr.setId(acctIdCdtr);
        ctrTrans.setCdtrAcct(cashAccountCdtr);

        // :71A
        ctrTrans.setChrgBr(getChrgBr(mt.getField71A().getValue()));

        // :72
        RemittanceInformation22 remittanceInformation = new RemittanceInformation22();
        remittanceInformation.getUstrd().add(mt.getField72().getValue());
        ctrTrans.setRmtInf(remittanceInformation);

        FIToFICustomerCreditTransferV13 transferV13 = new FIToFICustomerCreditTransferV13();
        transferV13.setGrpHdr(groupHeader);
        transferV13.getCdtTrfTxInf().add(ctrTrans);
        document.setFIToFICstmrCdtTrf(transferV13);

        JAXBContext context = JAXBContext.newInstance(Document.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter writer = new StringWriter();
        marshaller.marshal(document, writer);
        return writer.toString();
    }

    public String convertMTtoMXUsingProwide(String request) {
        MT103 mt = new MT103(request);
        MxPacs00800111 mx = new MxPacs00800111();
        CreditTransferTransaction58 ctrTrans = new CreditTransferTransaction58();

        // header
        GroupHeader96 groupHeader = new GroupHeader96();

        // :20
        groupHeader.setMsgId(mt.getField20().getValue());
        groupHeader.setNbOfTxs("1");

        SettlementInstruction11 settlementInstruction = new SettlementInstruction11();
        settlementInstruction.setSttlmMtd(com.prowidesoftware.swift.model.mx.dic.SettlementMethod1Code.CLRG);
        groupHeader.setSttlmInf(settlementInstruction);

        // :23B
        com.prowidesoftware.swift.model.mx.dic.LocalInstrument2Choice lclInstrm = new com.prowidesoftware.swift.model.mx.dic.LocalInstrument2Choice();
        lclInstrm.setPrtry(mt.getField23B().getValue());

        com.prowidesoftware.swift.model.mx.dic.PaymentTypeInformation28 pmtTpInf = new com.prowidesoftware.swift.model.mx.dic.PaymentTypeInformation28();
        pmtTpInf.setLclInstrm(lclInstrm);
        groupHeader.setPmtTpInf(pmtTpInf);

        // :32A
        ctrTrans.setIntrBkSttlmDt(LocalDate.now());
        com.prowidesoftware.swift.model.mx.dic.ActiveCurrencyAndAmount activeCurrencyAndAmount = new com.prowidesoftware.swift.model.mx.dic.ActiveCurrencyAndAmount();
        activeCurrencyAndAmount.setCcy(mt.getField32A().getCurrency());
        activeCurrencyAndAmount.setValue(mt.getField32A().getAmountBigDecimal());
        ctrTrans.setIntrBkSttlmAmt(activeCurrencyAndAmount);

        // :33B
        com.prowidesoftware.swift.model.mx.dic.ActiveOrHistoricCurrencyAndAmount activeOrHistoricCurrencyAndAmount = new com.prowidesoftware.swift.model.mx.dic.ActiveOrHistoricCurrencyAndAmount();
        activeOrHistoricCurrencyAndAmount.setCcy(mt.getField33B().getCurrency());
        activeOrHistoricCurrencyAndAmount.setValue(mt.getField33B().getAmountAsBigDecimal());
        ctrTrans.setInstdAmt(activeOrHistoricCurrencyAndAmount);

        // Debtor
        PartyIdentification135 debtor = new PartyIdentification135();
        debtor.setNm(mt.getField50K().getNameAndAddressLine1());

        PostalAddress24 postalAddressDbtr = new PostalAddress24();
        postalAddressDbtr.setStrtNm(mt.getField50K().getNameAndAddressLine2());

        String dbtrCity = mt.getField50K().getNameAndAddressLine3();
        postalAddressDbtr.setTwnNm(dbtrCity.split(",")[0].trim());
        postalAddressDbtr.setCtry(dbtrCity.split(",")[1].trim());
        debtor.setPstlAdr(postalAddressDbtr);
        ctrTrans.setDbtr(debtor);

        // Debtor acct
        com.prowidesoftware.swift.model.mx.dic.CashAccount40 cashAccountDbtr = new com.prowidesoftware.swift.model.mx.dic.CashAccount40();
        com.prowidesoftware.swift.model.mx.dic.AccountIdentification4Choice acctIdDbtr = new com.prowidesoftware.swift.model.mx.dic.AccountIdentification4Choice();
        com.prowidesoftware.swift.model.mx.dic.GenericAccountIdentification1 gaiDbtr = new com.prowidesoftware.swift.model.mx.dic.GenericAccountIdentification1();
        gaiDbtr.setId(mt.getField59().getAccount());
        acctIdDbtr.setOthr(gaiDbtr);
        cashAccountDbtr.setId(acctIdDbtr);
        ctrTrans.setDbtrAcct(cashAccountDbtr);

        // Creditor
        PartyIdentification135 creditor = new PartyIdentification135();
        debtor.setNm(mt.getField59().getNameAndAddressLine1());

        PostalAddress24 postalAddressCdtr = new PostalAddress24();
        postalAddressCdtr.setStrtNm(mt.getField59().getNameAndAddressLine2());

        String cdtrCity = mt.getField59().getNameAndAddressLine3();
        postalAddressCdtr.setTwnNm(cdtrCity.split(",")[0].trim());
        postalAddressCdtr.setCtry(cdtrCity.split(",")[1].trim());
        creditor.setPstlAdr(postalAddressCdtr);
        ctrTrans.setCdtr(creditor);

        // Creditor acct
        com.prowidesoftware.swift.model.mx.dic.CashAccount40 cashAccountCdtr = new com.prowidesoftware.swift.model.mx.dic.CashAccount40();
        com.prowidesoftware.swift.model.mx.dic.AccountIdentification4Choice acctIdCdtr = new com.prowidesoftware.swift.model.mx.dic.AccountIdentification4Choice();
        com.prowidesoftware.swift.model.mx.dic.GenericAccountIdentification1 gaiCdtr = new com.prowidesoftware.swift.model.mx.dic.GenericAccountIdentification1();
        gaiCdtr.setId(mt.getField50K().getAccount());
        acctIdCdtr.setOthr(gaiCdtr);
        cashAccountCdtr.setId(acctIdCdtr);
        ctrTrans.setCdtrAcct(cashAccountCdtr);

        // :71A
        ctrTrans.setChrgBr(getChrgBrProwide(mt.getField71A().getValue()));

        // :72
        RemittanceInformation21 remittanceInformation = new RemittanceInformation21();
        remittanceInformation.getUstrd().add(mt.getField72().getValue());
        ctrTrans.setRmtInf(remittanceInformation);

        FIToFICustomerCreditTransferV11 transferV11 = new FIToFICustomerCreditTransferV11();
        transferV11.setGrpHdr(groupHeader);
        transferV11.addCdtTrfTxInf(ctrTrans);
        mx.setFIToFICstmrCdtTrf(transferV11);
        return mx.document();
    }

    private ChargeBearerType1Code getChrgBr(String chrgBr) {
        return switch (chrgBr) {
            case "OUR" -> ChargeBearerType1Code.DEBT;
            case "BEN" -> ChargeBearerType1Code.CRED;
            case "SHA" -> ChargeBearerType1Code.SHAR;
            default -> ChargeBearerType1Code.SLEV;
        };
    }

    private com.prowidesoftware.swift.model.mx.dic.ChargeBearerType1Code getChrgBrProwide(String chrgBr) {
        return switch (chrgBr) {
            case "OUR" -> com.prowidesoftware.swift.model.mx.dic.ChargeBearerType1Code.DEBT;
            case "BEN" -> com.prowidesoftware.swift.model.mx.dic.ChargeBearerType1Code.CRED;
            case "SHA" -> com.prowidesoftware.swift.model.mx.dic.ChargeBearerType1Code.SHAR;
            default -> com.prowidesoftware.swift.model.mx.dic.ChargeBearerType1Code.SLEV;
        };
    }

    private XMLGregorianCalendar getGregorianCalendar(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(gregorianCalendar);
    }
}
