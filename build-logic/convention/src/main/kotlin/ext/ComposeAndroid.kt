package ext

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureComposeAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }
    }

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies {
        implementation(platform(libs.findLibrary("androidx-compose-bom").get()))
        implementationLibrary(libs, "androidx-activity-compose")
        implementationLibrary(libs, "androidx-ui")
        implementationLibrary(libs, "androidx-ui-graphics")
        implementationLibrary(libs, "androidx-ui-tooling-preview")
        implementationLibrary(libs, "androidx-ui-tooling")
        implementationLibrary(libs, "androidx-material3")
    }
}