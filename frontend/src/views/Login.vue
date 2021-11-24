<template>
  <v-container fluid>
    <v-col>
      <v-row class="justify-center">
        <v-card max-width="500px" width="500px" flat>
          <v-card-title>
            Sign In
          </v-card-title>
          <v-form>
            <v-card-text>
              <v-text-field label="Username" v-model="credentials.username"></v-text-field>
              <v-text-field type="password" label="Password" v-model="credentials.password"></v-text-field>
            </v-card-text>
            <v-card-actions>
              <v-spacer/>
              <v-btn @click.stop="signIn">Sign In</v-btn>
            </v-card-actions>
          </v-form>
        </v-card>
      </v-row>
    </v-col>
  </v-container>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'Login',
  methods: {
    ...mapActions('user', ['authUser']),
    signIn () {
      this.authUser(this.credentials)
    }
  },
  data: () => ({
    credentials: {
      username: '',
      password: ''
    }
  })
}
</script>

<style scoped>

</style>
