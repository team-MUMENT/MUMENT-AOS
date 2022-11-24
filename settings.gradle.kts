pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Mument_Android"
//파라미터가 vararg라 여러개 보내기 가능
include(
    ":app",
    ":core",
    ":core_dependent",
    ":navigation",
    ":data",
    ":domain",
    ":feature:login",
    ":feature:locker",
    ":feature:record",
    ":feature:home",
    ":feature:detail",
    ":feature:navigation"
)
