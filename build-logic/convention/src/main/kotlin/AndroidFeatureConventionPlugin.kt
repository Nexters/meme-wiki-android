import ext.androidTestImplementation
import ext.androidTestImplementationLibrary
import ext.coreLibraryDesugaring
import ext.debugImplementationLibrary
import ext.implementation
import ext.implementationBundle
import ext.implementationLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("mimu.android.library")
                apply("mimu.android.hilt")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                implementation(project(":core:domain"))
                implementation(project(":core:ui"))

                implementationBundle(libs, "kotlin")
                implementationBundle(libs, "androidx")

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