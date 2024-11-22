package com.example.gcceolinteractivepaper2.datamodels

data class PackagesData(
    var packages: ArrayList<PackageFormData>
)

data class PackageFormData(
    var packageName: String,
    var price: String,
    var duration: Int,
    var isChecked: Boolean=false
): java.io.Serializable
