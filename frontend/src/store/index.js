import Vue from 'vue'
import Vuex from 'vuex'

import { user } from './modules/user'
import axios from 'axios'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: () => ({
  }),
  getters: {
  },
  mutations: {
  },
  actions: {
    authUser ({ rootState }, payload) {
      return new Promise((resolve, reject) => {
        axios.get('http://localhost:8080/auth/user', {
          auth: {
            username: payload.username,
            password: payload.password
          }
        }).then((response) => {
          resolve(response)
        }).catch((error) => {
          reject(error)
        })
      })
    }
  },
  modules: {
    user
  }
})
