pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "meme"
include(":app")
include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:designsystem")
include(":core:ui")
include(":feature:main")
include(":feature:search")
include(":core:common")
include(":feature:category")
include(":feature:mymeme")
include(":feature:detail")
