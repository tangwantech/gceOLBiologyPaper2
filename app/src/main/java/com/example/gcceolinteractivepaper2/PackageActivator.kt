package com.example.gcceolinteractivepaper2

import com.example.gcceolinteractivepaper2.datamodels.PackageData

class PackageActivator {
    companion object{

        fun activateTrialPackage(): PackageData {
            val activationExpiryDates =
                ActivationExpiryDatesGenerator.generateActivationExpiryDates(
                    AppConstants.HOURS,
                    AppConstants.TRIAL_DURATION
                )
            return PackageData(
                "TRIAL",
                activationExpiryDates.activatedOn,
                activationExpiryDates.expiresOn,
                isPackageActive = true
            )
        }

        fun activatePackageData(packageType: String, packageDuration: Int): PackageData {
            val activationExpiryDates =
                ActivationExpiryDatesGenerator.generateActivationExpiryDates(
                    AppConstants.HOURS,
                    packageDuration
                )
            println(activationExpiryDates)

            return PackageData(
                packageName = packageType,
                activatedOn = activationExpiryDates.activatedOn,
                expiresOn = activationExpiryDates.expiresOn,
                isPackageActive = true
            )
        }

    }


}