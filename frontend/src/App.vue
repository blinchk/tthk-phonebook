<template>
  <v-app>
    <v-navigation-drawer app clipped v-model="drawerOpen">
      <v-list>
        <v-list-item-group>
          <v-list-item :to="item.to" v-for="(item, key) in nav" :key="key">
            <v-list-item-icon>
              <v-icon>
                {{ item.icon }}
              </v-icon>
            </v-list-item-icon>
            <v-list-item-title>
              {{ item.title }}
            </v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar
      app
      clipped-left
      color="primary"
      dark
    >
      <v-app-bar-nav-icon @click.stop="drawerOpen = !drawerOpen"/>
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
    drawerOpen: false,
    nav: [{
      title: 'Home',
      to: '/',
      icon: 'mdi-home'
    }, {
      title: 'Contacts',
      to: '/contacts',
      icon: 'mdi-contacts'
    }]
  })
}
</script>
