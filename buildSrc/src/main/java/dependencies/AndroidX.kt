package dependencies

object AndroidX {

  const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
  const val app_compat = "androidx.appcompat:appcompat:${Versions.app_compat}"
  const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
  const val ui_tooling = "androidx.ui:ui-tooling:${Versions.androidx_ui}"

  // we will remove these 2 dependencies later when building the compose-only nav system
  const val nav_fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_component}"
  const val nav_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.nav_component}"

  const val compose_ui = "androidx.compose.ui:ui:${Versions.compose}"
  const val compose_foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
  const val compose_material = "androidx.compose.material:material:${Versions.compose}"
  const val compose_icons_core = "androidx.compose.material:material-icons-core:${Versions.compose}"
  const val compose_icons_extended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
  const val compose_activity = "androidx.activity:activity-compose:${Versions.compose_activity}"

  const val compose_ui_preview_tooling = "androidx.compose.ui:ui-tooling:${Versions.compose_tooling}"
  const val compose_ui_preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose_tooling}"


  const val navigation_compose = "androidx.navigation:navigation-compose:${Versions.nav_compose}"

//  const val navigation_hilt = "androidx.hilt:hilt-navigation:${Versions.hilt_navigation}"
  const val navigation_hilt = "androidx.hilt:hilt-navigation-compose:${Versions.hilt_navigation}" // might not work

  const val datastore = "androidx.datastore:datastore-preferences:${Versions.datastore}"

  // Splash Screen
  const val splash = "androidx.core:core-splashscreen:${Versions.splash}"
}