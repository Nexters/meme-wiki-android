package ext

import com.android.build.api.dsl.CommonExtension
import const.Constants
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        compileSdk = Constants.compileSdk
        defaultConfig {
            minSdk = Constants.minSdk
        }
        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = Constants.javaVersion
            targetCompatibility = Constants.javaVersion
        }
        kotlinOptions {
            jvmTarget = Constants.jvmVersion
        }
    }
}

private fun CommonExtension<*, *, *, *, *, *>.kotlinOptions(
    block: KotlinJvmOptions.() -> Unit
) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}