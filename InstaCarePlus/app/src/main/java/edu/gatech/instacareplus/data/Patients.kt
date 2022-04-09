package edu.gatech.instacareplus.data

data class patient(val name: String = "", val patient_id: String = "", val avl: String = "",
                   val doctor_id: Int = -1, val consultation_id: Long = -1)