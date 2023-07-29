plugins {
    kotlin("jvm")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(KotlinDependencies.annotation)
    implementation(KotlinDependencies.kotlinxSerialization)
    implementation(KotlinDependencies.coroutines)
    implementation(AndroidXDependencies.pagingCommon)
    addOnlyTestDependencies()
    implementation(project(Modules.CORE_MODULE))
}
