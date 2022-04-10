package edu.gatech.instacareplus.data

import model.Prescription

data class docRecord(val docName: String = "", val consultDate: String = "", val docType: String = "", val consultID: String = "", val presc: Prescription)