//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2025.05.02 at 05:06:07 PM WIB 
//


package com.example.converterdemo.mx;

import jakarta.xml.bind.annotation.*;


/**
 * <p>Java class for Document complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Document"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FIToFICstmrCdtTrf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.13}FIToFICustomerCreditTransferV13"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
        "fiToFICstmrCdtTrf"
})
@XmlRootElement(name = "document", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.13")
public class Document {

    @XmlElement(name = "FIToFICstmrCdtTrf", required = true)
    protected FIToFICustomerCreditTransferV13 fiToFICstmrCdtTrf;

    /**
     * Gets the value of the fiToFICstmrCdtTrf property.
     *
     * @return possible object is
     * {@link FIToFICustomerCreditTransferV13 }
     */
    public FIToFICustomerCreditTransferV13 getFIToFICstmrCdtTrf() {
        return fiToFICstmrCdtTrf;
    }

    /**
     * Sets the value of the fiToFICstmrCdtTrf property.
     *
     * @param value allowed object is
     *              {@link FIToFICustomerCreditTransferV13 }
     */
    public void setFIToFICstmrCdtTrf(FIToFICustomerCreditTransferV13 value) {
        this.fiToFICstmrCdtTrf = value;
    }

}
