<template>
  <v-app>
    <v-navigation-drawer app clipped v-model="drawerOpen">
      <h1>Nav</h1>
    </v-navigation-drawer>
    <v-app-bar
      app
      clipped-left
      color="primary"
      dark
    >
      <v-app-bar-nav-icon @click.stop="drawerOpen = !drawerOpen" />
      <v-app-bar-title>
        Phonebook
      </v-app-bar-title>
    </v-app-bar>

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: 'App',

  data: () => ({
    drawerOpen: false
  })
}
</script>
