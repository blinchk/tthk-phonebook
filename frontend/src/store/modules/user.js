import axios from 'axios'

const state = () => ({
  accessToken: null,
  user: {
    username: null,
    email: null,
    firstName: null,
    lastName: null
  }
})

const getters = {
  accessToken: (state) => state.accessToken,
  accessTokenHeaderValue: (state) => 'Bearer ' + state.accessToken
}

const actions = {
  authUser ({ rootState, commit }, payload) {
    return new Promise((resolve, reject) => {
      axios.get('http://localhost:8080/auth/login', {
        auth: {
          username: payload.username,
          password: payload.password
        }
      }).then((response) => {
        commit('setAccessToken', response.data.token)
        commit('setUser', response.data)
        resolve(response)
      }).catch((error) => {
        reject(error)
      })
    })
  }
}

const mutations = {
  setAccessToken (state, payload) {
    localStorage.setItem('accessToken', payload)
    state.accessToken = payload
  },
  setUser (state, payload) {
    const user = {
      username: payload.username,
      email: payload.email,
      firstName: payload.firstName,
      lastName: payload.lastName
    }
    localStorage.setItem('userData', JSON.stringify(user))
    state.user = user
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
