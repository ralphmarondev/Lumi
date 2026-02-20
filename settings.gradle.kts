pluginManagement {
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
    }
}

rootProject.name = "Lumi"
include(":app")
include(":core")
include(":miniapps:notes")
include(":miniapps:weather")
include(":miniapps:clock")
include(":system:boot")
include(":system:launcher")
include(":system:settings")
include(":system:media")
include(":miniapps:calendar")
include(":system:telephony")
include(":miniapps:calculator")
include(":system:store")
include(":miniapps:community")
