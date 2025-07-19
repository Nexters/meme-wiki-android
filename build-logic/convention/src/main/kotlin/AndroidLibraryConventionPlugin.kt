import com.android.build.gradle.LibraryExtension
import ext.androidTestImplementationLibrary
import ext.configureKotlinAndroid
import ext.coreLibraryDesugaring
import ext.implementationBundle
import ext.implementationLibrary
import ext.testImplementationLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                implementationBundle(libs, "kotlin")
                testImplementationLibrary(libs, "junit")
                androidTestImplementationLibrary(libs, "androidx-junit")

                coreLibraryDesugaring(libs, "android-tool-desugar")
            }
        }
    }
}