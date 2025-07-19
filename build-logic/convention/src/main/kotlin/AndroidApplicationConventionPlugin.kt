import com.android.build.api.dsl.ApplicationExtension
import const.Constants
import ext.androidTestImplementation
import ext.androidTestImplementationLibrary
import ext.configureKotlinAndroid
import ext.coreLibraryDesugaring
import ext.debugImplementationLibrary
import ext.implementationBundle
import ext.testImplementationLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Application Extension 을 위한 Plugin
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("mimu.android.hilt")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    applicationId = Constants.packageName
                    targetSdk = Constants.targetSdk
                    versionCode = Constants.versionCode
                    versionName = Constants.versionName
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                buildFeatures {
                    viewBinding = true
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                implementationBundle(libs, "kotlin")
                implementationBundle(libs, "androidx")

                testImplementationLibrary(libs, "junit")
                androidTestImplementationLibrary(libs, "androidx-junit")
                androidTestImplementationLibrary(libs, "androidx-espresso-core")
                androidTestImplementation(platform(libs.findLibrary("androidx-compose-bom").get()))
                androidTestImplementationLibrary(libs, "androidx-ui-test-junit4")
                debugImplementationLibrary(libs, "androidx-ui-tooling")
                debugImplementationLibrary(libs, "androidx-ui-test-manifest")

                coreLibraryDesugaring(libs, "android-tool-desugar")
            }
        }
    }
}