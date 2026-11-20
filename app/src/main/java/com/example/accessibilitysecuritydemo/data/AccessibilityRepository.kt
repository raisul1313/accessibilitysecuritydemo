package com.example.accessibilitysecuritydemo.data

import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.view.accessibility.AccessibilityManager
import com.example.accessibilitysecuritydemo.domain.EnabledAccessibilityService


class AccessibilityRepository(
    private val context: Context
) {

    @SuppressLint("ServiceCast")
    fun getEnabledThirdPartyServices(): List<EnabledAccessibilityService> {

        val accessibilityManager = context.getSystemService(
            Context.ACCESSIBILITY_SERVICE
        ) as AccessibilityManager

        val packageManager = context.packageManager

        return accessibilityManager
            .getEnabledAccessibilityServiceList(
                AccessibilityServiceInfo.FEEDBACK_ALL_MASK
            )
            .mapNotNull { accessibilityInfo ->

                val serviceInfo = accessibilityInfo
                    .resolveInfo?.serviceInfo
                    ?: return@mapNotNull null

                val packageName = serviceInfo.packageName

                // Ignore our own application
                if (packageName == context.packageName) {
                    return@mapNotNull null
                }

                val applicationInfo = serviceInfo.applicationInfo

                val isSystemApp =
                    (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0 ||
                            (applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0

                // Only detect third-party applications
                if (isSystemApp) {
                    return@mapNotNull null
                }

                val appName = packageManager
                    .getApplicationLabel(applicationInfo)
                    .toString()

                EnabledAccessibilityService(
                    packageName = packageName,
                    appName = appName,
                    serviceName = serviceInfo.name,
                    isSystemApp = isSystemApp
                )
            }
            .distinctBy { it.packageName }
    }
}
